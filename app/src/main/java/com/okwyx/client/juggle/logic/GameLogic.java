package com.okwyx.client.juggle.logic;

import com.okwyx.client.juggle.mananger.CreateManager;
import com.okwyx.client.juggle.mananger.ResourceManager;
import com.okwyx.client.juggle.mananger.SoundManager;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;



public class GameLogic extends BaseLogic {

	
	public GameLogic() {
	}

	@Override
	public void onCreateResources() {
		super.onCreateResources();
		//初始化纹理等相关资源
		ResourceManager.getInstance().init();
		//初始化精灵管理器
		CreateManager.getInstance().init();
	}

	@Override
	public void onCreateScene(Scene pScene) {
		CreateManager.getInstance().onCreateScene(pScene);
		super.onCreateScene(pScene);
	}
	
	@Override
	public void onReloadResources() {
		super.onReloadResources();
		CreateManager.getInstance().onReloadResources();
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) {
		return CreateManager.getInstance().onSceneTouchEvent(pScene, pTouchEvent);
	}


	@Override
	public void fixedUpdate() {
		super.fixedUpdate();
		CreateManager.getInstance().fixedUpdate();
	}

	@Override
	public void onRelease() {
		super.onRelease();
		mThread.setRunning(false);
		SoundManager.getInstance().release();
		CreateManager.getInstance().release();
	}

}
