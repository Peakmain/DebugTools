package com.peakmain.debugtools

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.basiclibrary.network.RetrofitManager
import com.peakmain.basiclibrary.network.status.ApiStatus
import com.peakmain.debug.log.HttpLoggingActivity

class MainActivity : AppCompatActivity() {
    private var api = RetrofitManager.createService(WanAndroidService::class.java) {
        RetrofitUtils.create(WanAndroidService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_name).setOnClickListener {
           /* val clazz = Class.forName("com.peakmain.debug.DebugToolDialogFragment")
            val target = clazz.getConstructor().newInstance() as DebugToolDialogFragment
            target.show(supportFragmentManager, "debug_tool")*/
            startActivity(Intent(this, HttpLoggingActivity::class.java))
        }
        RetrofitManager.createData(api.getBannerJson(), object : ApiStatus<Any>() {
            override fun error(exception: Exception) {
            }

            override fun success(t: Any) {
               // Log.e("TAG",t.toString())

            }

        })
        RetrofitManager.createData(api.getListJson(1,294), object : ApiStatus<Any>() {
            override fun error(exception: Exception) {
            }

            override fun success(t: Any) {
               // Log.e("TAG",t.toString())

            }

        })
        RetrofitManager.createData(api.loginUser("test","123456"),object :ApiStatus<Any>(){
            override fun error(exception: Exception) {
            }

            override fun success(t: Any) {
                Log.e("TAG",t.toString())
            }

        })
    }
}