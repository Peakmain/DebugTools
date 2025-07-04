package com.peakmain.debug

import android.content.Context
import android.content.Intent
import android.os.Build
import com.peakmain.debug.activity.CrashLogActivity
import com.peakmain.debug.activity.EnvironmentExchangeActivity
import com.peakmain.debug.annotation.PDebug
import com.peakmain.debug.log.HttpLoggingActivity
import com.peakmain.ui.utils.fps.FpsMonitorUtils

/**
 * author ：Peakmain
 * createTime：2022/3/13
 * mail:2726449200@qq.com
 * describe：
 */
class DebugTools {


    fun buildTime(): String =
        "构建时间:${BuildConfig.BUILD_TIME}"


    fun buildEnvironment(): String =
        "构建环境: " + if (BuildConfig.DEBUG) "测试环境" else "正式环境"

    fun buildDevice(): String =
        "设备信息:" + Build.BRAND + "-" + Build.VERSION.SDK_INT + "-" + Build.CPU_ABI


    @PDebug(name = "查看Crash日志", desc = "可以一键分享给Android开发，迅速定位偶现问题")
    fun crashLog(context: Context) {
        val intent = Intent(context, CrashLogActivity::class.java)
        context.startActivity(intent)
    }

    @PDebug(name = "打开/关闭Fps", desc = "打开后可以查看页面实时的FPS")
    fun toggleFps(context: Context) {
        FpsMonitorUtils.toggle()
    }

    @PDebug(name = "接口抓包工具", desc = "可以查看100条最新的接口数据")
    fun httpLogging(context: Context) {
        context.startActivity(Intent(context, HttpLoggingActivity::class.java))
    }

    @PDebug(name = "环境切换", desc = "一键切换Http和H5环境")
    fun exchangeEnvironment(context: Context){
        context.startActivity(Intent(context,EnvironmentExchangeActivity::class.java))
    }
    @PDebug(name = "生成bug", desc = "生成bug，方便测试查看crash日志")
    fun createCrash(context: Context) {
        5 / 0
    }
}