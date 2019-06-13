package com.ansh.view.custom

import android.content.Context
import android.graphics.Color
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView

class ExpandableTextView : AppCompatTextView {

    private var showingLine = 3

    private var showMore = "Show more"
    private var showLess = "Show less"
    private val dots = "..."

    private val MAGIC_NUMBER = 5

    private var showMoreTextColor = Color.RED
    private var showLessTextColor = Color.RED

    private var mainText: String? = null

    private var isAlreadySet: Boolean = false

    private var onStateChange: IStateChange? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        maxLines = showingLine
        init()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mainText = text.toString()
    }

    private fun init() {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val text = text.toString()
                if (!isAlreadySet) {
                    mainText = getText().toString()
                    isAlreadySet = true
                }
                var showingText = ""

                if (showingLine >= lineCount) {
                    try {
                        throw Exception("Line Number cannot exceed total line count")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    return
                }

                var start = 0
                var end: Int
                for (i in 0 until showingLine) {
                    end = layout.getLineEnd(i)
                    showingText += text.substring(start, end)
                    start = end
                }

                var newText = showingText.substring(
                    0,
                    showingText.length - (dots.length + showMore.length + MAGIC_NUMBER)
                )
                newText += dots + showMore

                setText(newText)

                showMoreButton()
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun showMoreButton() {
        val spannableString = SpannableString(text)

        spannableString.setSpan(object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

            override fun onClick(@Nullable view: View) {
                maxLines = Integer.MAX_VALUE
                text = mainText
                showLessButton()
            }
        }, text.length - (dots.length + showMore.length), text.length, 0)

        spannableString.setSpan(
            ForegroundColorSpan(showMoreTextColor),
            text.length - (dots.length + showMore.length),
            text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        movementMethod = LinkMovementMethod.getInstance()
        setText(spannableString, TextView.BufferType.SPANNABLE)

        onStateChange?.onChange(true)
    }

    private fun showLessButton() {
        val text = text.toString() + dots + showLess
        val spannableString = SpannableString(text)

        spannableString.setSpan(object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }

            override fun onClick(@Nullable view: View) {
                maxLines = showingLine
                init()
            }
        }, text.length - (dots.length + showLess.length), text.length, 0)

        spannableString.setSpan(
            ForegroundColorSpan(showLessTextColor),
            text.length - (dots.length + showLess.length),
            text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        movementMethod = LinkMovementMethod.getInstance()
        setText(spannableString, TextView.BufferType.SPANNABLE)

        onStateChange?.onChange(false)
    }

    fun updateView(isOpen: Boolean) {
        if (isOpen) {
            showLessButton()
        } else {
            init()
        }
    }

    fun addStateChangeListener(listener: IStateChange) {
        onStateChange = listener
    }

    interface IStateChange {
        fun onChange(isShrink: Boolean)
    }
}