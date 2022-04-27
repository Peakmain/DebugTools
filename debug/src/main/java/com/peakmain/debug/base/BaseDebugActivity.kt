package com.peakmain.debug.base

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.peakmain.basiclibrary.base.activity.BaseActivity
import com.peakmain.basiclibrary.base.viewmodel.BaseViewModel
import com.peakmain.basiclibrary.utils.StatusBarUtils
import com.peakmain.debug.R
import com.peakmain.ui.navigationbar.DefaultNavigationBar

/**
 * author ：Peakmain
 * createTime：2022/3/14
 * mail:2726449200@qq.com
 * describe：
 */
abstract class BaseDebugActivity<T : ViewDataBinding, E : BaseViewModel> : BaseActivity<T, E>() {

    lateinit var mDefaultNavigationBar: DefaultNavigationBar.Builder
    override fun initBefore() {
        mDefaultNavigationBar= DefaultNavigationBar.Builder(this, findViewById(android.R.id.content))
            .setTitleTextColor(android.R.color.white)
            .setToolbarBackgroundColor(R.color.ui_color_2F73F6)
            .setNavigationOnClickListener(View.OnClickListener {
                finish()
            })
            .setDisplayHomeAsUpEnabled(true)
            .setRightResId(R.drawable.ic_debug_clear_all_24)
            .hideRightView()
            .hideLeftText()
        StatusBarUtils.setColor(this, ContextCompat.getColor(this,R.color.ui_color_2F73F6),255)
    }
}