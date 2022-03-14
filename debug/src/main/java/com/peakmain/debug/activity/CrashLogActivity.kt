package com.peakmain.debug.activity

import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peakmain.debug.R
import com.peakmain.debug.adapter.CrashLogAdapter
import com.peakmain.debug.base.BaseDebugActivity
import com.peakmain.debug.databinding.DebugActivityCrashLogBinding
import com.peakmain.debug.viewmodel.CrashLogViewModel

/**
 * author ：Peakmain
 * createTime：2022/3/13
 * mail:2726449200@qq.com
 * describe：
 */
class CrashLogActivity(override val layoutId: Int = R.layout.debug_activity_crash_log) :
    BaseDebugActivity<DebugActivityCrashLogBinding, CrashLogViewModel>() {
    override fun initView() {
        initToolbar()
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.shape_divider_debug_tool
            )!!)
        val crashFiles = mViewModel.crashFiles
        mBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CrashLogActivity)
            adapter = CrashLogAdapter(this@CrashLogActivity, crashFiles)
            if (crashFiles.isEmpty()) {
                showEmptyView()
            } else {
                showContentView()
            }
            addItemDecoration(decoration)
        }


    }

    private fun initToolbar() {
        mDefaultNavigationBar
            .setTitleText("crash日志")
            .setDisplayHomeAsUpEnabled(true)
            .setRightResId(R.drawable.ic_debug_clear_all_24)
            .showRightView()
            .setRightViewClickListener {
                AlertDialog.Builder(this)
                    .setTitle("清空日志")
                    .setMessage("是否确定清空所有日志")
                    .setNegativeButton("取消"
                    ) { dialog, _ -> dialog?.dismiss() }.setPositiveButton("确定"
                    ) { dialog, _ -> dialog.dismiss() }
                    .show()
            }.create()

    }


}