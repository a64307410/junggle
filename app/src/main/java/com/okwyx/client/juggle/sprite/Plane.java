package com.okwyx.client.juggle.sprite;

import com.okwyx.client.juggle.Constans;
import com.okwyx.client.juggle.mananger.CreateManager;
import com.okwyx.client.juggle.mananger.ResourceManager;
import com.okwyx.client.juggle.object.GameObject;

import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;


public class Plane extends GameObject {

	public enum PlaneStatus {
		Empty, Full
	}
	
	private Sprite planeGift;
	private PlaneStatus mPlaneStatus = PlaneStatus.Full;
	private String chirldName= "plane2.png";
	
	public void setChirldName(String chirldName) {
		this.chirldName = chirldName;
	}

	public Plane(String imgName, int depth) {
		super(imgName, depth);
	}

	public Plane(String imgName, float x, float y, int depth) {
		super(imgName, x, y, depth);
	}

	@Override
	public void onCreateScene(Scene pScene) {
		super.onCreateScene(pScene);
		
		planeGift = ResourceManager.getSpritePool(chirldName).obtainPoolItem(0, 0);
		planeGift.setZIndex(1);
		planeGift.setVisible(true);
		mSprite.attachChild(planeGift);
	}
	
	public void flyAnim() {
		if (mPlaneStatus == PlaneStatus.Empty) {
			mPlaneStatus = PlaneStatus.Full;
			
			planeGift.setVisible(true);
		}
		mSprite.setVisible(true);
		MoveXModifier mxm = new MoveXModifier(10f, Constans.CAMERA_WIDTH + getW(), -getW());
		mSprite.registerEntityModifier(mxm);
	}
	
	public void onPress() {
		if (mPlaneStatus == PlaneStatus.Full) {
			mPlaneStatus = PlaneStatus.Empty;
			
			planeGift.setVisible(false);
			CreateManager.getInstance().getFrameCreate().setColorBall(true);
		}
	}
}
