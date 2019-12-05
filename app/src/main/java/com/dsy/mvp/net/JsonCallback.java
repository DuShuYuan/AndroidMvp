package com.dsy.mvp.net;

import android.os.Handler;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
import com.dsy.mvp.bean.BaseBean;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class JsonCallback<T> extends AbsCallback<T> {


    public JsonCallback() {
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
    }


    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) {
            throw new DataException(-1,"返回内容为空");
        }
        Type genType = getClass().getGenericSuperclass();
        Type type, rawType = null, typeArgument;
        // 这里得到第二层泛型的所有的类型 A<B>
        type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        JsonReader reader = new JsonReader(body.charStream());
        T data = new Gson().fromJson(reader, type);

        BaseBean bean ;
        try {
            bean = ((BaseBean) data);
        } catch (Exception e) {
            return data;
        }
        //根据接口定义自行修改code解析
        if (bean.getCode() == 0) {//成功
            return data;
        } else {//异常
            throw new DataException(bean.getCode(),bean.getMsg());
        }

/*
        if (type instanceof ParameterizedType) {
            //这里得到第二层数据的真实类型：A
            rawType = ((ParameterizedType) type).getRawType();
            //这里得到第二层数据的泛型的真实类型：B
//            typeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        }

        //如果T类型是BaseBean或者BaseBean<xxBean>则进行code解析
        if (type  == BaseBean.class || rawType == BaseBean.class) {
            MLog.i("=======================================");
            int code = ((BaseBean) data).getCode();
            //根据接口定义自行修改code解析
            if (code == 0) {//成功

                return data;
            } else if (code == 104) {//异常，根据实际情况修改code和message
                throw new DataException("用户授权信息无效");
            } else {
                throw new DataException( code + "：" + ((BaseBean) data).getMsg());
            }
        }
*/


//        return data;

    }


    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Throwable exception = response.getException();
        //可自行修改
        String msg;
        if (exception instanceof UnknownHostException || exception instanceof ConnectException) {
            msg="网络连接失败，请连接网络！";
        } else if (exception instanceof SocketTimeoutException) {
            msg="网络请求超时";
        } else if (exception instanceof HttpException) {
            msg="服务端响应码404和500了";
        } else if (exception instanceof StorageException) {
            msg="sd卡不存在或者没有权限";
        } else if (exception instanceof DataException) {
            msg=exception.getMessage();
        }else {
            msg=exception.getMessage();
        }
        System.out.println(msg);

        showToast(msg);

        exception.printStackTrace();
    }

    private void showToast(final String msg){
        if (!TextUtils.isEmpty(msg)){
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort(msg);
                }
            });
        }
    }

}
