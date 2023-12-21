package com.peakmain.debug.manager

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
    private var mH5EnvironmentExchangeBeans: MutableList<EnvironmentExchangeBean> = ArrayList()
    var mSelectEnvironmentCallback: ((EnvironmentExchangeBean) -> Unit)? = null
    var mSelectH5EnvironmentCallback: ((EnvironmentExchangeBean) -> Unit)? = null
    var nativeEnvironmentUrl: String=H5PreferenceManager.instance.getNativeEnvironmentUrl()
    var h5EnvironmentUrl: String=H5PreferenceManager.instance.getH5EnvironmentUrl()

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

    fun initH5EnvironmentExchangeBeanList(
        environmentExchangeBeans: MutableList<EnvironmentExchangeBean>,
        selectH5EnvironmentCallback: ((EnvironmentExchangeBean) -> Unit)? = null,
    ): DebugToolsManager {
        this.mH5EnvironmentExchangeBeans = environmentExchangeBeans
        this.mSelectH5EnvironmentCallback = selectH5EnvironmentCallback
        return this
    }

    fun addEnvironmentExchangeBean(environmentExchangeBean: EnvironmentExchangeBean): DebugToolsManager {
        mEnvironmentExchangeBeans.add(environmentExchangeBean)
        return this
    }

    fun addH5EnvironmentExchangeBean(environmentExchangeBean: EnvironmentExchangeBean): DebugToolsManager {
        mH5EnvironmentExchangeBeans.add(environmentExchangeBean)
        return this
    }

    fun getEnvironmentExchangeList(): MutableList<EnvironmentExchangeBean> {
        return mEnvironmentExchangeBeans
    }

    fun getH5EnvironmentExchangeList(): MutableList<EnvironmentExchangeBean> {
        return mH5EnvironmentExchangeBeans
    }

    fun show(context: FragmentActivity) {
        val clazz = Class.forName("com.peakmain.debug.DebugToolDialogFragment")
        val target = clazz.getConstructor().newInstance() as DebugToolDialogFragment
        target.show(context.supportFragmentManager, "debug_tool")
    }
}