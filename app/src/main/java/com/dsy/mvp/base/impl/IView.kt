package com.dsy.mvp.base.impl

import android.app.Activity
import androidx.annotation.StringRes
import com.dsy.mvp.widget.status.LoadStatus

interface IView {
    val mActivity: Activity
    fun toast(msg: CharSequence)
    fun toast(@StringRes id: Int)
    fun showLoadingDialog()
    fun hideLoadingDialog()
    fun showLoadingView()
    fun showContentView()
    fun showErrorView()
    fun showEmptyView()
    fun showCustomView(status: LoadStatus)
}