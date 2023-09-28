package com.peakmain.debug.adapter

import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.holder.BaseLibraryViewHolder
import com.peakmain.debug.R
import com.peakmain.debug.base.EnvironmentExchangeBean
import com.peakmain.debug.databinding.DebugRecyclerEnvironmentItemBinding
import com.peakmain.debug.manager.DebugToolsManager
import com.peakmain.ui.utils.LogUtils
import com.peakmain.ui.utils.PreferencesUtil

/**
 * author ：Peakmain
 * createTime：2023/9/27
 * mail:2726449200@qq.com
 * describe：
 */
internal class H5EnvironmentExchangeAdapter(data: MutableList<EnvironmentExchangeBean>) :
    CommonRecyclerDataBindingAdapter<EnvironmentExchangeBean, DebugRecyclerEnvironmentItemBinding>(
        data, R.layout.debug_recycler_environment_item, null
    ) {
    private var mOldSelectedPosition: Int = -1
    private var isClick = false

    private val h5EnvironmentExchangeKey = "h5EnvironmentExchangeKey"
    private var mPreferencesUtils: PreferencesUtil? = null
    private var mSaveUrl: String
    init {
        mPreferencesUtils = PreferencesUtil.instance
        mSaveUrl = mPreferencesUtils?.getParam(h5EnvironmentExchangeKey, "") as String
    }
    override fun convert(
        holder: BaseLibraryViewHolder<DebugRecyclerEnvironmentItemBinding>,
        itemData: EnvironmentExchangeBean,
        position: Int,
    ) {
        val itemDataBinding = holder.itemDataBinding
        if (!isClick) {
            if (!android.text.TextUtils.isEmpty(mSaveUrl)) {
                if (mSaveUrl == itemData.url) {
                    defaultSelect(position, itemData)
                } else {
                    itemData.isSelected = false
                }
            }
            else if (mOldSelectedPosition == -1 && itemData.isSelected) {
                defaultSelect(position, itemData)
                mPreferencesUtils?.saveParam(h5EnvironmentExchangeKey, itemData.url)
            } else {
                itemData.isSelected = false
            }
        }
        itemDataBinding.vm = itemData

        holder.itemView.setOnClickListener {
            isClick = true
            if (mOldSelectedPosition == position) return@setOnClickListener
            updateEnvironmentExchange(itemData, position)
        }
    }
    private fun defaultSelect(
        position: Int,
        itemData: EnvironmentExchangeBean,
    ) {
        mOldSelectedPosition = position
        DebugToolsManager.instance.mSelectH5EnvironmentCallback?.invoke(itemData)
    }
    private fun updateEnvironmentExchange(
        itemData: EnvironmentExchangeBean,
        position: Int,
    ) {
        itemData.isSelected = true
        if (mOldSelectedPosition != -1){
            mData[mOldSelectedPosition].isSelected = false
            notifyItemChanged(mOldSelectedPosition)
        }
        notifyItemChanged(position)
        mPreferencesUtils?.saveParam(h5EnvironmentExchangeKey, itemData.url)
        DebugToolsManager.instance.mSelectH5EnvironmentCallback?.invoke(itemData)
        mOldSelectedPosition = position
    }
}