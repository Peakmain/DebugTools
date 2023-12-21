package com.peakmain.debug.adapter

import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.holder.BaseLibraryViewHolder
import com.peakmain.debug.R
import com.peakmain.debug.base.EnvironmentExchangeBean
import com.peakmain.debug.databinding.DebugRecyclerEnvironmentItemBinding
import com.peakmain.debug.manager.DebugToolsManager
import com.peakmain.debug.manager.H5PreferenceManager

/**
 * author ：Peakmain
 * createTime：2023/9/27
 * mail:2726449200@qq.com
 * describe：
 */
internal class EnvironmentExchangeAdapter(
    data: MutableList<EnvironmentExchangeBean>,
) :
    CommonRecyclerDataBindingAdapter<EnvironmentExchangeBean, DebugRecyclerEnvironmentItemBinding>(
        data, R.layout.debug_recycler_environment_item, null
    ) {
    private var mOldSelectedPosition: Int = -1
    private var isClick = false
    private var mSaveUrl: String=H5PreferenceManager.instance.getNativeEnvironmentUrl()

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
            } else if (mOldSelectedPosition == -1 && itemData.isSelected) {
                defaultSelect(position, itemData)
                H5PreferenceManager.instance.saveNativeEnvironmentUrl(itemData.url)
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
        DebugToolsManager.instance.mSelectEnvironmentCallback?.invoke(itemData)
    }

    private fun updateEnvironmentExchange(
        itemData: EnvironmentExchangeBean,
        position: Int,
    ) {
        itemData.isSelected = true
        if (mOldSelectedPosition != -1) {
            mData[mOldSelectedPosition].isSelected = false
            notifyItemChanged(mOldSelectedPosition)
        }
        notifyItemChanged(position)
        H5PreferenceManager.instance.saveNativeEnvironmentUrl(itemData.url)
        DebugToolsManager.instance.mSelectEnvironmentCallback?.invoke(itemData)
        mOldSelectedPosition = position
    }
}