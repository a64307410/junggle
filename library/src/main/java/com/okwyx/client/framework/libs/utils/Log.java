package com.okwyx.client.framework.libs.utils;


import com.okwyx.client.framework.libs.log.LogSettings;
import com.okwyx.client.framework.libs.log.Logger;

/**
 * 作者：Swei on 2017/4/10 20:51<BR/>
 * 邮箱：sweilo@qq.com
 */

public class Log {
    private static final String TAG = "okwyx";
    private static final boolean DEBUG = true;

    static{
        Logger.init(TAG).methodCount(5).logLevel(DEBUG? LogSettings.LogLevel.FULL: LogSettings.LogLevel.NONE);
    }

    public static void setTag(String tag){
        Logger.init(tag);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void d(Object object) {
        Logger.d(object);
    }

    public static void e(String message, Object... args) {
        Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        Logger.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }


}
