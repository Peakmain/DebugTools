package com.peakmain.debug.viewmodel

import androidx.lifecycle.MutableLiveData
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel

/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
class HttpLoggingViewModel : BaseViewModel() {
    var mLoggingMutableList: MutableLiveData<MutableList<String>> = MutableLiveData()
    override fun initModel() {

    }
}