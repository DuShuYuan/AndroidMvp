package com.dsy.mvp.utils;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.dsy.mvp.BuildConfig;

/**
 * Log统一管理类
 */
public class MLog {
    public static void initLogger(){
        LogUtils.getConfig().setStackOffset(1)
                .setLogSwitch(BuildConfig.DEBUG)
                .setGlobalTag("dsy");
    }

    /**
     * 记录“verbose”类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void v(String tag, @NonNull String message) {
        LogUtils.vTag(tag, message);
    }

    /**
     * 记录“verbose”类型的日志（自动生成标签）.
     *
     * @param message 信息
     */
    public static void v(@NonNull String message) {
        LogUtils.v(message);
    }

    /**
     * 记录“debug”类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void d(String tag, @NonNull String message) {
        LogUtils.dTag(tag, message);
    }

    /**
     * 记录“debug”类型的日志（自动生成标签）.
     *
     * @param message 信息
     */
    public static void d(@NonNull String message) {
        LogUtils.d(message);
    }

    /**
     * 记录“info”类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void i(String tag, @NonNull String message) {
        LogUtils.iTag(tag, message);
    }

    /**
     * 记录“info”类型的日志（自动生成标签）.
     *
     * @param message 信息
     */
    public static void i(@NonNull String message) {
        LogUtils.i(message);
    }

    /**
     * 记录“warn”类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void w(String tag, @NonNull String message) {
        LogUtils.wTag(tag, message);
    }

    /**
     * 记录“warn”类型的日志（自动生成标签）.
     *
     * @param message 信息
     */
    public static void w(@NonNull String message) {
        LogUtils.w(message);
    }

    /**
     * 记录“error”类型的日志.
     *
     * @param tag     标签
     * @param t       {@link Throwable}
     * @param message 信息
     */
    public static void e(String tag, Throwable t, String message) {
        LogUtils.eTag(tag, t, message);
    }

    /**
     * 记录“error”类型的日志（自动生成标签）.
     *
     * @param t       {@link Throwable}
     * @param message 信息
     */
    public static void e(Throwable t, String message) {
        LogUtils.e(t, message);
    }

    /**
     * 记录“error”类型的日志.
     *
     * @param tag     标签
     * @param message 信息
     */
    public static void e(String tag, @NonNull String message) {
        LogUtils.eTag(tag, message);
    }

    /**
     * 记录“error”类型的日志（自动生成标签）.
     *
     * @param message 信息
     */
    public static void e(@NonNull String message) {
        LogUtils.e(message);
    }

    /**
     * 记录“error”类型的日志.
     *
     * @param tag 标签
     * @param t   {@link Throwable}
     */
    public static void e(String tag, @NonNull Throwable t) {
        LogUtils.eTag(tag, t);
    }

    /**
     * 记录“error”类型的日志（自动生成标签）.
     *
     * @param t {@link Throwable}
     */
    public static void e(@NonNull Throwable t) {
        LogUtils.e(t);
    }


    /**
     * 记录“json”类型的日志.
     *
     * @param tag  标签
     * @param json json
     */
    public static void json(String tag, @NonNull Object json) {
        LogUtils.json(tag, json);
    }

    /**
     * 记录“json”类型的日志（自动生成标签）.
     *
     * @param json 信息
     */
    public static void json(@NonNull Object json) {
        LogUtils.json(json);
    }


}