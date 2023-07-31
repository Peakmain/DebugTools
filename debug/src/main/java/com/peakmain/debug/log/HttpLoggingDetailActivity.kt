package com.peakmain.debug.log

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
            updateSelectTvColor( 0)
            mBinding.tvResult.text = mHttpLoggingBean.headInfo
        }
        mBinding.stvRequestBody.setOnClickListener {
            updateSelectTvColor( 1)
            mBinding.tvResult.text = mHttpLoggingBean.requestBody ?: "无数据"
        }
        mBinding.stvBackResult.setOnClickListener {
            updateSelectTvColor( 2)
            mBinding.tvResult.text = mHttpLoggingBean.result
        }
    }

    fun updateSelectTvColor(position: Int) {

        mBinding.stvHeader.apply {
            updateTextStyle(position==0,this@HttpLoggingDetailActivity)
        }
        mBinding.stvRequestBody.apply {
            updateTextStyle(position==1,this@HttpLoggingDetailActivity)
        }
        mBinding.stvBackResult.apply {
            updateTextStyle(position==2,this@HttpLoggingDetailActivity)
        }
    }

}