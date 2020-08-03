package com.dsy.mvp.base.impl

import android.app.Activity

interface IView {
    val mActivity: Activity
    fun toast(msg: String)
    fun toast(id: Int)
    fun showLoadingDialog()
    fun hideLoadingDialog()
}