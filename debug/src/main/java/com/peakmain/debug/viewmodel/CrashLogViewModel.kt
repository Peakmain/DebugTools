package com.peakmain.debug.viewmodel

import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.debug.utils.CrashUtils

/**
 * author ：Peakmain
 * createTime：2022/3/14
 * mail:2726449200@qq.com
 * describe：
 */
class CrashLogViewModel:BaseViewModel() {

    val crashFiles = CrashUtils.getCrashFiles()
    override fun initModel() {

    }
}