package com.okwyx.client.framework.libs.ui;

import android.app.Application;

import com.okwyx.plugin.Plugin;

/**
 * 作者：Swei on 2017/5/29 06:27<BR/>
 * 邮箱：sweilo@qq.com
 */

public class BaseApplication  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Plugin.install(this);
    }
}
