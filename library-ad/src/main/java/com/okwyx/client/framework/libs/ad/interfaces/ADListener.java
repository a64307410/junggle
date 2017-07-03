package com.okwyx.client.framework.libs.ad.interfaces;

import com.okwyx.client.framework.libs.utils.Log;

/**
 * 作者：Swei on 2017/6/11 18:36<BR/>
 * 邮箱：sweilo@qq.com
 */

public interface ADListener<T> {
    /** 加载 */
    public static final int LOAD_SUC = 1;
    public static final int LOAD_FAL = 2;
    public static final int LOAD_EMP = 3;

    /** 点击事件 */
    public static final int AD_SHOW = 4;
    public static final int AD_CLICK = 5;
    public static final int AD_ERROR =6;
    public static final int AD_SKIP = 7;



    public void onStatus(int code, T obj);

    public static class DefaultADListener<T> implements ADListener<T> {
        @Override
        public void onStatus(int code, T obj) {
            Log.d("code:" + code + ",obj:" + (obj != null ? obj.toString():"null"));
        }
    }

}
