package com.peakmain.debug.viewmodel

import androidx.lifecycle.MutableLiveData
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.debug.bean.HttpLoggingBean

/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
class HttpLoggingViewModel : BaseViewModel() {
    var mLoggingMutableList: MutableLiveData<MutableList<HttpLoggingBean>> = MutableLiveData()
    var isPositiveSequence = MutableLiveData<Boolean>()
    override fun initModel() {

    }
}