package com.dsy.mvp;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.dsy.mvp.component.CrashHandler;
import com.dsy.mvp.utils.MLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class App extends Application {

    private static App instance;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(this);
        MLog.initLogger();
        //错误捕获工具
        CrashHandler.getInstance().init(getApplicationContext());
        initOkgo();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initOkgo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        OkGo.getInstance().init(this).setOkHttpClient(builder.build());
    }

}
