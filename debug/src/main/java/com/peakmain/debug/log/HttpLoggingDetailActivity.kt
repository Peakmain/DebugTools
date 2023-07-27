package com.peakmain.debug.log

import android.view.View
import com.peakmain.basiclibrary.viewmodel.EmptyViewModel
import com.peakmain.debug.R
import com.peakmain.debug.base.BaseDebugActivity
import com.peakmain.debug.bean.HttpLoggingBean
import com.peakmain.debug.databinding.ActivityDebugHttpLoggingDetailBinding

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
            mBinding.tvResult.text = mHttpLoggingBean.headInfo
        }
        mBinding.stvRequestBody.setOnClickListener {
            mBinding.tvResult.text = mHttpLoggingBean.requestBody?:"无数据"
        }
        mBinding.stvBackResult.setOnClickListener {
            mBinding.tvResult.text = mHttpLoggingBean.result
        }
    }
}