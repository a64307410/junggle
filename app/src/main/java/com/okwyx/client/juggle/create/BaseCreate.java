package com.okwyx.client.juggle.create;

import android.view.MotionEvent;

import com.okwyx.client.juggle.base.IGameObject;
import com.okwyx.client.juggle.base.ILifecycle;
import com.okwyx.client.juggle.base.ILogic;
import com.okwyx.client.juggle.base.IObjectManager;
import com.okwyx.client.juggle.object.GameObject;
import com.okwyx.client.juggle.sprite.People;
import com.okwyx.client.juggle.utils.GameUtil;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class BaseCreate implements IObjectManager, IGameObject, ILifecycle,IOnSceneTouchListener, ILogic {
	protected List<GameObject> mList;
	private long touchDelayLeft = 0;
	private long touchDelayRight = 0;
	protected Scene mScene;
	private static final long DeltaTouchTime = People.FrameTime1 + People.FrameTime2 + People.FrameTime3 + People.FrameTime4 + People.FrameTime5; // 毫秒


	@Override
	public void onCreateResources() {

	}

	@Override
	public void onReloadResources() {
		
	}

	@Override
	public void onCreateScene(Scene pScene) {
		this.mScene = pScene;
		Iterator<GameObject> it = getObjects().iterator();
		while (it.hasNext()) {
			it.next().onCreateScene(pScene);
		}
	}

	@Override
	public void onRelease() {

	}

	@Override
	public List<GameObject> getObjects() {
		return declearObject();
	}

	protected List<GameObject> declearObject() {
		if (mList == null) {
			mList = new ArrayList<GameObject>();
			Field[] fields = getClass().getDeclaredFields();
			for (Field f : fields) {
				try {
					f.setAccessible(true);
					Object obj = f.get(this);
					if (obj instanceof GameObject) {
						mList.add((GameObject) obj);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return mList;
	}

	@Override
	public void init() {
		if(mList !=null){
			mList.clear();
		}
		mList = null;
	}

	@Override
	public void release() {

	}

	@Override
	public void fixedUpdate() {
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) {
		MotionEvent me = pTouchEvent.getMotionEvent();
		switch (me.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchArea(me.getX());
			break;
		case MotionEvent.ACTION_POINTER_1_DOWN:
			break;
		case MotionEvent.ACTION_POINTER_2_DOWN:
			touchArea(me.getX(1));
			break;
		}
		return false;
	}

	private void touchArea(float x) {
		if(x < GameUtil.SCREEN_WIDTH / 2){
			if (System.currentTimeMillis() - touchDelayLeft > DeltaTouchTime) {
				touchDelayLeft = System.currentTimeMillis();
				onTouchLeft();
			}
		}else{
			if (System.currentTimeMillis() - touchDelayRight > DeltaTouchTime) {
				touchDelayRight = System.currentTimeMillis();
				onTouchRight();
			}
		}
	}
	
	public abstract void onTouchLeft();
	public abstract void onTouchRight();
}
