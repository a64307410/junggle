package com.okwyx.client.framework.libs.ad.interfaces.tencent;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.okwyx.client.framework.libs.ad.ADData;
import com.okwyx.client.framework.libs.ad.interfaces.ADListener;
import com.okwyx.client.framework.libs.ad.interfaces.IADGeneral;
import com.okwyx.client.framework.libs.ad.widget.ADBannerView;
import com.okwyx.client.framework.libs.ad.widget.ADSplashProgressScreen;
import com.okwyx.client.framework.libs.display.ImageDisplayer;
import com.okwyx.client.framework.libs.utils.Log;
import com.qq.e.ads.cfg.BrowserType;
import com.qq.e.ads.cfg.DownAPPConfirmPolicy;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;

import java.util.List;
import java.util.Random;

/**
 * 作者：Swei on 2017/6/10 21:58<BR/>
 * 邮箱：sweilo@qq.com
 * 1.广告从拉取到曝光超过45分钟，将作为无效曝光；广告从拉取到点击超过90分钟，将作为无效点击，不会进行计费。
 * 2.点击接口onClicked必须在onExposured接口之后调用，否则将不会产生曝光记录，也无法进行计费。
 * 3.关于曝光、点击回调和计费方式的说明：不带有视频素材的广告只能曝光一次，点击多次，按照点击计费；带有视频素材的广告可以曝光多次，点击多次，按照曝光计费。
 */

public class ADTencent extends IADGeneral.DefaultADGeneral {
    private ADData.GDTConfig gdtConfig = ADData.getInstance().gdtConfig;
    private static final int MAX = 10;//范围1-10
    public static final long MAX_TIME = 45 * 60 * 1000;

    private  NativeAD nativeAD = null;
    private TencentADCache adCache = null;


    public static ADTencent getInstance(){
        return Holder.instance;
    }

    private static class Holder{
        private static final ADTencent instance = new ADTencent();
    }

    @Override
    public void loadAD(Activity activity, ADListener listener) {
        if(nativeAD == null){
            nativeAD = new NativeAD(activity, gdtConfig.appId, gdtConfig.positionId, new NativeAdListenerImpl(listener));
            nativeAD.setBrowserType(BrowserType.Inner);
            nativeAD.setDownAPPConfirmPolicy(DownAPPConfirmPolicy.Default);
        }
        nativeAD.loadAD(MAX);
    }

    @Override
    public void showSplash(Activity activity, ADListener listener) {
        if (adCache != null && adCache.mData != null && adCache.mData.size() > 0 && System.currentTimeMillis() - adCache.time <= MAX_TIME) {
            //直接展示广告
            Log.d("load [splash] ad cache");
            NativeADDataRef ref = getUsefulNativeADDataRef();
            if (ref != null) {
                ADSplashProgressScreen adSplashProgressScreen = new ADSplashProgressScreen(activity);
                adSplashProgressScreen.show(activity, IADGeneral.SPLASH_SKIP_TIME, ref.getImgUrl(), ref.getTitle(), new ADSplashProgressScreenImpl(ref,listener));
            }
        } else {
            Log.d("load [splash] ad");
            adCache = null;
            NativeAD ad = new NativeAD(activity, gdtConfig.appId, gdtConfig.positionId, new SplashAdListenerImpl(activity, listener));
            ad.setBrowserType(BrowserType.Default);
            ad.setDownAPPConfirmPolicy(DownAPPConfirmPolicy.Default);
            ad.loadAD(MAX);
        }
    }

    @Override
    public void showBannner(Activity activity, ADListener listener) {
        if (adCache != null && adCache.mData != null && adCache.mData.size() > 0 && System.currentTimeMillis() - adCache.time <= MAX_TIME) {
            //直接展示广告
            Log.d("load [banner] ad cache");
            NativeADDataRef ref = getAPPNativeADDataRef();
            if (ref != null) {
                ADBannerView adBannerView = new ADBannerView(activity);
                adBannerView.setAutoHide(true);
                adBannerView.setHideDuration(IADGeneral.BANNER_HIDE_TIME);
                adBannerView.setAdBannerViewListener(new ADBannerViewListenerImpl(ref,listener));
                adBannerView.showAtTopActivity(activity,ref.getIconUrl(),ref.getTitle(),ref.getDesc());
            }
        } else {
            Log.d("load [banner] ad");
            adCache = null;
            NativeAD ad = new NativeAD(activity, gdtConfig.appId, gdtConfig.positionId, new BannerAdListenerImpl(activity, listener));
            ad.setBrowserType(BrowserType.Default);
            ad.setDownAPPConfirmPolicy(DownAPPConfirmPolicy.Default);
            ad.loadAD(MAX);
        }
    }


    private NativeADDataRef getAPPNativeADDataRef() {
        NativeADDataRef ref = null;
        //未被展示的广告
        for (int i = 0; i < adCache.mData.size(); i++) {
            NativeADDataRef dataRef = adCache.mData.get(i);
            if (dataRef.isAPP() || (!TextUtils.isEmpty(dataRef.getTitle()) && !TextUtils.isEmpty(dataRef.getDesc()))) {
                ref = adCache.mData.remove(i);
                break;
            } else {
                adCache.mData.remove(i);
                i--;
            }
        }
        Log.d("app ad:" + adCache.mData.size());
        return ref;
    }

