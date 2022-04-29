package com.peakmain.debug.viewmodel

import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.debug.utils.CrashUtils
import java.io.File

/**
 * author ：Peakmain
 * createTime：2022/3/14
 * mail:2726449200@qq.com
 * describe：
 */
class CrashLogViewModel : BaseViewModel() {

    lateinit var crashFiles: Array<File>
    override fun initModel() {
        crashFiles = CrashUtils.getCrashFiles()
    }
}