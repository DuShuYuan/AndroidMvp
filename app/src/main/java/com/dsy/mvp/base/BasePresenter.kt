package com.dsy.mvp.base

import com.dsy.mvp.base.impl.IPresenter
import com.dsy.mvp.base.impl.IView
import com.lzy.okgo.OkGo

abstract class BasePresenter<T : IView> : IPresenter {
    protected var mView: T? = null
    @Suppress("UNCHECKED_CAST")
    override fun attachView(iView: IView) {
        mView = iView as T
    }

    override fun detachView() {
        OkGo.getInstance().cancelTag(this)
        mView = null
    }
}