package com.peakmain.debug.adapter

import com.peakmain.basiclibrary.adapter.CommonRecyclerDataBindingAdapter
import com.peakmain.basiclibrary.adapter.holder.BaseLibraryViewHolder
import com.peakmain.debug.R
import com.peakmain.debug.databinding.DebugRecyclerHttpLoggingBinding

/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
internal class HttpLoggingAdapter(data:MutableList<String>): CommonRecyclerDataBindingAdapter<String,DebugRecyclerHttpLoggingBinding>(
    data,
    R.layout.debug_recycler_http_logging) {
    override fun convert(
        holder: BaseLibraryViewHolder<DebugRecyclerHttpLoggingBinding>,
        itemData: String,
        position: Int
    ) {
        val binding = holder.itemDataBinding
        binding.vm = itemData
    }

}