package com.okwyx.client.framework.libs.display;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;


/**
 * 图片加载器,统一调用此类函数
 * 配置:https://github.com/nostra13/Android-Universal-Image-Loader/wiki/Configuration
 */
public final class ImageDisplayer {
    /** 默认线程池个数 */
    private static final int DEFAULT_POOLSIZE = 3;
    /** 内存占比,缓存先进先出策略 */
    private static final int DEFAULT_MEMCACHE_PERCENT = 24;

    private static final int FADEIN_DURATIONMILLIS = 300;


    public static final void init(Context app) {
        File cacheDir = StorageUtils.getCacheDirectory(app);//路径在/mnt/sdcard/Android/data/包名/cache
        init(app,cacheDir,DEFAULT_POOLSIZE,DEFAULT_MEMCACHE_PERCENT);
    }

    public static final void init(Context app ,File cacheDir) {
        init(app,cacheDir,DEFAULT_POOLSIZE,DEFAULT_MEMCACHE_PERCENT);
    }

    public static final void init(Context app , File cacheDir, int poolsize , int memcachePercent) {
        //本地路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(app)
                .threadPoolSize(poolsize) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .memoryCacheSizePercentage(memcachePercent) // default 1/8 30/100运行时内存
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(app)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(getDefaultDisplayImageOptions()) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static DisplayImageOptions getDefaultDisplayImageOptions(){
        return getDefaultDisplayImageOptions(ImageDisplayerProvider.getLoadingDrawable(),ImageDisplayerProvider.getEmptyDrawable(),ImageDisplayerProvider.getErrorDrawable());
    }

    public static DisplayImageOptions getDefaultDisplayImageOptions(Drawable loadingDrawable){
        return getDefaultDisplayImageOptions(loadingDrawable,ImageDisplayerProvider.getEmptyDrawable(),ImageDisplayerProvider.getErrorDrawable());
    }

    public static DisplayImageOptions getDefaultDisplayImageOptions(Drawable loadingDrawable , Drawable emptyDrawable , Drawable errorDrawable){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .delayBeforeLoading(0)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)//支持图片文件中包含exif扩展描述,例如camara旋转
                .extraForDownloader(null)//解析InputStream使用的额外参数
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)//比rgb888更省内存,原因没用透明色
                .displayer(new FadeInBitmapDisplayer(FADEIN_DURATIONMILLIS,true,false,false))//只在网络加载时装载图片
                .showImageOnLoading(loadingDrawable)
                .showImageForEmptyUri(emptyDrawable)
                .showImageOnFail(errorDrawable)
                .build();
        return options;
    }

    public static DisplayImageOptions getNoAnimationDisplayImageOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)
                .delayBeforeLoading(0)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)//支持图片文件中包含exif扩展描述,例如camara旋转
                .extraForDownloader(null)//解析InputStream使用的额外参数
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.RGB_565)//比rgb888更省内存,原因没用透明色
                .displayer(new SimpleBitmapDisplayer())//只在网络加载时装载图片
                .build();
        return options;
    }


    public static void load(Context context,String uri , final ImageLoadingListener listener){
        final ImageView imageView = new ImageView(context);
        load(imageView, uri, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                listener.onLoadingStarted(imageUri,view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                listener.onLoadingFailed(imageUri,view,failReason);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                listener.onLoadingComplete(imageUri,view,loadedImage);
                imageView.setImageDrawable(null);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                listener.onLoadingCancelled(imageUri,view);
            }
        });
    }


    /**
     * 异步加载图片,loading规则来自ImageDisplayerProvider
     * @param imageView
     * @param uri
     */
    public static void load(ImageView imageView , String uri){
        load(imageView,uri,null);
    }



    /**
     * 异步加载图片
     * @param imageView
     * @param uri
     * @param listener 监听器
     */
    public static void load(ImageView imageView , String uri ,ImageLoadingListener listener){
        load(imageView,uri,ImageDisplayerProvider.getLoadingDrawable(),listener);
    }

    public static void load(ImageView imageView , String uri , Drawable loadingDrawable ,ImageLoadingListener listener){
        DisplayImageOptions options = null;
        if(loadingDrawable != null){
            options = getDefaultDisplayImageOptions(loadingDrawable);
        }else{
            options = getNoAnimationDisplayImageOptions();
        }

        ImageLoader.getInstance().displayImage(uri,imageView,options,listener);
    }

    public static boolean hasDiskCache(String url){
        return ImageLoader.getInstance().getDiskCache().get(url).exists();
    }

    /**
     * 停止当前所有正在加载的任务
     */
    public static void stop(){
        try{
            ImageLoader.getInstance ().stop ();
        }catch(Throwable e){
            e.printStackTrace();
        }
    }


    /**
     * 清除内存缓存
     */
    public static void clearImageMemCache(){
        ImageLoader imgLoader = ImageLoader.getInstance ();
        imgLoader.clearMemoryCache ();
    }
    /**
     * 清除本地缓存
     */
    public static void clearImageDiskCache(){
        ImageLoader imgLoader = ImageLoader.getInstance ();
        imgLoader.clearDiskCache();
    }

    public static ImageLoader getImageLoader() {
        return ImageLoader.getInstance();
    }

    public static PauseOnScrollListener getPauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling , AbsListView.OnScrollListener listener){
        return new PauseOnScrollListener(getImageLoader(), pauseOnScroll, pauseOnFling,listener);
    }

}