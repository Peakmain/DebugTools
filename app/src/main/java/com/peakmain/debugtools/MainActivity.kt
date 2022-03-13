package com.peakmain.debugtools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.peakmain.debug.DebugToolDialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_name).setOnClickListener {
            val clazz = Class.forName("com.peakmain.debug.DebugToolDialogFragment")
            val target = clazz.getConstructor().newInstance() as DebugToolDialogFragment
            target.show(supportFragmentManager,"debug_tool")
        }
    }
}