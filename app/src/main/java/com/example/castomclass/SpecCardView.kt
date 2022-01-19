package com.example.castomclass

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.example.myservices.R
import com.example.myservices.R.styleable.KliCardView

class SpecCardView: CardView {
        var specid: Int=0
        var spectel: String = "tel:"
        constructor(context: Context) : this(context, null)
        constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
        ) {
            initialize(attrs)
        }

        fun initialize(attrs: AttributeSet?) {
            val typpedArray = context.theme.obtainStyledAttributes(
                attrs,
                KliCardView,
                0,
                0)
            specid=typpedArray.getInteger(R.styleable.SpecCardView_specid,0)
            spectel= typpedArray.getString(R.styleable.SpecCardView_spectel).toString()
        }
    }
