package com.peakmain.debug.ext

import android.content.Context
import androidx.core.content.ContextCompat
import com.peakmain.debug.R
import com.peakmain.ui.widget.ShapeTextView

/**
 * author ：Peakmain
 * createTime：2023/07/31
 * mail:2726449200@qq.com
 * describe：
 */ fun ShapeTextView.updateTextStyle(isSelect:Boolean,context:Context) {
    if (isSelect) {
        setTextColor(
            ContextCompat.getColor(
                context,
                R.color.white
            )
        )
        setNormalBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.amber_900
            )
        )
    } else {
        setTextColor(
            ContextCompat.getColor(
                context,
                R.color.ui_color_272A2B
            )
        )
        setNormalBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.white
            )
        )
    }
}