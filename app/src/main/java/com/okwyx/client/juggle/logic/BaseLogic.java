package com.okwyx.client.juggle.logic;

import com.okwyx.client.juggle.base.GameThread;
import com.okwyx.client.juggle.base.IGameObject;
import com.okwyx.client.juggle.base.ILogic;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;



public class BaseLogic implements ILogic, IGameObject, IOnSceneTouchListener {
	
	protected GameThread mThread;
	
	public BaseLogic() {
		mThread = new GameThread(this);
	}

	public GameThread getmThread() {
		return mThread;
	}

	public void setmThread(GameThread mThread) {
		this.mThread = mThread;
	}

	@Override
	public void fixedUpdate() {

	}

	@Override
	public void onCreateResources() {
		
	}

	@Override
	public void onCreateScene(Scene pScene) {
		mThread.start();
	}

	@Override
	public void onRelease() {
		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) {
		return false;
	}

	@Override
	public void onReloadResources() {
	}

}
