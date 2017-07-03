package com.okwyx.client.tencent;

import android.os.Bundle;

import com.okwyx.client.framework.libs.utils.Log;

/**
 * 作者：Swei on 2017/6/10 18:35<BR/>
 * 邮箱：sweilo@qq.com
 */

public class ADVActivity extends com.qq.e.ads.ADActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tencent activity : onCreate ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("tencent activity : onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tencent activity : onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tencent activity : onDestroy ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tencent activity : onResume ");
    }


}
