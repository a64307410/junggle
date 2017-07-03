package com.okwyx.client.juggle;

import android.content.Context;
import android.os.Handler;

import com.okwyx.client.framework.libs.ui.BaseApplication;

/**
 * 作者：Swei on 2017/5/31 10:32<BR/>
 * 邮箱：sweilo@qq.com
 */

public class JuggleApplication extends BaseApplication {

    public static Handler mHandler = new Handler();

    public static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }



}