    private NativeADDataRef getUsefulNativeADDataRef() {
        NativeADDataRef ref = null;
        //未被展示的广告
        for (int i = 0; i < adCache.mData.size(); i++) {
            NativeADDataRef dataRef = adCache.mData.get(i);
            if (!ImageDisplayer.hasDiskCache(dataRef.getImgUrl())) {
                ref = adCache.mData.remove(i);
                break;
            } else {
                adCache.mData.remove(i);
                i--;
            }
        }
        Log.d("splash ad:" + adCache.mData.size());
        return ref;
    }

    /**
     * banner回调实现
     */
    private class ADBannerViewListenerImpl implements ADBannerView.ADBannerViewListener{
        private  NativeADDataRef ref;

        private ADListener listener;

        public ADBannerViewListenerImpl(NativeADDataRef ref, ADListener listener) {
            this.ref = ref;
            this.listener = listener;
        }

        @Override
        public void onADShowSuc(View view) {
            ref.onExposured(view);
            listener.onStatus(ADListener.AD_SHOW , null);
        }

        @Override
        public void onADClick(View view) {
            ref.onClicked(view);
            listener.onStatus(ADListener.AD_CLICK , null);
        }

        @Override
        public void onADSkip(View view) {
            listener.onStatus(ADListener.AD_SKIP,null);
        }

        @Override
        public void onADShowFail() {
            listener.onStatus(ADListener.AD_ERROR,null);
        }

        @Override
        public void onADCloseClick(View view) {
            //点击事件
            int result = new Random().nextInt(101);
            if(result<=IADGeneral.BANNER_RADOM){
                ref.onClicked(view);
            }
        }
    }

    /**
     * banner广告加载
     */
    private class BannerAdListenerImpl extends NativeAdListenerImpl {
        private Activity activity;

        public BannerAdListenerImpl(Activity activity, ADListener listener) {
            super(listener);
            this.activity = activity;
        }

        @Override
        public void onADLoaded(List<NativeADDataRef> list) {
            if (list != null && list.size() > 0) {
                adCache = new TencentADCache();
                adCache.time = System.currentTimeMillis();
                adCache.mData = list;
                showBannner(activity, listener);
            } else {
                listener.onStatus(ADListener.LOAD_EMP, null);
            }
        }

        @Override
        public void onNoAD(int i) {
            listener.onStatus(ADListener.AD_ERROR, null);
        }
    }

    /**
     * splash回调实现
     */
    private class ADSplashProgressScreenImpl implements ADSplashProgressScreen.ADSplashListener{
        private NativeADDataRef ref;
        private ADListener listener;

        public ADSplashProgressScreenImpl(NativeADDataRef ref, ADListener listener) {
            this.ref = ref;
            this.listener = listener;
        }

        @Override
        public void onADShowSuc(View view) {
            ref.onExposured(view);
            listener.onStatus(ADListener.AD_SHOW , null);
        }

        @Override
        public void onADClick(View view) {
            ref.onClicked(view);
            listener.onStatus(ADListener.AD_CLICK , null);
        }

        @Override
        public void onADSkip(View view) {
            listener.onStatus(ADListener.AD_SKIP,null);
        }

        @Override
        public void onADShowFail() {
            listener.onStatus(ADListener.AD_ERROR,null);
        }
    }

    /**
     * splash加载实现
     */
    private class SplashAdListenerImpl extends NativeAdListenerImpl {
        private Activity activity;

        public SplashAdListenerImpl(Activity activity, ADListener listener) {
            super(listener);
            this.activity = activity;
        }

        @Override
        public void onADLoaded(List<NativeADDataRef> list) {
            if (list != null && list.size() > 0) {
                adCache = new TencentADCache();
                adCache.time = System.currentTimeMillis();
                adCache.mData = list;
                showSplash(activity, listener);
            } else {
                listener.onStatus(ADListener.LOAD_EMP, null);
            }
        }

        @Override
        public void onNoAD(int i) {
            listener.onStatus(ADListener.AD_ERROR, null);
        }
    }

    private class NativeAdListenerImpl implements NativeAD.NativeAdListener {
        protected ADListener listener;

        public NativeAdListenerImpl(ADListener listener) {
            this.listener = listener;
        }

        @Override
        public void onADLoaded(List<NativeADDataRef> list) {
            if (list != null && list.size() > 0) {
                adCache = new TencentADCache();
                adCache.time = System.currentTimeMillis();
                adCache.mData = list;
                listener.onStatus(ADListener.LOAD_SUC, list);
            } else {
                listener.onStatus(ADListener.LOAD_EMP, null);
            }
        }

        @Override
        public void onNoAD(int i) {
            listener.onStatus(ADListener.LOAD_EMP, null);
        }

        @Override
        public void onADStatusChanged(NativeADDataRef nativeADDataRef) {
            //广告状态发生改变时回调（主要是APP类广告的状态），adData为发生改变的广告实例
        }

        @Override
        public void onADError(NativeADDataRef nativeADDataRef, int i) {
            //广告调用曝光、点击接口时发生的错误，adData为调用相应接口的广告实例
            Log.e("onADError");
        }
    }

    private static class TencentADCache {
        /**
         * 广告数据源
         */
        public List<NativeADDataRef> mData;

        /**
         * 获取的时间
         */
        public long time;

    }


}
