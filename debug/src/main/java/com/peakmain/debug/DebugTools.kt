package com.peakmain.debug

import android.content.Context
import android.content.Intent
import android.os.Build
import com.peakmain.debug.activity.CrashLogActivity

/**
 * author ：Peakmain
 * createTime：2022/3/13
 * mail:2726449200@qq.com
 * describe：
 */
class DebugTools {
    fun buildVersion(): String =
         "构建版本:${BuildConfig.VERSION_NAME}"


    fun buildTime(): String =
         "构建时间:${BuildConfig.BUILD_TIME}"


    fun buildEnvironment(): String = "构建环境: " + if (BuildConfig.DEBUG) "测试环境" else "正式环境"
    fun buildDevice(): String =
         "设备信息:" + Build.BRAND + "-" + Build.VERSION.SDK_INT + "-" + Build.CPU_ABI


    @PDebug(name = "查看Crash日志", desc = "可以一键分享给开发同学，迅速定位偶现问题")
    fun crashLog(context: Context) {
        val intent = Intent(context, CrashLogActivity::class.java)
        context.startActivity(intent)
    }
}