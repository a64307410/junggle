package com.okwyx.client.juggle.activity;

import android.os.Bundle;

import com.okwyx.client.juggle.Constans;
import com.okwyx.client.juggle.base.SpeedScene;
import com.okwyx.client.juggle.logic.GameLogic;
import com.okwyx.client.juggle.mananger.ResourceManager;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public abstract class BaseGameActivity extends SimpleBaseGameActivity{
	//游戏主逻辑类
	protected GameLogic mGameLogic;
	
	/** 创建游戏主控制逻辑对象 */
	public abstract GameLogic createGameLogic();

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, Constans.CAMERA_WIDTH, Constans.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getRenderOptions().setDithering(true);
		
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		
		return engineOptions;
	}
	
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		ResourceManager.setup(this, getEngine(), this.getApplicationContext());
	}
	//GLSurface 线程
	@Override
	protected void onCreateResources() {
		if(mGameLogic == null){
			mGameLogic = createGameLogic();
		}
		mGameLogic.onCreateResources();
	}

	@Override
	protected Scene onCreateScene() {
		SpeedScene mScene = new SpeedScene();
		mScene.setOnSceneTouchListener(mGameLogic);
		mGameLogic.onCreateScene(mScene);
		return mScene;
	}
	
	@Override
	public void onReloadResources() {
		super.onReloadResources();
		ResourceManager.getInstance().init();
		mGameLogic.onReloadResources();
	}
	
	
	public GameLogic getGameLogic() {
		return mGameLogic;
	}

	/////////////////////////////////////////////////////////////DEBUG
	@Override
	public synchronized void onSurfaceCreated(GLState pGLState) {
		super.onSurfaceCreated(pGLState);
	}

	@Override
	public synchronized void onSurfaceChanged(GLState pGLState, int pWidth, int pHeight) {
		super.onSurfaceChanged(pGLState, pWidth, pHeight);
	}

	@Override
	protected synchronized void onCreateGame() {
		super.onCreateGame();
	}

	@Override
	public synchronized void onGameCreated() {
		super.onGameCreated();
	}

	@Override
	protected synchronized void onResume() {
		super.onResume();
	}

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyResources() throws Exception {
		super.onDestroyResources();
	}

}
