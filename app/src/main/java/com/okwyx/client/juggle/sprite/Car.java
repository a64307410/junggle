package com.okwyx.client.juggle.sprite;

import com.okwyx.client.juggle.Constans;
import com.okwyx.client.juggle.mananger.CreateManager;
import com.okwyx.client.juggle.mananger.ResourceManager;
import com.okwyx.client.juggle.object.FrameGameObject;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.modifier.IModifier;

public class Car extends FrameGameObject {

	public enum PlaneStatus {
		Empty, Full
	}
	
	private AnimatedSprite carGift;
	private PlaneStatus mPlaneStatus = PlaneStatus.Full;
	private String chirldName= "car2_2_3.png";
	
	public void setChirldName(String chirldName) {
		this.chirldName = chirldName;
	}

	public Car(String imgName, int depth) {
		super(imgName, depth);
	}

	public Car(String imgName, float x, float y, int depth) {
		super(imgName, x, y, depth);
	}

	@Override
	public void onCreateScene(Scene pScene) {
		super.onCreateScene(pScene);
		
		carGift = ResourceManager.getAnimatedSpritePool(chirldName).obtainPoolItem(0, 0);
		carGift.setZIndex(1);
		carGift.setVisible(true);
		mSprite.attachChild(carGift);
	}
	MoveXModifier mxm = null;
	public void moveAnim() {
		if (mPlaneStatus == PlaneStatus.Empty) {
			mPlaneStatus = PlaneStatus.Full;
			carGift.setVisible(true);
		}
		mSprite.setVisible(true);
		if(mxm != null){
			mSprite.unregisterEntityModifier(mxm);
		}
		mxm = new MoveXModifier(12f, Constans.CAMERA_WIDTH + getW(), -getW() ,new IEntityModifier.IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				startFramAnimation((AnimatedSprite)mSprite);
				startFramAnimation(carGift);
			}
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				stopFramAnimation((AnimatedSprite)mSprite);
				stopFramAnimation(carGift);
			}
		} );
		mSprite.registerEntityModifier(mxm);
	}
	
	private void startFramAnimation(AnimatedSprite sprite){
		sprite.animate(new long[] {60,60,60,60,60,60}, new int[] {0,1,2,3,4,5}, true);
	}
	public void stopFramAnimation(AnimatedSprite sprite){
		sprite.stopAnimation();
	}
	
	public void onPress() {
		if (mPlaneStatus == PlaneStatus.Full) {
			mPlaneStatus = PlaneStatus.Empty;
			
			carGift.setVisible(false);
			CreateManager.getInstance().getFrameCreate().setColorBall(true);
		}
	}
}
