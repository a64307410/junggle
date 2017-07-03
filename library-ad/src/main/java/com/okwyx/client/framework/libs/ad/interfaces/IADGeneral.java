package com.okwyx.client.framework.libs.ad.interfaces;

import android.app.Activity;

import com.okwyx.client.framework.libs.utils.Log;

/**
 * 作者：Swei on 2017/6/10 20:31<BR/>
 * 邮箱：sweilo@qq.com
 */

public interface IADGeneral extends IBaseAD{
    /** splash广告自动跳过持续时间 */
    public static int SPLASH_SKIP_TIME = 4000;

    /** banner自动隐藏时间 */
    public static long BANNER_HIDE_TIME = 8000;

    /** 33%概率点中事件 */
    public static int BANNER_RADOM = 33;




    public void showSplash(Activity activity,ADListener listener);

    public void showBannner(Activity activity,ADListener listener);

    public static class DefaultADGeneral implements IADGeneral{

        @Override
        public void showSplash(Activity activity, ADListener listener) {
            Log.d("showSplash");
        }

        @Override
        public void showBannner(Activity activity, ADListener listener) {
            Log.d("showBannner");
        }

        @Override
        public void loadAD(Activity activity, ADListener listener) {
            Log.d("loadAD");
        }
    }

}
