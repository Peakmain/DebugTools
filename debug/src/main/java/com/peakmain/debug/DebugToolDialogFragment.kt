package com.peakmain.debug

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peakmain.debug.annotation.PDebug
import java.lang.reflect.Method

/**
 * author ：Peakmain
 * createTime：2022/3/13
 * mail:2726449200@qq.com
 * describe：
 */
class DebugToolDialogFragment : AppCompatDialogFragment() {
    private val debugTools = arrayOf(DebugTools::class.java)
    private lateinit var mRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = dialog?.window?.findViewById<ViewGroup>(android.R.id.content) ?: container
        val view = inflater.inflate(R.layout.debug_dialog_tool, parent, false)

        dialog?.window?.setLayout(
            (getScreenWidth() * 0.7f).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_background_debug_tool)
        mRecyclerView=view.findViewById(R.id.debug_recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemDecoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.shape_divider_debug_tool
            )!!
        )

        val list = mutableListOf<DebugToolBean>()
        val size = debugTools.size
        for (index in 0 until size) {
            val claz = debugTools[index]
            val target = claz.getConstructor().newInstance()
            val declaredMethods = target.javaClass.declaredMethods
            for (method in declaredMethods) {
                var title = ""
                var desc = ""
                var enable = false
                val annotation = method.getAnnotation(PDebug::class.java)

                if (annotation != null) {
                    title = annotation.name
                    desc = annotation.desc
                    enable = true
                } else {
                    method.isAccessible = true
                    title = method.invoke(target) as String
                }

                val func = DebugToolBean(title, desc, method, enable, target)
                list.add(func)
            }
        }

        mRecyclerView.addItemDecoration(itemDecoration)
        mRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = DebugToolAdapter(list)
    }


    inner class DebugToolAdapter(private val list: List<DebugToolBean>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = layoutInflater.inflate(R.layout.debug_recycler_tool_item, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val debugFunction = list[position]

            val itemTitle = holder.itemView.findViewById<TextView>(R.id.tv_debug_title)
            val itemDesc = holder.itemView.findViewById<TextView>(R.id.tv_debug_desc)

            itemTitle.text = debugFunction.name
            if (TextUtils.isEmpty(debugFunction.desc)) {
                itemDesc.visibility = View.GONE
            } else {
                itemDesc.visibility = View.VISIBLE
                itemDesc.text = debugFunction.desc
            }

            if (debugFunction.enable) {
                holder.itemView.setOnClickListener {
                    debugFunction.invoke(activity!!)
                    dismiss()
                }
            }
        }

    }


    private fun getScreenWidth(): Int = resources.displayMetrics.widthPixels
}

data class DebugToolBean(
    val name: String,
    val desc: String,
    val method: Method,
    val enable: Boolean,
    val target: Any
) {
    fun invoke(context: Context) {
        method.invoke(target, context)
    }
}