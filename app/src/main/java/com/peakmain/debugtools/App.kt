package com.peakmain.debugtools

import android.app.Application
import com.peakmain.debug.utils.CrashUtils

/**
 * author ：Peakmain
 * createTime：2022/3/13
 * mail:2726449200@qq.com
 * describe：
 */
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        CrashUtils.init(this)
    }
}