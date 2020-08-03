package com.dsy.mvp.presenter

import com.dsy.mvp.base.impl.IPresenter
import com.dsy.mvp.base.impl.IView

interface MainContract {

    interface Presenter : IPresenter{
        fun reqData()
    }
    interface View : IView {
        fun onSuccess()
    }
}