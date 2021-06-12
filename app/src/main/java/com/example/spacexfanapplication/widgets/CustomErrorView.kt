package com.example.spacexfanapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.spacexfanapplication.R

class CustomErrorView : ConstraintLayout {
    private var txtErrorHeading: TextView? = null
    private var txtErrorDescription: TextView? = null
    private var imgErrorIcon: ImageView? = null
    private var btnError: Button? = null
    private var errorView: LinearLayout? = null

    constructor(context: Context) : super(context) {
        initWithAttrs(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        if (!isInEditMode)
            initWithAttrs(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        if (!isInEditMode)
            initWithAttrs(context, attrs, defStyleAttr)
    }

    /**
     * This method is used to set the Custom fonts
     */
    private fun initWithAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val rootView =
            LayoutInflater.from(context).inflate(R.layout.error_layout, this)
        txtErrorHeading = rootView.findViewById(R.id.txtErrorHeading)
        txtErrorDescription = rootView.findViewById(R.id.txtErrorDescription)
        imgErrorIcon = rootView.findViewById(R.id.imgErrorIcon)
        btnError = rootView.findViewById(R.id.btnError)
        errorView = rootView.findViewById(R.id.errorView)
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomErrorView,
            defStyleAttr,
            0
        )
        val show = a.getBoolean(R.styleable.CustomErrorView_show, false)
        if (show) {
            show()
        } else {
            hide()
        }
        a.recycle()
    }

    fun showMessages(heading: String? = null, description: String? = null) {
        if (heading == null) {
            txtErrorHeading?.visibility = View.GONE
        } else {
            txtErrorHeading?.text = heading
            txtErrorHeading?.visibility = View.VISIBLE
        }

        if (description == null) {
            txtErrorDescription?.visibility = View.GONE
        } else {
            txtErrorDescription?.text = description
            txtErrorDescription?.visibility = View.VISIBLE
        }
    }


    fun showButton() {
        btnError?.visibility = View.VISIBLE
    }

    fun setButtonClick(onClickListener: OnClickListener) {
        btnError?.setOnClickListener(onClickListener)
    }

    fun showImage(image: Int? = null) {
        if (image == null ) {
            return
        }
        imgErrorIcon?.visibility = View.VISIBLE
        Glide.with(imgErrorIcon?.context!!).load(image).into(imgErrorIcon!!)
    }

    fun show() {
        errorView?.visibility = View.VISIBLE
    }

    fun hide() {
        errorView?.visibility = View.GONE
    }


}
