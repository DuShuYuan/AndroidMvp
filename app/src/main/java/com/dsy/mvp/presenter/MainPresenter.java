package com.dsy.mvp.presenter;

import com.dsy.mvp.base.BasePresenter;
import com.dsy.mvp.bean.SimpleBean;
import com.dsy.mvp.net.JsonCallback;
import com.dsy.mvp.net.ServerApi;
import com.lzy.okgo.model.Response;


public class MainPresenter extends BasePresenter<MainView>{

    public void checkVersion(String currentVersion) {
        ServerApi.getAndroid(this,1).execute(new JsonCallback<SimpleBean>() {
            @Override
            public void onSuccess(Response<SimpleBean> response) {
                mView.showUpdateDialog("");
            }
        });
    }
}
