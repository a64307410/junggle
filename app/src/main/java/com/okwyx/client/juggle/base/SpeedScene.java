package com.okwyx.client.juggle.base;

import org.andengine.entity.scene.Scene;

public class SpeedScene extends Scene{
	
    private float mSpeedFactor = 1f;

    public float getSpeedFactor() {
        return mSpeedFactor;
    }

    public void setSpeedFactor(float pFactor) {
        this.mSpeedFactor = pFactor;
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed * mSpeedFactor);
    }
}
