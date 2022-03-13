package com.peakmain.debug.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peakmain.debug.R
import com.peakmain.debug.utils.CrashHelper
import com.peakmain.debug.utils.CrashUtils
import java.io.File

/**
 * author ：Peakmain
 * createTime：2022/3/13
 * mail:2726449200@qq.com
 * describe：
 */
class CrashLogActivity : AppCompatActivity() {
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.debug_activity_crash_log)
        mToolbar = findViewById(R.id.view_root)
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener {
            finish()
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        val crashFiles = CrashUtils.getCrashFiles()
        mRecyclerView=findViewById<RecyclerView>(R.id.recycler_view).apply{
            layoutManager=LinearLayoutManager(this@CrashLogActivity)
            adapter=CrashLogAdapter(crashFiles)
        }
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.shape_divider_debug_tool
            )!!
        )
        mRecyclerView.addItemDecoration(decoration)
    }
    inner class CrashLogAdapter(private val crashFiles: Array<File>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return object : RecyclerView.ViewHolder(
                layoutInflater.inflate(
                    R.layout.debug_recycler_crash_log_item,
                    parent,
                    false
                )
            ) {}
        }

        override fun getItemCount(): Int {
            return crashFiles.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val file = crashFiles.get(position)
            holder.itemView.findViewById<TextView>(R.id.tv_debug_title).text = file.name
            holder.itemView.findViewById<TextView>(R.id.tv_debug_share).setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra("subject", "")
                intent.putExtra("body", "")

                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(
                        this@CrashLogActivity,
                        "${packageName}.fileProvider",
                        file
                    )
                } else {
                    Uri.fromFile(file)
                }
                intent.putExtra(Intent.EXTRA_STREAM, uri)//添加文件
                if (file.name.endsWith(".txt")) {
                    intent.type = "text/plain"//纯文本
                } else {
                    intent.type = "application/octet-stream" //二进制文件流
                }
                startActivity(Intent.createChooser(intent, "分享Crash 日志文件"))
            }
        }

    }
}