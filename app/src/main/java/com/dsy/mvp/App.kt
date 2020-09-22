package com.dsy.mvp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.dsy.mvp.component.CrashHandler
import com.dsy.mvp.utils.MLog
import com.dsy.mvp.widget.status.StatusHolder
import com.dsy.mvp.widget.status.StatusView
import com.lzy.okgo.OkGo
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
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

        StatusHolder.initDefault { holder, convertView, status ->
            val sView: StatusView = if (convertView != null && convertView is StatusView) {
                convertView
            } else {
                StatusView(holder.context, holder.retryTask)
            }
            sView.setStatus(status)
            return@initDefault sView
        }


        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
//            layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white) //全局设置主题颜色
            return@setDefaultRefreshHeaderCreator MaterialHeader(context).setColorSchemeResources(R.color.colorAccent)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f).setFinishDuration(0)
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