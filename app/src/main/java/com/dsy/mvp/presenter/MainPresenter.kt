package com.dsy.mvp.presenter

import com.dsy.mvp.base.BasePresenter
import com.dsy.mvp.bean.SimpleBean
import com.dsy.mvp.net.JsonCallback
import com.dsy.mvp.net.ServerApi
import com.lzy.okgo.model.Response

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {
    override fun reqData() {
        ServerApi.getAndroid(this, 1).execute(object : JsonCallback<SimpleBean>() {
            override fun onSuccess(response: Response<SimpleBean>) {
                mView?.onSuccess()
            }
        })
    }
}