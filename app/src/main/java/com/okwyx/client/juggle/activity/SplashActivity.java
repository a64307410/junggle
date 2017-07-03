package com.okwyx.client.juggle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.okwyx.client.framework.libs.ad.ADData;
import com.okwyx.client.framework.libs.ad.interfaces.ADListener;
import com.okwyx.client.framework.libs.ad.interfaces.tencent.ADTencent;
import com.okwyx.client.framework.libs.ui.BaseActivity;
import com.okwyx.client.juggle.R;

/**
 * 作者：Swei on 2017/5/31 17:06<BR/>
 * 邮箱：sweilo@qq.com
 */

public class SplashActivity extends BaseActivity {
    private long startTime;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ADData.init(this);

        startTime = System.currentTimeMillis();
        ADTencent.getInstance().loadAD(this, new ADListener.DefaultADListener() {
            @Override
            public void onStatus(int code, Object obj) {
                super.onStatus(code, obj);
                if (!isFinishing()) {
                    switch (code) {
                        case ADListener.LOAD_SUC:
                            handler.removeCallbacks(startMainRunnable);
                            long now = System.currentTimeMillis();
                            long time = now - startTime;
                            if (time <= 2000) {
                                handler.postDelayed(showADRunnable, time);
                            } else {
                                showADRunnable.run();
                            }
                            break;
                    }
                }
            }
        });
        handler.postDelayed(startMainRunnable, 4000);
    }

    private Runnable startMainRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private Runnable showADRunnable = new Runnable() {
        @Override
        public void run() {
            show();
        }
    };

    private void show() {
        ADTencent.getInstance().showSplash(this, new ADListener.DefaultADListener() {
            @Override
            public void onStatus(int code, Object obj) {
                super.onStatus(code, obj);
                switch (code) {
                    case ADListener.AD_SKIP:
                        startMainRunnable.run();
                        break;
                    case ADListener.AD_CLICK:

                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
//   屏蔽back按键     super.onBackPressed();
    }

}
