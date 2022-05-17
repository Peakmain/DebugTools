package com.peakmain.debug.bean

/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
data class HttpLoggingBean(
    var requestStartMessage: String? = null,
    var requestUrl: String? = null,
    var requestBody: String? = null,
    var responseContent: String? = null,
    var responseBody: String? = null,
    var contentType: String? = null,
    var contentLength: String? = null,
    var headInfo: String? = null

)