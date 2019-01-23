package com.ansh.snippets.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioButton

class CustomRadioButton : RadioButton {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun toggle() {
        isChecked = !isChecked
    }
}