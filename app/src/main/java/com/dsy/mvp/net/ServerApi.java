package com.dsy.mvp.net;


import com.dsy.mvp.bean.SimpleBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;

public class ServerApi {

    public static GetRequest<SimpleBean> getAndroid(Object tag, int page) {

        return OkGo.<SimpleBean>get(Urls.UrlSipmle+page)
                .tag(tag);
    }
}
