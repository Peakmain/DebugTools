package com.peakmain.debugtools

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.basiclibrary.network.RetrofitManager
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.debug.base.EnvironmentExchangeBean
import com.peakmain.debug.manager.DebugToolsManager
import com.peakmain.ui.utils.LogUtils
import com.peakmain.ui.utils.ToastUtils

class MainActivity : AppCompatActivity() {
    private var api = RetrofitManager.createService(WanAndroidService::class.java) {
        RetrofitUtils.create(WanAndroidService::class.java)
    }
    var mEnvironmentExchangeBeans: MutableList<EnvironmentExchangeBean> = ArrayList()
    var mH5EnvironmentExchangeBeans: MutableList<EnvironmentExchangeBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        findViewById<TextView>(R.id.tv_name).setOnClickListener {
            DebugToolsManager.instance
                .initEnvironmentExchangeBeanList(mEnvironmentExchangeBeans) {
                    ToastUtils.showLong("当前选中的环境是:${it.title},url是:${it.url}")
                }.initH5EnvironmentExchangeBeanList(mH5EnvironmentExchangeBeans){
                    LogUtils.e("当前选中的H5环境是:${it.title},url是:${it.url}")
                    ToastUtils.showLong("当前选中的H5环境是:${it.title},url是:${it.url}")
                }
                .show(this)
        }
        RetrofitManager.createData(api.getBannerJson(), object : ApiStatus<Any>() {
            override fun error(exception: Exception) {
            }

            override fun success(t: Any) {
                // Log.e("TAG",t.toString())

            }

        })
        RetrofitManager.createData(api.getListJson(1, 294), object : ApiStatus<Any>() {
            override fun error(exception: Exception) {
            }

            override fun success(t: Any) {
                // Log.e("TAG",t.toString())

            }

        })
        RetrofitManager.createData(api.loginUser("test", "123456"), object : ApiStatus<Any>() {
            override fun error(exception: Exception) {
            }

            override fun success(t: Any) {
                Log.e("TAG", t.toString())
            }

        })
    }

    private fun initData() {
        mEnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa0",
                "https://www.baidu.com",
                false
            )
        )
        mEnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa1",
                "https://www.baidu1.com",
                true
            )
        )
        mEnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa2",
                "https://www.baidu2.com",
                true
            )
        )
        mEnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa3",
                "https://www.baidu3.com",
                false
            )
        )
        mEnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa3",
                "https://www.baidu3.com",
                false
            )
        )
        mEnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa3",
                "https://www.baidu3.com",
                false
            )
        )

        mEnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa3",
                "https://www.baidu3.com",
                false
            )
        )
        mEnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa3",
                "https://www.baidu3.com",
                false
            )
        )
        mH5EnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa0",
                "https://www.baidu.com",
                false
            )
        )
        mH5EnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa1",
                "https://www.baidu1.com",
                true
            )
        )

        mH5EnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa0",
                "https://www.baidu.com",
                false
            )
        )
        mH5EnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa0",
                "https://www.baidu.com",
                false
            )
        )
        mH5EnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa0",
                "https://www.baidu.com",
                false
            )
        )
        mH5EnvironmentExchangeBeans.add(
            EnvironmentExchangeBean(
                "qa0",
                "https://www.baidu.com",
                false
            )
        )
    }
}