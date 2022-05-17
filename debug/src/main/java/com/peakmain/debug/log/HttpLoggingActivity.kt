package com.peakmain.debug.log

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peakmain.debug.R
import com.peakmain.debug.adapter.HttpLoggingAdapter
import com.peakmain.debug.base.BaseDebugActivity
import com.peakmain.debug.bean.HttpLoggingBean
import com.peakmain.debug.databinding.DebugHttpLoggingBinding
import com.peakmain.debug.viewmodel.HttpLoggingViewModel

/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
class HttpLoggingActivity(override val layoutId: Int = R.layout.debug_http_logging) :
    BaseDebugActivity<DebugHttpLoggingBinding, HttpLoggingViewModel>() {
    private var mAdapter: HttpLoggingAdapter? = null

    override fun initView() {
        initToolbar()
        mAdapter = HttpLoggingAdapter(mViewModel.mLoggingMutableList.value!!)
        mBinding.debugRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@HttpLoggingActivity)
        }
        val mutableList = mViewModel.mLoggingMutableList.value
        if (mutableList != null && mutableList.size > 0) {
            mBinding.tvHeader.text = mViewModel.mLoggingMutableList.value!![0].requestUrl
        }
        mViewModel.mLoggingMutableList.observe(this,
            Observer<MutableList<HttpLoggingBean>> {
                if (mutableList != null && mutableList.size > 0) {
                    mBinding.tvHeader.text = mViewModel.mLoggingMutableList.value!![0].requestUrl
                }
                notifyDataChange(it)
            })

        mBinding.debugRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val findFirstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                    if (mutableList != null && mutableList.size > 0) {
                        mBinding.tvHeader.text =
                            mViewModel.mLoggingMutableList.value!![findFirstVisibleItemPosition].requestUrl
                    }
                }
            }
        })
    }

    private fun notifyDataChange(data: MutableList<HttpLoggingBean>) {
        Log.e("TAG11", data.toString())
        mAdapter?.setData(data)
    }


    private fun initToolbar() {
        val create = mDefaultNavigationBar
            .setTitleText("接口抓包工具")
            .setDisplayHomeAsUpEnabled(true)
            .setRightResId(R.drawable.ic_descending_order)
            .showRightView()
            .setRightViewClickListener(View.OnClickListener { v ->
                val value = mViewModel.isPositiveSequence.value
                mViewModel.isPositiveSequence.value = if (value == null) true else !value
                mViewModel.mLoggingMutableList.value =
                    mViewModel.mLoggingMutableList.value?.asReversed()
            }).create()

        mViewModel.isPositiveSequence.observe(this,
            Observer<Boolean> {
                create?.setRightResId(if (it) R.drawable.ic_positive_sequence else R.drawable.ic_descending_order)
            })

    }

}