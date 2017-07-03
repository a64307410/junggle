package com.okwyx.client.tencent;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import com.okwyx.client.framework.libs.utils.Log;

/**
 * 作者：Swei on 2017/6/10 18:35<BR/>
 * 邮箱：sweilo@qq.com
 */

public class ADVService extends com.qq.e.comm.DownloadService{


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i("tencent service : onBind");
        return super.onBind(arg0);
    }

    @Override
    public void onCreate() {
        Log.d("tencent service : onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d("tencent service : onDestroy");
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration arg0) {
        Log.d("tencent service : onConfigurationChanged");
        super.onConfigurationChanged(arg0);
    }

    @Override
    public void onLowMemory() {
        Log.d("tencent service : onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onRebind(Intent arg0) {
        Log.d("tencent service : onRebind");
        super.onRebind(arg0);
    }

    @Override
    public int onStartCommand(Intent arg0, int arg1, int arg2) {
        Log.d("tencent service : onStartCommand");
        return super.onStartCommand(arg0, arg1, arg2);
    }

    @Override
    public void onTaskRemoved(Intent arg0) {
        Log.d("tencent service : onTaskRemoved");
        super.onTaskRemoved(arg0);
    }

    @Override
    public void onTrimMemory(int arg0) {
        Log.d("tencent service : onTrimMemory");
        super.onTrimMemory(arg0);
    }

    @Override
    public boolean onUnbind(Intent arg0) {
        Log.d("tencent service : onUnbind");
        return super.onUnbind(arg0);
    }


}
