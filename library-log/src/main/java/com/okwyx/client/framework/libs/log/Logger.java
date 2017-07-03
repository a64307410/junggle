package com.okwyx.client.framework.libs.log;

/**
 * 作者：apple on 2017/4/10 20:21<BR/>
 * 邮箱：sweilo@qq.com
 * <BR/>
 * eg:<BR/>
 * Log.d("hello %s %d", "world", 5);<BR/>
 * Log.d(list/map/set/new String[]);
 *
 */

public class Logger {

    private static final String DEFAULT_TAG = LogSettings.DEFAULT_TAG;



    private static LogPrinter printer = new LogPrinter.AndroidLoggerPrinter();

    private Logger(){}


    public static LogSettings init() {
        return init(DEFAULT_TAG);
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger as TAG
     */
    public static LogSettings init(String tag) {
        printer = new LogPrinter.AndroidLoggerPrinter();
        return printer.init(tag);
    }

    public static void resetSettings() {
        printer.resetSettings();
    }

    public static LogPrinter t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static LogPrinter t(int methodCount) {
        return printer.t(null, methodCount);
    }

    public static LogPrinter t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        printer.log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        printer.d(message, args);
    }

    public static void d(Object object) {
        printer.d(object);
    }

    public static void e(String message, Object... args) {
        printer.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        printer.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        printer.i(message, args);
    }

    public static void v(String message, Object... args) {
        printer.v(message, args);
    }

    public static void w(String message, Object... args) {
        printer.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        printer.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        printer.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        printer.xml(xml);
    }


}
