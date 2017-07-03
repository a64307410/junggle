package com.okwyx.client.framework.libs.ad.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.okwyx.client.framework.libs.ad.R;
import com.okwyx.client.framework.libs.display.ImageDisplayer;

/**
 * 作者：Swei on 2017/6/13 16:55<BR/>
 * 邮箱：sweilo@qq.com
 */

public class ADSplashProgressScreen extends LinearLayout {
    public long durationMillis = 300;

    private TextView adTagTv;
    private ImageView adImgView;
    private SkipProgressBar skipProgressBar;
    private ViewGroup viewGroup;
    private TextView descTv;


    public ADSplashProgressScreen(Context context) {
        super(context);
        init(context);
    }

    public ADSplashProgressScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.ad_splash_skip, this);
        adTagTv = (TextView) findViewById(R.id.ad_name_tv);
        adTagTv.setShadowLayer(2.0F, 2.0F, 2.0F, context.getResources().getColor(R.color.color_adtext_shadow));
        adImgView = (ImageView) findViewById(R.id.ad_splash_imgview);
        skipProgressBar = (SkipProgressBar) findViewById(R.id.ad_splash_skipbar);
        viewGroup = (ViewGroup) findViewById(R.id.ad_item_layout);
        descTv = (TextView) findViewById(R.id.ad_desc_tv);
    }

    public void show(final Activity activity, final long skipTime, String url, final String desc, final ADSplashListener callback) {
        ImageDisplayer.load(adImgView, url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (activity.isFinishing()) {
                    callback.onADShowFail();
                } else {
                    callback.onADShowSuc(ADSplashProgressScreen.this);
                    //点击触发跳转
                    viewGroup.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callback.onADClick(ADSplashProgressScreen.this);
                        }
                    });
                    //点击屏幕别的按钮
                    ADSplashProgressScreen.this.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callback.onADClick(ADSplashProgressScreen.this);
                        }
                    });

                    //跳过
                    skipProgressBar.setSkipListener(new SkipProgressBar.SkipListener() {
                        @Override
                        public void onSkip(boolean paramBoolean) {
                            callback.onADSkip(ADSplashProgressScreen.this);
                        }
                    });
                    skipProgressBar.setTime(skipTime - durationMillis);
                    if (!TextUtils.isEmpty(desc)) {
                        viewGroup.setVisibility(View.VISIBLE);
                        descTv.setText(desc);
                    } else {
                        viewGroup.setVisibility(View.GONE);
                        descTv.setText(null);
                    }
                    activity.addContentView(ADSplashProgressScreen.this, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    fadeIn();
                }
            }
            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    private void fadeIn() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(durationMillis);
        this.startAnimation(alphaAnimation);
    }

    public interface ADSplashListener {
        /**
         * 广告被展示
         * @param view
         */
        void onADShowSuc(View view);

        /**
         * 广告被点击
         * @param view
         */
        void onADClick(View view);

        /**
         * 广告被跳过
         * @param view
         */
        void onADSkip(View view);

        /**
         * 广告展示失败,activity finish
         */
        void onADShowFail();
    }


}
