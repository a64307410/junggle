package com.okwyx.client.framework.libs.ad.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.okwyx.client.framework.libs.ad.R;
import com.okwyx.client.framework.libs.utils.Log;

/**
 * 作者：Swei on 2017/6/13 03:09<BR/>
 * 邮箱：sweilo@qq.com
 */

public class SkipProgressBar extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final int PER_REFRESH = 30;
    private SurfaceHolder surfaceHolder = null;
    private Canvas canvas = null;
    private SkipListener skipListener = null;
    private Context context = null;

    private Paint paint = new Paint();

    private boolean running = true;

    private float perRefresh;

    /**
     * 圆环x,y坐标
     */
    private float cx;
    private float cy;

    //几秒后进度走完,单位毫秒
    private long time;

    private int refreshCount;

    private Handler mHandler = new Handler();

    public void setTime(long time) {
        this.time = time;

        perRefresh = 360.0f/time * PER_REFRESH;

    }

    public SkipProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SkipProgressBar(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.surfaceHolder = getHolder();
        this.surfaceHolder.setFormat(PixelFormat.RGBA_8888);
        this.surfaceHolder.addCallback(this);

        this.cx = dp2Px(context, 42f) / 2;
        this.cy = dp2Px(context, 42f) / 2;
        setZOrderOnTop(true);
    }

    public void setSkipListener(SkipListener skipListener) {
        this.skipListener = skipListener;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.paint.setARGB(255, 154, 154, 154);
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            stop();
            this.skipListener.onSkip(true);
        }
        return true;
    }

    @Override
    public void run() {
        while (running) {
            long startTime = System.currentTimeMillis();
            float progress =perRefresh * refreshCount++;
            drawTextAndBg(progress);
            long endTime = System.currentTimeMillis();
            /**计算出游戏一次更新的毫秒数**/
            int diffTime  = (int)(endTime - startTime);
            if(progress>=360){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        SkipProgressBar.this.skipListener.onSkip(true);
                    }
                });
                stop();
            }
            while(diffTime <=PER_REFRESH) {
                diffTime = (int)(System.currentTimeMillis() - startTime);
                /**线程等待**/
                Thread.yield();
            }
        }
    }

    private void drawTextAndBg(float progress) {
        this.paint.setAntiAlias(true);
        this.canvas = this.surfaceHolder.lockCanvas(null);
        if (this.canvas != null) {
            this.canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            this.paint.setARGB(102, 0, 0, 0);
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setARGB(77, 0, 0, 0);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeWidth(dp2Px(context, 2.0f));
            this.canvas.drawCircle(this.cx, this.cy, dp2Px(context, 34.0f) / 2, this.paint);
            this.paint.setARGB(204, 255, 255, 255);//白色填充
            this.paint.setStyle(Paint.Style.FILL);

            this.paint.setStrokeWidth(0.0F);
            this.canvas.drawCircle(this.cx, this.cy, dp2Px(context, 32.0f) / 2, this.paint);

            //进度
            float x = dp2Px(context, 3f);//42 -34-2
            float y = dp2Px(context, 3f);
            this.paint.setStyle(Paint.Style.STROKE);

            this.paint.setColor(Color.rgb(255, 153, 0));
            this.paint.setStrokeWidth(dp2Px(context, 2.0f));
            RectF oval = new RectF(x, y, getWidth() - x, getHeight() - y);
            Log.d(progress+"");
            canvas.drawArc(oval, -90, progress, false, paint);

            //文字
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setStrokeWidth(0.0F);
            this.paint.setTextSize(dp2Px(this.context, 12.0F));
            this.paint.setARGB(255, 154, 154, 154);
            this.canvas.drawText(context.getString(R.string.ad_skip), this.cx - dp2Px(this.context, 11.0F), this.cy + dp2Px(this.context, 4.5F), this.paint);
            this.surfaceHolder.unlockCanvasAndPost(this.canvas);

        }
    }

    private int dp2Px(Context context, float dp) {
        float f1 = context.getResources().getDisplayMetrics().density;
        return (int) (dp * f1 + 0.5F);
    }

    public interface SkipListener {
        void onSkip(boolean paramBoolean);
    }

    public void stop(){
        running = false;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.GONE);
            }
        });
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
        skipListener = null;
    }
}
