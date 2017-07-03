package com.okwyx.client.juggle.sprite;

import com.okwyx.client.juggle.Configation;
import com.okwyx.client.juggle.JuggleApplication;
import com.okwyx.client.juggle.object.FrameGameObject;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;

import java.util.concurrent.atomic.AtomicBoolean;


public class People extends FrameGameObject implements AutoBall.JuggleListener {
    /**
     * 帧动画间隔（毫秒）
     */
    public static int FrameTime1 = 50;
    public static int FrameTime2 = 70;
    public static int FrameTime3 = 100;
    public static int FrameTime4 = 100;
    public static int FrameTime5 = 100;

    public static int FrameTime2_default = 80;
    public static int FrameTime3_default = 110;
    public static int FrameTime4_default = 110;
    public static int FrameTime5_default = 110;

    public static int FrameDeadTime1 = 100;
    public static int FrameDeadTime2 = 100;
    public static int FrameDeadTime3 = 100;
    public static int FrameDeadTime4 = 100;

    public static int FrameBeatTime = 200;

    //1100 - 320 = 780
    public static int FrameTime1_1 = 50;
    public static int FrameTime2_1 = 70;
    public static int FrameTime3_1 = 100;
    public static int FrameTime4_1 = 100;
    public static int FrameTime5_1 = 100;


    private AtomicBoolean isjunggle = new AtomicBoolean();
    private long startJuggleTime = 0;

    private Head mHead;


    public People(String imgName, float x, float y, int depth) {
        super(imgName, x, y, depth);
    }

    public People(String imgName, int depth) {
        super(imgName, depth);
    }

    public void setHead(Head pHead) {
        this.mHead = pHead;
    }


    public Head getHead() {
        return mHead;
    }

    @Override
    public void onCreateScene(Scene mScene) {
        // TODO Auto-generated method stub
        super.onCreateScene(mScene);
    }

    public void start() {
        getSprite().animate(new long[]{FrameTime1_1, FrameTime2_1, FrameTime3_1, FrameTime4_1, FrameTime5_1},
                new int[]{0, 1, 2, 1, 3}, false);
        mHead.getSprite().animate(new long[]{FrameTime1, FrameTime2_1, FrameTime3_1, FrameTime4_1, FrameTime5_1},
                new int[]{0, 1, 2, 1, 3}, false);
    }

    public void setJuggle(boolean isJuggle) {
        isjunggle.set(isJuggle);
    }

    public void onTouch() {
        if (Configation.isGameOver) {
            return;
        }
        getSprite().animate(new long[]{FrameTime1, FrameTime2, FrameTime3, FrameTime4, FrameTime5},
                new int[]{0, 1, 2, 1, 3}, false);
        mHead.getSprite().animate(new long[]{FrameTime1, FrameTime2, FrameTime3, FrameTime4, FrameTime5},
                new int[]{0, 1, 2, 1, 3}, false);

        JuggleApplication.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isjunggle.set(true);
                startJuggleTime = System.currentTimeMillis();
            }
        }, FrameTime1);

        JuggleApplication.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isjunggle.set(false);
            }
        }, FrameTime1 + FrameTime2 + FrameTime3);
    }

    public void over() {
        mHead.getSprite().animate(new long[]{FrameDeadTime1, FrameDeadTime2, FrameDeadTime3, FrameDeadTime4}, new int[]{4, 5, 6, 7}, false);
        getSprite().animate(new long[]{FrameDeadTime1, FrameDeadTime2, FrameDeadTime3, FrameDeadTime4}, new int[]{4, 5, 6, 7}, false, new AnimatedSprite.IAnimationListener() {
            @Override
            public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
            }

            @Override
            public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
            }

            @Override
            public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
            }

            @Override
            public void onAnimationFinished(AnimatedSprite arg0) {
                getSprite().animate(new long[]{FrameBeatTime, FrameBeatTime}, new int[]{6, 7});
                mHead.getSprite().animate(new long[]{FrameBeatTime, FrameBeatTime}, new int[]{6, 7});
            }
        });

    }

    public void reset() {
        getSprite().stopAnimation();
        getSprite().animate(new long[]{FrameTime1}, new int[]{0}, false);

        mHead.getSprite().stopAnimation();
        mHead.getSprite().animate(new long[]{FrameTime1}, new int[]{0}, false);
    }

    public boolean isjunggle() {
        return isjunggle.get();
    }

    public long startJuggleTime() {
        return startJuggleTime;
    }

    @Override
    public void onJunggle() {
        start();
    }
}
