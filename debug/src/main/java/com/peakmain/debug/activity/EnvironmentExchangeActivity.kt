package com.peakmain.debug.activity

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.peakmain.debug.R
import com.peakmain.debug.adapter.EnvironmentExchangeAdapter
import com.peakmain.debug.adapter.H5EnvironmentExchangeAdapter
import com.peakmain.debug.base.BaseDebugActivity
import com.peakmain.debug.databinding.DebugActivityEnvironmentExchangeBinding
import com.peakmain.debug.manager.DebugToolsManager
import com.peakmain.debug.viewmodel.EnvironmentExchangeViewModel

/**
 * author ：Peakmain
 * createTime：2023/9/27
 * mail:2726449200@qq.com
 * describe：
 */
class EnvironmentExchangeActivity(override val layoutId: Int = R.layout.debug_activity_environment_exchange) :
    BaseDebugActivity<DebugActivityEnvironmentExchangeBinding, EnvironmentExchangeViewModel>() {
    override fun initView() {
        mDefaultNavigationBar.setTitleText("环境切换").setDisplayHomeAsUpEnabled(true).create()
        initData()
    }

    private fun initData() {
        val environmentExchangeList = DebugToolsManager.instance.getEnvironmentExchangeList()
        val h5EnvironmentExchangeList = DebugToolsManager.instance.getH5EnvironmentExchangeList()
        if (h5EnvironmentExchangeList.isEmpty()) {
            mBinding.tvH5Title.visibility = View.GONE
        }else{
            mBinding.tvH5Title.visibility = View.VISIBLE
            H5EnvironmentExchangeAdapter(h5EnvironmentExchangeList).also {
                mBinding.rvH5EnvironmentExchange.adapter=it
            }
            mBinding.rvEnvironmentExchange.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        if (environmentExchangeList.isEmpty()) {
            mBinding.rvEnvironmentExchange.showEmptyView()
        } else {
            EnvironmentExchangeAdapter(environmentExchangeList).also {
                mBinding.rvEnvironmentExchange.adapter = it
            }

            mBinding.rvEnvironmentExchange.addItemDecoration(
                DividerItemDecoration(
                    this,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}