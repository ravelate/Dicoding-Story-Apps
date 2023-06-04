package com.fianku.firstsubmission.customView

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.fianku.firstsubmission.R

class EmailEditText : AppCompatEditText {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if(s.length > 0){
            if (!isValidEmail(s)){
                error = context.getString(R.string.invalid_email)
            }
        }
    }
    private fun isValidEmail(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}