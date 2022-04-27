package com.peakmain.debug.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.peakmain.debug.DebugFileProvider
import com.peakmain.debug.R
import java.io.File

/**
 * author ：Peakmain
 * createTime：2022/3/14
 * mail:2726449200@qq.com
 * describe：
 */
class CrashLogAdapter(val context: Context, private val crashFiles: Array<File>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(context).inflate(
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
                DebugFileProvider.getUriForFile(
                    context,
                    "${context.packageName}.peakmain.debug.fileProvider",
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
            context.startActivity(Intent.createChooser(intent, "分享Crash 日志文件"))
        }
    }

}