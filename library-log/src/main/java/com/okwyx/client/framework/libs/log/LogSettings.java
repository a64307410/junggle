package com.okwyx.client.framework.libs.log;


public class LogSettings {
    /** 默认日志TAG */
    public static final String DEFAULT_TAG = "GeekStudio";

    /** 日志对应level */
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    /** 方法递归层数 */
    private int methodCount = 2;
    private boolean showThreadInfo = true;
    private int methodOffset = 0;
    private LogAdapter logAdapter;


    public enum LogLevel {

        /**
         * 展示所有log
         */
        FULL,

        /**
         * 不展示log
         */
        NONE
    }




    /**
     * Determines to how logs will be printed
     */
    private LogLevel logLevel = LogLevel.FULL;

    public LogSettings hideThreadInfo() {
        showThreadInfo = false;
        return this;
    }

    public LogSettings methodCount(int methodCount) {
        if (methodCount < 0) {
            methodCount = 0;
        }
        this.methodCount = methodCount;
        return this;
    }

    public LogSettings logLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public LogSettings methodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public LogSettings logAdapter(LogAdapter logAdapter) {
        this.logAdapter = logAdapter;
        return this;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    public LogAdapter getLogAdapter() {
        if (logAdapter == null) {
            logAdapter = new LogAdapter.AndroidLogAdapter();
        }
        return logAdapter;
    }

    public void reset() {
        methodCount = 2;
        methodOffset = 0;
        showThreadInfo = true;
        logLevel = LogLevel.FULL;
    }

}
