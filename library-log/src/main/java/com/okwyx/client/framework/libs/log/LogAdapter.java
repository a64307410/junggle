package com.okwyx.client.framework.libs.log;


import android.util.Log;

public interface LogAdapter {

    void d(String tag, String message);

    void e(String tag, String message);

    void w(String tag, String message);

    void i(String tag, String message);

    void v(String tag, String message);

    void wtf(String tag, String message);

    public static final class AndroidLogAdapter implements  LogAdapter{

        @Override public void d(String tag, String message) {
            Log.d(tag, message);
        }

        @Override public void e(String tag, String message) {
            Log.e(tag, message);
        }

        @Override public void w(String tag, String message) {
            Log.w(tag, message);
        }

        @Override public void i(String tag, String message) {
            Log.i(tag, message);
        }

        @Override public void v(String tag, String message) {
            Log.v(tag, message);
        }

        @Override public void wtf(String tag, String message) {
            Log.wtf(tag, message);
        }
    }


}
