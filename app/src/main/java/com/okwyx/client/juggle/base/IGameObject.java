package com.okwyx.client.juggle.base;

import org.andengine.entity.scene.Scene;

public interface IGameObject {
	public void onCreateResources();

	public void onCreateScene(Scene pScene);

	public void onRelease();
	
	public void onReloadResources();
	
}
