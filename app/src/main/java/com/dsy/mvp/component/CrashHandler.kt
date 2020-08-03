package com.dsy.mvp.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.PathUtils
import com.dsy.mvp.App
import com.dsy.mvp.BuildConfig
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 */
@SuppressLint("SimpleDateFormat")
class CrashHandler(context: Context) : Thread.UncaughtExceptionHandler {
    //日志路径
    private var path= PathUtils.getExternalAppCachePath() + "/log/"
    //获取系统默认的UncaughtException处理器
    private var mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        //保存日志文件
        val fileName = saveCrashInfo2File(ex)
        //上传文件
        uploadExceptionToServer(fileName)
        if (BuildConfig.DEBUG) {
            //让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex)
        } else {
            try {
                //延迟3秒上传日志
                Thread.sleep(3000)
                //退出App
                AppUtils.exitApp()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {
        val sb = StringBuilder()
        val head = """
            ************* Log Head ****************
            Time Of Crash      : ${SimpleDateFormat("MM-dd HH:mm:ss").format(Date(System.currentTimeMillis()))}
            Device Manufacturer: ${Build.MANUFACTURER}
            Device Model       : ${Build.MODEL}
            Android Version    : ${Build.VERSION.RELEASE}
            Android SDK        : ${Build.VERSION.SDK_INT}
            App VersionName    : ${BuildConfig.VERSION_NAME}
            App VersionCode    : ${BuildConfig.VERSION_CODE}
            ************* Log Head ****************


            """.trimIndent()
        sb.append(head)
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        try {
            val timestamp = System.currentTimeMillis()
            val time = SimpleDateFormat("MM-dd").format(Date())
            val fileName = "crash-$time-$timestamp.log"
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val fos = FileOutputStream(path + fileName)
            fos.write(sb.toString().toByteArray())
            fos.close()
            return fileName
        } catch (e: Exception) {
            Log.e("CrashHandler", "an error occured while writing file...", e)
        }
        return null
    }

    /**
     * 将错误信息上传至服务器
     */
    private fun uploadExceptionToServer(fileName: String?) {
        //TODO 将错误信息上传至服务器
    }

}