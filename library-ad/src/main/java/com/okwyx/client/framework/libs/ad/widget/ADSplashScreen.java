package com.okwyx.client.framework.libs.ad.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.okwyx.client.framework.libs.display.ImageDisplayer;
import com.qq.e.ads.nativ.NativeADDataRef;

public class ADSplashScreen extends ImageView {
    // default delaytime 3s
    public long delayMillis = 2500;
    public long durationMillis = 300;
    private Context mContext;

    public ADSplashScreen(Context context) {
        super(context);
        mContext = context;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
        this.setScaleType(ScaleType.FIT_XY);
    }

    public void show() {
        if (!this.isShown() && !((Activity) mContext).isFinishing()) {
            ((Activity) mContext).addContentView(this, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }

    public void dismissDelayed() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                fadeOut();
            }
        }, delayMillis);
    }

    /**
     * auto dismiss
     */
    public void showOneTime(final NativeADDataRef ref) {
        ImageDisplayer.load(this, ref.getImgUrl(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ref.onExposured(ADSplashScreen.this);
                ADSplashScreen.this.show();
                ADSplashScreen.this.dismissDelayed();
                ADSplashScreen.this.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.onClicked(ADSplashScreen.this);
                    }
                });
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

    private void fadeOut() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(durationMillis);
        alphaAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    if (ADSplashScreen.this.isShown()) {
                        ADSplashScreen.this.setVisibility(View.GONE);
                        ((ViewGroup) ADSplashScreen.this.getParent()).removeView(ADSplashScreen.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ADSplashScreen.this.startAnimation(alphaAnimation);
    }


}
