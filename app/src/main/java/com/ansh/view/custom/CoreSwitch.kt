package com.ansh.view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch

class CoreSwitch : Switch {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun toggle() {
        isChecked = isChecked
    }
}
