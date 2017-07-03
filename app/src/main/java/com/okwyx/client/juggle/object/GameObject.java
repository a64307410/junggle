package com.okwyx.client.juggle.object;

import android.graphics.PointF;

import com.okwyx.client.juggle.base.IGameObject;
import com.okwyx.client.juggle.base.ILogic;
import com.okwyx.client.juggle.base.SpritePool;
import com.okwyx.client.juggle.mananger.ResourceManager;

import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;


public class GameObject implements IGameObject, ILogic {
	protected String imgName;
	protected Sprite mSprite;
	protected int depth;
	protected float x;
	protected float y;
	protected boolean isReleased = false;
	protected Scene mScene;
	private List<Sprite> releaseLaterList = new ArrayList<Sprite>();
	
	public GameObject(String imgName, int depth) {
		this(imgName, -1000 , -1000, depth);
	}
	
	public GameObject(String imgName, float x, float y, int depth) {
		this.imgName = imgName;
		this.x = x;
		this.y = y;
		this.depth = depth;
	}
	
	public float getX() {
		return mSprite.getX();
	}
	
	public float getY() {
		return mSprite.getY();
	}
	
	public PointF getPosition() {
		return new PointF(getX(), getY());
	}
	
	public void setX(float x) {
		this.x = x;
		mSprite.setX(x);
	}
	
	public void setY(float y) {
		this.y = y;
		mSprite.setY(y);
	}
	
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setPosition(PointF point) {
		setPosition(point.x, point.y);
	}
	
	public float getW() {
		return mSprite.getWidth();
	}
	
	public float getH() {
		return mSprite.getHeight();
	}
	
	public float getRotation() {
		if(mSprite !=null){
			return mSprite.getRotation();
		}
		return -1;
	}

	@Override
	public void onCreateResources() {
	}

	@Override
	public void onCreateScene(final Scene pScene) {
		this.mScene = pScene;
		createSprite();
		addAnimation();
	}
	
	public void replaceSprite(String newImgName){
		if(mSprite != null){
			this.imgName = newImgName;
			createSprite();
		}else{
		}
	}
	
	
	private void createSprite() {
		SpritePool pool = ResourceManager.getSpritePool(imgName);
		if(pool == null){
			return;
		}
		mSprite = pool.obtainPoolItem(x, y);
		mSprite.setZIndex(depth);
		if (mSprite.hasParent()) {
			ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mSprite.detachSelf();
					mScene.attachChild(mSprite);
				}
			});
		} else {
			mScene.attachChild(mSprite);
		}
		mScene.sortChildren(false);
	}
	
	public void addAnimation() {
		
	}
	
	public void registerEntityModifier(final IEntityModifier pEntityModifier) {
		mSprite.registerEntityModifier(pEntityModifier);
	}
	
	public void setImg(String pImgName) {
		releaseLaterList.add(mSprite);
		
		this.imgName = pImgName;
		createSprite();
		
		releaseLater();
	}
	
	private void releaseLater() {
		ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (releaseLaterList.size() > 0) {
					releaseLaterList.get(0).detachSelf();
					releaseLaterList.remove(0);
				}
			}
		});
	}
	
	public Sprite getSprite() {
		return mSprite;
	}
	public void setSprite(Sprite mSprite) {
		this.mSprite = mSprite;
	}

	@Override
	public void onRelease() {
		isReleased = true;
		ResourceManager.getSpritePool(imgName).recyclePoolItem(mSprite);
	}
	
	@Override
	public void fixedUpdate() {
		
	}

	@Override
	public void onReloadResources() {
		
	}

	public String getImgName() {
		return imgName;
	}
	
}
