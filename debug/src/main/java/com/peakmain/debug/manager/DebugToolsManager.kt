package com.peakmain.debug.manager

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.peakmain.debug.DebugToolDialogFragment
import com.peakmain.debug.base.EnvironmentExchangeBean

/**
 * author ：Peakmain
 * createTime：2023/9/27
 * mail:2726449200@qq.com
 * describe：
 */
class DebugToolsManager private constructor() {
    private var mEnvironmentExchangeBeans: MutableList<EnvironmentExchangeBean> = ArrayList()
    var mSelectEnvironmentCallback: ((EnvironmentExchangeBean) -> Unit)? = null

    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DebugToolsManager()
        }
    }

    fun initEnvironmentExchangeBeanList(
        environmentExchangeBeans: MutableList<EnvironmentExchangeBean>,
        selectEnvironmentCallback: ((EnvironmentExchangeBean) -> Unit)? = null,
    ): DebugToolsManager {
        this.mEnvironmentExchangeBeans = environmentExchangeBeans
        this.mSelectEnvironmentCallback = selectEnvironmentCallback
        return this
    }

    fun addEnvironmentExchangeBean(environmentExchangeBean: EnvironmentExchangeBean): DebugToolsManager {
        mEnvironmentExchangeBeans.add(environmentExchangeBean)
        return this
    }

    fun getEnvironmentExchangeList(): MutableList<EnvironmentExchangeBean> {
        return mEnvironmentExchangeBeans
    }

    fun show(context: FragmentActivity) {
        val clazz = Class.forName("com.peakmain.debug.DebugToolDialogFragment")
        val target = clazz.getConstructor().newInstance() as DebugToolDialogFragment
        target.show(context.supportFragmentManager, "debug_tool")
    }
}