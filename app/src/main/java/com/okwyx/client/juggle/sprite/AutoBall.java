package com.okwyx.client.juggle.sprite;

import com.okwyx.client.juggle.object.PhysicGameObject;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;


public class AutoBall extends PhysicGameObject implements IUpdateHandler {
    // 加速度
    public static int g = 1000;
    // 顶上去的速度
    public static int forceV = -550;
    // 1开始动
    public int status = 0;

    private float positionY;

    private JuggleListener juggleListener;

    public AutoBall(String imgName, int depth) {
        super(imgName, depth);
    }

    public void setJuggleListener(JuggleListener juggleListener) {
        this.juggleListener = juggleListener;
    }

    public AutoBall(String imgName, float x, float y, int depth) {
        super(imgName, x, y, depth);
    }

    @Override
    public float getY() {
        return super.getY() + getH();
    }

    public void start() {
        status = 1;
        if (positionY == 0) {
            positionY = getY();
        } else {
            getSprite().unregisterUpdateHandler(getPhysicsHandler());
            getSprite().setY(positionY);
        }
        getSprite().registerUpdateHandler(getPhysicsHandler());
        getPhysicsHandler().setAccelerationY(g);
        getPhysicsHandler().setVelocityY(forceV);
        setY(positionY);
    }


    @Override
    public void onCreateScene(Scene mScene) {
        super.onCreateScene(mScene);
        getSprite().registerUpdateHandler(this);
        getSprite().setVisible(true);
    }

    private boolean isMove = true;

    @Override
    public void onUpdate(float pSecondsElapsed) {
        switch (status) {
            case 1:
                if (getY() > positionY - 40 && getPhysicsHandler().getVelocityY() > 0 && isMove) {
                    if (juggleListener != null) {
                        juggleListener.onJunggle();
                    }
                    isMove = false;
                }
                if (getY() > positionY) {
                    getPhysicsHandler().setVelocityY(forceV);
                    isMove = true;
                }
                break;
        }
    }


    @Override
    public void reset() {
    }

    public static interface JuggleListener {
        void onJunggle();

    }

}
