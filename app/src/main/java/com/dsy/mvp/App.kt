package com.dsy.mvp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.dsy.mvp.component.CrashHandler
import com.dsy.mvp.ui.adapter.EmptyAdapter
import com.dsy.mvp.ui.adapter.ErrorAdapter
import com.dsy.mvp.ui.adapter.LoadingAdapter
import com.dsy.mvp.utils.MLog
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.LoadingHelper.AdapterPool
import com.dylanc.loadinghelper.ViewType
import com.lzy.okgo.OkGo
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import java.util.logging.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Utils.init(this)
        MLog.initLogger()
        //错误捕获工具
        CrashHandler(this)
        initOkgo()
        LoadingHelper.setDefaultAdapterPool {
            register(ViewType.LOADING, LoadingAdapter())
            register(ViewType.ERROR, ErrorAdapter())
            register(ViewType.EMPTY, EmptyAdapter())
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initOkgo() {
        val builder = OkHttpClient.Builder()
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
        //全局的连接超时时间
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS)
        val loggingInterceptor = HttpLoggingInterceptor("OkGo")
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO)
        builder.addInterceptor(loggingInterceptor)
        OkGo.getInstance().init(this).okHttpClient = builder.build()
    }

    companion object {
        @get:Synchronized
        lateinit var instance: App
    }
}