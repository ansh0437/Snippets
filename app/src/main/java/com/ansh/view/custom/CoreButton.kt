package com.ansh.view.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatButton
import android.util.AttributeSet
import android.util.StateSet
import com.ansh.R
import com.ansh.extensions.toDP

class CoreButton : AppCompatButton {

    private var btnStyle = 1
    private var cornerRadius = 0
    private var strokeWidth = 0
    private var fillColor = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet, defStyle: Int = 0) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.CoreButton, defStyle, 0)

        applyFont(arr.getString(R.styleable.CoreButton_font_name))

        btnStyle = arr.getInt(R.styleable.CoreButton_btn_style, 1)
        cornerRadius = arr.getInt(R.styleable.CoreButton_corner_radius, 0)
        strokeWidth = arr.getInt(R.styleable.CoreButton_stroke_width, 0)
        fillColor =
                arr.getColor(R.styleable.CoreButton_fill_color, ContextCompat.getColor(context, R.color.colorPrimaryDark))
        val strokeColor =
                arr.getColor(
                        R.styleable.CoreButton_stroke_color,
                        ContextCompat.getColor(context, android.R.color.transparent)
                )

        if (btnStyle == 1) {
            background = getDrawable(fillColor, strokeColor)
        } else {
            val defaultColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
            val textColor = arr.getColor(R.styleable.CoreButton_text_color, defaultColor)
            val selectorTextColor = arr.getColor(R.styleable.CoreButton_selector_text_color, defaultColor)
            val selectorFillColor = arr.getColor(R.styleable.CoreButton_selector_fill_color, defaultColor)
            val selectorStrokeColor = arr.getColor(R.styleable.CoreButton_selector_stroke_color, defaultColor)
            textSelectorBG(textColor, selectorTextColor)
            selectorBG(fillColor, strokeColor, selectorFillColor, selectorStrokeColor)
        }

        arr.recycle()
    }

    fun setFillColor(value: Int) {
        fillColor = value
        background = getDrawable(value, ContextCompat.getColor(context, android.R.color.transparent))
        invalidate()
    }

    private fun applyFont(fontName: String?) {
        if (!fontName.isNullOrBlank())
            typeface = Typeface.createFromAsset(context.assets, "fonts/$fontName")
    }

    private fun textSelectorBG(textColor: Int, selectorTextColor: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_pressed), intArrayOf(-android.R.attr.state_pressed))
        val colors = intArrayOf(selectorTextColor, textColor)
        setTextColor(ColorStateList(states, colors))
    }

    private fun selectorBG(fillColor: Int, strokeColor: Int, selectorFillColor: Int, selectorStrokeColor: Int) {
        val bg = StateListDrawable()
        bg.addState(intArrayOf(android.R.attr.state_pressed), getDrawable(selectorFillColor, selectorStrokeColor))
        bg.addState(StateSet.NOTHING, getDrawable(fillColor, strokeColor))
        background = bg
    }

    private fun getDrawable(fillColor: Int, strokeColor: Int): Drawable {
        val bg = GradientDrawable()
        bg.shape = GradientDrawable.RECTANGLE
        bg.cornerRadius = cornerRadius.toDP
        bg.setStroke(strokeWidth.toDP.toInt(), strokeColor)
        bg.setColor(fillColor)
        return bg
    }

    /*
    USE CASES

    <com.ansh.views.CoreButton
    android:id="@+id/one"
    android:layout_width="200dp"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:text="Hello World"
    app:fill_color="@color/colorAccent"
    app:font_name="@string/roboto_bold" />

    <com.ansh.views.CoreButton
    android:layout_width="200dp"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:text="Hello World"
    app:corner_radius="10"
    app:fill_color="@{vm.clicked ? @color/colorPrimary : @color/colorAccent}"
    app:font_name="@string/roboto_bold" />

    <com.ansh.views.CoreButton
    android:layout_width="200dp"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:text="Hello World"
    app:corner_radius="10"
    app:fill_color="@color/transparent"
    app:font_name="@string/roboto_bold"
    app:stroke_color="@color/colorAccent"
    app:stroke_width="3" />

    <com.ansh.views.CoreButton
    android:layout_width="200dp"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:text="Hello World"
    app:corner_radius="10"
    app:fill_color="@color/colorAccent"
    app:font_name="@string/roboto_bold"
    app:stroke_color="@color/colorPrimaryDark"
    app:stroke_width="3" />

    <com.ansh.views.CoreButton
    android:layout_width="200dp"
    android:layout_height="wrap_content"
    android:layout_margin="10dp"
    android:text="Hello World"
    app:btn_style="focusable"
    app:corner_radius="10"
    app:fill_color="@color/transparent"
    app:font_name="@string/roboto_bold"
    app:selector_fill_color="@color/colorPrimary"
    app:selector_stroke_color="@color/colorPrimaryDark"
    app:selector_text_color="#fff"
    app:stroke_color="@color/colorAccent"
    app:stroke_width="3"
    app:text_color="#000" />
    */
}