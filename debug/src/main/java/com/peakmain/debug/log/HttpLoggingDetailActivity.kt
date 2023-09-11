package com.peakmain.debug.log

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.peakmain.basiclibrary.viewmodel.EmptyViewModel
import com.peakmain.debug.R
import com.peakmain.debug.base.BaseDebugActivity
import com.peakmain.debug.bean.HttpLoggingBean
import com.peakmain.debug.databinding.ActivityDebugHttpLoggingDetailBinding
import com.peakmain.debug.ext.updateTextStyle

/**
 * author ：Peakmain
 * createTime：2023/07/26
 * mail:2726449200@qq.com
 * describe：
 */
class HttpLoggingDetailActivity(override val layoutId: Int = R.layout.activity_debug_http_logging_detail) :
    BaseDebugActivity<ActivityDebugHttpLoggingDetailBinding, EmptyViewModel>() {
    private val mHttpLoggingBean by lazy {
        intent.getSerializableExtra("HttpLoggingBean") as HttpLoggingBean
    }

    override fun initView() {
        mDefaultNavigationBar
            .setTitleText(mHttpLoggingBean.requestUrl ?: "接口详情")
            .setDisplayHomeAsUpEnabled(true)
            .create()

        mBinding.tvResult.text = mHttpLoggingBean.headInfo
        mBinding.stvHeader.setOnClickListener {
            updateSelectTvColor(0)
            mBinding.tvResult.text = mHttpLoggingBean.headInfo
        }
        mBinding.stvRequestBody.setOnClickListener {
            updateSelectTvColor(1)
            mBinding.tvResult.text = mHttpLoggingBean.requestBody ?: "无数据"
        }
        mBinding.stvBackResult.setOnClickListener {
            updateSelectTvColor(2)
            mBinding.tvResult.text = mHttpLoggingBean.result
        }
        mBinding.ivShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val title = mHttpLoggingBean.requestUrl
            val shareResult = StringBuffer()
            if (!TextUtils.isEmpty(title)) {
                intent.putExtra(Intent.EXTRA_SUBJECT, title)
                shareResult.append("请求的url:${title}\n\n")
            }
            val requestBody = mHttpLoggingBean.requestBody
            if (requestBody != null)
                shareResult.append("请求参数body:${requestBody}\n\n")
            else
                shareResult.append("请求参数body:无数据\n\n")
            val result = mHttpLoggingBean.result
            if (!TextUtils.isEmpty(result)) {
                shareResult.append("返回的结果是：${result}\n\n")
            }
            intent.putExtra(Intent.EXTRA_TEXT, shareResult.toString())
            startActivity(Intent.createChooser(intent, "接口分享:$title"))
        }
        mBinding.ivShare.visibility = View.INVISIBLE
    }

    private fun updateSelectTvColor(position: Int) {

        mBinding.stvHeader.apply {
            updateTextStyle(position == 0, this@HttpLoggingDetailActivity)
        }
        mBinding.stvRequestBody.apply {
            updateTextStyle(position == 1, this@HttpLoggingDetailActivity)
        }
        mBinding.stvBackResult.apply {
            updateTextStyle(position == 2, this@HttpLoggingDetailActivity)
        }
        mBinding.ivShare.visibility = if (position == 2) View.VISIBLE else View.INVISIBLE
    }

}