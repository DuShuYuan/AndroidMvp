package com.dsy.mvp.base

import com.dsy.mvp.base.impl.IPresenter
import com.dsy.mvp.base.impl.IView

class SimplePresenter : BasePresenter<SimpleContract.View>(), SimpleContract.Presenter

interface SimpleContract {
    interface Presenter : IPresenter
    interface View : IView
}