package com.okwyx.client.framework.libs.display;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

public final class ImageDisplayerProvider {
    private static final int[] LOADING_COLOR = {
            0xFF564F4E, 0xFFA14F18, 0xFF805752,
            0xFF000000, 0xFF9AB8AE
    };

    private static final int EMPTY_DRAWABLE = 0xFF000000;

    private static final int ERROR_DRAWABLE = 0xFF000000;

    private static int currentIndex = 0;

    public static Drawable getLoadingDrawable() {
        int color =LOADING_COLOR[currentIndex++ % LOADING_COLOR.length];
        Drawable drawable = new ColorDrawable(color);
        return drawable;
    }

    public static Drawable getEmptyDrawable(){
        Drawable drawable = new ColorDrawable(EMPTY_DRAWABLE);
        return drawable;
    }

    public static Drawable getErrorDrawable(){
        Drawable drawable = new ColorDrawable(ERROR_DRAWABLE);
        return drawable;
    }



}
