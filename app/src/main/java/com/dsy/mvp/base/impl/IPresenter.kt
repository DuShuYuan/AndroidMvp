package com.dsy.mvp.base.impl

interface IPresenter {
    /**
     * 注入View，使之能够与View相互响应
     */
    fun attachView(iView: IView)

    /**
     * 释放资源，如果使用了网络请求 可以在此执行IModel.cancelRequest()
     */
    fun detachView()
}