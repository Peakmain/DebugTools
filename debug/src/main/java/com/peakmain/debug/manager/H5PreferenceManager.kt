package com.peakmain.debug.manager

import com.peakmain.ui.utils.PreferencesUtil

/**
 * author ：Peakmain
 * createTime：2023/12/21
 * mail:2726449200@qq.com
 * describe：
 */
internal class H5PreferenceManager private constructor() {
    private var mPreferencesUtils: PreferencesUtil? = null

    init {
        mPreferencesUtils = PreferencesUtil.instance
    }

    companion object {
        private const val environmentExchangeKey = "environmentExchangeKey"
        private const val h5EnvironmentExchangeKey = "h5EnvironmentExchangeKey"

        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            H5PreferenceManager()
        }
    }

    fun getH5EnvironmentUrl(): String {
        return mPreferencesUtils?.getParam(h5EnvironmentExchangeKey, "") as String? ?: ""
    }

    fun saveH5EnvironmentUrl(url: String) {
        mPreferencesUtils?.saveParam(h5EnvironmentExchangeKey, url)
    }

    fun getNativeEnvironmentUrl(): String {
        return mPreferencesUtils?.getParam(environmentExchangeKey, "") as String? ?: ""
    }

    fun saveNativeEnvironmentUrl(url: String) {
        mPreferencesUtils?.saveParam(environmentExchangeKey, url)
    }
}