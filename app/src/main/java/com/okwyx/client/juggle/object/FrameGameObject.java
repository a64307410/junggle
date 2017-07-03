package com.okwyx.client.juggle.object;

import com.okwyx.client.juggle.base.AnimatedSpritePool;
import com.okwyx.client.juggle.mananger.ResourceManager;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;


public class FrameGameObject extends GameObject {

	public FrameGameObject(String imgName, float x, float y, int depth) {
		super(imgName, x, y, depth);
	}

	public FrameGameObject(String imgName, int depth) {
		super(imgName, depth);
	}
	
	
	
	@Override
	public void replaceSprite(String newImgName) {
		if(mSprite != null){
			this.imgName = newImgName;
			AnimatedSpritePool pool2 = ResourceManager.getAnimatedSpritePool(imgName);
			final Sprite currentSprite = pool2.obtainPoolItem(x, y);
			
			currentSprite.setZIndex(depth);
			currentSprite.setScaleX(mSprite.getScaleX());
			ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					mScene.detachChild(mSprite);
					if (currentSprite.hasParent()) {
						currentSprite.detachSelf();
					}
					mScene.attachChild(currentSprite);
					mSprite = currentSprite;
					mScene.sortChildren(false);
				}
			});
		}else{
		}
	}

	@Override
	public void onCreateScene(final Scene mScene) {
		this.mScene = mScene;
		AnimatedSpritePool pool = ResourceManager.getAnimatedSpritePool(imgName);
		if (pool == null) {
			throw new RuntimeException("unLoad Resurece " + imgName);
		}
		mSprite = pool.obtainPoolItem(x, y);
		mSprite.setZIndex(depth);
		if (mSprite.hasParent()) {
			ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					mSprite.detachSelf();
					mScene.attachChild(mSprite);
				}
			});
		} else {
			mScene.attachChild(mSprite);
		}
		mScene.sortChildren(false);
		addAnimation();
	}

	@Override
	public AnimatedSprite getSprite() {
		return (AnimatedSprite) super.getSprite();
	}

}
