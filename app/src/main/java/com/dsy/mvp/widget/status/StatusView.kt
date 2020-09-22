package com.dsy.mvp.widget.status

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.dsy.mvp.R
import kotlinx.android.synthetic.main.state_view.view.*
import org.jetbrains.anko.sdk27.listeners.onClick

@SuppressLint("ViewConstructor")
class StatusView(context: Context, private var retryTask: Runnable? = null) : LinearLayout(context) {

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        inflate(context, R.layout.state_view, this)
        setBackgroundColor(resources.getColor(R.color.white))
        state_btn.onClick {
            retryTask?.run()
        }
    }

    var currentState = LoadStatus.SUCCESS

    fun setStatus(status: LoadStatus) {
        this.currentState = status
        var show = true
        when (status) {
            LoadStatus.SUCCESS -> {
                show = false
            }
            LoadStatus.LOADING -> {
                state_loading.isVisible = true
                state_img.isVisible = false
                state_text.isVisible = false
                state_btn.isVisible = false
                ((state_loading.background) as AnimationDrawable).start()
            }
            else -> {
                state_loading.isVisible = false
                state_img.isVisible = true
                state_text.isVisible = true
                state_img.setImageResource(status.emptyImg)
                state_text.setText(status.emptyTxt)
                if (status.emptyBtn != 0) {
                    state_btn.isVisible = true
                    state_btn.setText(status.emptyBtn)
                } else {
                    state_btn.isVisible = false
                }
            }
        }
        isVisible = show
    }

}