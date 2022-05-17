package com.peakmain.debug.log

import android.util.Log
import com.peakmain.basiclibrary.config.BasicLibraryConfig
import com.peakmain.debug.bean.HttpLoggingBean
import com.peakmain.debug.viewmodel.HttpLoggingViewModel
import com.peakmain.ui.utils.HandlerUtils
import okhttp3.*
import kotlin.jvm.Volatile
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.io.IOException
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
class HttpLoggingInterceptor : Interceptor {

    @Volatile
    private var headersToRedact = emptySet<String>()
    private var mViewModel: HttpLoggingViewModel? = null
    private var mHttplogginList: MutableList<HttpLoggingBean>? = null

    init {
        mViewModel = BasicLibraryConfig.getInstance().getApp().getViewModelProvider()
            .get(HttpLoggingViewModel::class.java)
        mHttplogginList = ArrayList()

    }

    fun redactHeader(name: String) {
        val newHeadersToRedact: MutableSet<String> =
            TreeSet(java.lang.String.CASE_INSENSITIVE_ORDER)
        newHeadersToRedact.addAll(headersToRedact)
        newHeadersToRedact.add(name)
        headersToRedact = newHeadersToRedact
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        val connection = chain.connection()
        var result = "\n————————请求Start————————\n"
        val mHttpLoggingBean = HttpLoggingBean()
        mHttpLoggingBean.requestStartMessage =
            request.method() + ' ' + request.url() + if (connection != null) " " + connection.protocol() else ""

        if (hasRequestBody) {
            mHttpLoggingBean.requestStartMessage += "(\" + requestBody!!.contentLength() + \"-byte body)"
        }
        if (hasRequestBody) {
            if (requestBody!!.contentType() != null) {
                mHttpLoggingBean.contentType = "Content-Type: ${requestBody.contentType()}"
            }
            if (requestBody.contentLength() != -1L) {
                mHttpLoggingBean.contentLength = "Content-Length: ${requestBody.contentLength()}"
            }
            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name,
                        ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)
                ) {
                    logHeader(headers, i,mHttpLoggingBean)
                }
                i++
            }
            if (!hasRequestBody) {
                result += "--> END ${request.method()}\n"
            } else if (bodyHasUnknownEncoding(request.headers())) {
                result += "--> END ${request.method()} (encoded body omitted)\n"
            } else {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                var charset = UTF8
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }

                // logger.log("");
                if (isPlaintext(buffer)) {
                    result += buffer.readString(charset) + "\n"
                    result += "--> END ${request.method()} (${requestBody.contentLength()} -byte body)"
                } else {
                    result += "--> END ${request.method()} (binary ${requestBody.contentLength()} -byte body omitted)"
                }
            }
        }
        val startNs = System.nanoTime()
        val response: Response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            result += "<-- HTTP FAILED: $e"
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        mHttpLoggingBean.responseContent = "<-- ${response.code()}(" + if (response.message()
                .isEmpty()
        ) "" else ' ' + response.message() + ")请求的url链接: ${
            response.request().url()
        }" + " (" + tookMs + "ms," + ("$bodySize body") + ')'
        val headers = response.headers()
        var i = 0
        val count = headers.size()
        while (i < count) {
            logHeader(headers, i, mHttpLoggingBean)
            i++
        }
        if (!HttpHeaders.hasBody(response)) {
            result += "<-- END HTTP\n"
        } else if (bodyHasUnknownEncoding(response.headers())) {
            result += "<-- END HTTP (encoded body omitted)\n"
        } else {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            var buffer = source.buffer()
            var gzippedLength: Long? = null
            mHttpLoggingBean.responseBody = "返回结果：\n"
            if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                gzippedLength = buffer.size()
                var gzippedResponseBody: GzipSource? = null
                try {
                    gzippedResponseBody = GzipSource(buffer.clone())
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                } finally {
                    gzippedResponseBody?.close()
                }
            }
            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            if (!isPlaintext(buffer)) {
                mHttpLoggingBean.responseBody += "<-- END HTTP (binary \" + buffer.size() + \"-byte body omitted)\n"
                return response
            }
            if (contentLength != 0L) {
                mHttpLoggingBean.responseBody += buffer.clone().readString(charset) + "\n"
            }
            //mHttpLoggingBean.responseBody = FormatJson.format(mHttpLoggingBean.responseBody)
            /* mHttpLoggingBean.responseBody += if (gzippedLength != null) {
                 "<-- END HTTP (" + buffer.size() + "-byte, " + gzippedLength + "-gzipped-byte body)\n"

             } else {
                 "<-- END HTTP (" + buffer.size() + "-byte body)\n"
             }*/
        }
        result += "————————请求End————————\n"
        Log.e("treasure", mHttpLoggingBean.toString())
        mHttplogginList?.add(mHttpLoggingBean)
        HandlerUtils.runOnUiThread(Runnable {
            mViewModel?.mLoggingMutableList?.value = mHttplogginList
        })
        return response
    }

    private fun logHeader(headers: Headers, i: Int, mHttpLoggingBean: HttpLoggingBean) {
        val value = if (headersToRedact.contains(headers.name(i))) "██" else headers.value(i)
        mHttpLoggingBean.headInfo += headers.name(i) + ": " + value

    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        fun isPlaintext(buffer: Buffer): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false // Truncated UTF-8 sequence.
            }
        }

        private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
            val contentEncoding = headers["Content-Encoding"]
            return (contentEncoding != null && !contentEncoding.equals("identity",
                ignoreCase = true)
                    && !contentEncoding.equals("gzip", ignoreCase = true))
        }
    }
}