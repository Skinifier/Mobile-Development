package com.capstone.skinifier.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.skinifier.R

class MyButtonOutline : AppCompatButton {

    constructor(context: Context) : super(context) // untuk di Activity/Fragment
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)  // untuk di XML

    private var txtColor: Int
    private var enabledBackground: Drawable
    private var disabledBackground: Drawable
    init {
        txtColor = ContextCompat.getColor(context, R.color.primary)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.my_button_outline) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.my_button_outline) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
    }
}