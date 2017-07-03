package com.okwyx.client.framework.libs.ad.widget;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.okwyx.client.framework.libs.ad.R;
import com.okwyx.client.framework.libs.display.ImageDisplayer;
import com.okwyx.client.framework.libs.utils.Log;

public class ADBannerView extends LinearLayout {
	private ViewGroup rootView;
	private ImageView adIconimg;
	private TextView adText1Tv;
	private TextView adText2Tv;

	private View closeView;

    /** 是否自动隐藏 */
    private boolean autoHide = true;
    /** 自动隐藏时间 */
    private long hideDuration = 5000;

    /** 动画执行时间 */
	private int showAniTime = 800;
	private int hideAniTime = 240;


	private ADBannerViewListener adBannerViewListener = new DefaultADBannerViewListener();
	
	public ADBannerView(Context context) {
		super(context);
		init(context);
	}

	public ADBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public ADBannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

    public void setAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
    }

    public void setHideDuration(long hideDuration) {
        this.hideDuration = hideDuration;
    }

    public void setAdBannerViewListener(ADBannerViewListener adBannerViewListener) {
        this.adBannerViewListener = adBannerViewListener;
    }

    public void showAtTopActivity(Activity activity,String url,String txt1,String txt2){
        try {
            setInfo(url, txt1, txt2);
            setOnItemClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideWithAnimation();
                    adBannerViewListener.onADClick(ADBannerView.this);
                }
            });
            this.setOnCloseListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideWithAnimation();
                    adBannerViewListener.onADCloseClick(ADBannerView.this);
                }
            });
            activity.addContentView(ADBannerView.this, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            startWithAnimation();
            adBannerViewListener.onADShowSuc(ADBannerView.this);
        }catch (Exception e){
            adBannerViewListener.onADShowFail();
        }
    }

	private void init(Context context) {
		rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.ad_top_banner, this);
		closeView = rootView.findViewById(R.id.adclose_img);
		adIconimg = (ImageView) rootView.findViewById(R.id.ad_img_icon);
		adText1Tv = (TextView) rootView.findViewById(R.id.ad_tv_title);
		adText2Tv = (TextView) rootView.findViewById(R.id.ad_tv_desc);
		TextView adTagTv = (TextView) rootView.findViewById(R.id.ad_name_tv);
        adTagTv.setShadowLayer(2.0F, 2.0F, 2.0F, context.getResources().getColor(R.color.color_adtext_shadow));
    }

	private void setOnItemClickListener(OnClickListener listener) {
		rootView.setOnClickListener(listener);
	}

	private void setOnCloseListener(OnClickListener listener) {
		closeView.setOnClickListener(listener);
	}

	private void setInfo(String imgUrl, String txt1, String txt2) {
		ImageDisplayer.load(adIconimg, imgUrl);
		adText1Tv.setText(txt1);
		adText2Tv.setText(txt2);
	}


	private void startWithAnimation() {
		TranslateAnimation translateAnimation = createTranslateAnimation(0f, 0f, -1f, 0f);
		translateAnimation.setDuration(showAniTime);
		translateAnimation.setInterpolator(new OvershootInterpolator());
		translateAnimation.setAnimationListener(new DefaultAnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
                if(autoHide){
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            hideWithAnimation();
                        }
                    }, hideDuration);
                }
			}
		});
		this.startAnimation(translateAnimation);
	}

    private void hideWithAnimation() {
        TranslateAnimation translateAnimation = createTranslateAnimation(0f, 0f, 0f, -1f);
        translateAnimation.setDuration(hideAniTime);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setAnimationListener(new DefaultAnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    if (ADBannerView.this.isShown()) {
                        ADBannerView.this.setVisibility(View.GONE);
                        ((ViewGroup) ADBannerView.this.getParent()).removeView(ADBannerView.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adBannerViewListener.onADSkip(ADBannerView.this);
            }
        });
        ADBannerView.this.startAnimation(translateAnimation);
    }

    private TranslateAnimation createTranslateAnimation(float x, float toX, float y , float toY){
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, x, Animation.RELATIVE_TO_SELF,toX,
				Animation.RELATIVE_TO_SELF, y, Animation.RELATIVE_TO_SELF,toY);
		translateAnimation.setFillAfter(true);
		return translateAnimation;
	}
	
	private class DefaultAnimationListener implements AnimationListener{

		@Override
		public void onAnimationStart(Animation animation) {
			
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}
	}
	
	public static class DefaultADBannerViewListener implements ADBannerViewListener{
        @Override
        public void onADShowSuc(View view) {
            Log.d("ADBanner: onADShowSuc");
        }

        @Override
        public void onADClick(View view) {
            Log.d("ADBanner: onADClick");
        }

        @Override
        public void onADSkip(View view) {
            Log.d("ADBanner: onADSkip");
        }

        @Override
        public void onADShowFail() {
            Log.d("ADBanner: onADShowFail");
        }

        @Override
        public void onADCloseClick(View view) {
            Log.d("ADBanner: onADCloseClick");
        }
    }

	public interface ADBannerViewListener {
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

        /**
         * 关闭按钮被点击
         * @param view
         */
        void onADCloseClick(View view);
	}

}
