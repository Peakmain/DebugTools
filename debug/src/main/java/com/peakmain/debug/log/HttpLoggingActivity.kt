package com.peakmain.debug.log

import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
        Log.e("TAG",mViewModel.toString())
        mViewModel.mLoggingMutableList.observe(this,
            Observer<MutableList<HttpLoggingBean>> {
                notifyDataChange(it)
            })
    }

   private fun notifyDataChange(data: MutableList<HttpLoggingBean>) {
        Log.e("TAG11", data.toString())
        mAdapter?.setData(data)
    }
    private fun initToolbar() {
        mDefaultNavigationBar
            .setTitleText("接口抓包工具")
            .setDisplayHomeAsUpEnabled(true)
            .setRightResId(R.drawable.ic_debug_clear_all_24)
            .showRightView()
            .setRightViewClickListener(View.OnClickListener { v ->
                AlertDialog.Builder(v.context)
                    .setTitle("清空日志")
                    .setMessage("是否确定清空所有数据")
                    .setNegativeButton("取消"
                    ) { dialog, _ -> dialog?.dismiss() }
                    .setPositiveButton("确定"
                    ) { dialog, _ ->

                        dialog.dismiss()
                    }.show()
            }).create()

    }

}