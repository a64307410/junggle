package com.okwyx.client.juggle.mananger;

import com.okwyx.client.juggle.UserConfig;
import com.okwyx.client.juggle.base.IGameObject;
import com.okwyx.client.juggle.base.ILifecycle;
import com.okwyx.client.juggle.base.ILogic;
import com.okwyx.client.juggle.create.BaseCreate;
import com.okwyx.client.juggle.create.FrameCreate;
import com.okwyx.client.juggle.create.StaticCreate;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CreateManager implements IGameObject, ILifecycle, IOnSceneTouchListener, ILogic {

	private List<BaseCreate> creates1 = new ArrayList<BaseCreate>();
	private List<BaseCreate> creates2 = new ArrayList<BaseCreate>();

	private CreateManager() {
		StaticCreate staticCreate = new StaticCreate();
		creates1.add(staticCreate);
		FrameCreate frameCreate = new FrameCreate();
		creates1.add(frameCreate);
//		ChooseTeamStaticCreate ctsc = new ChooseTeamStaticCreate();
//		creates2.add(ctsc);
//		ChooseTeamFrameCreate ctfc = new ChooseTeamFrameCreate();
//		creates2.add(ctfc);
	}

	public StaticCreate getStaticCreate() {
		return (StaticCreate) creates1.get(0);
	}

	public FrameCreate getFrameCreate() {
		return (FrameCreate) creates1.get(1);
	}

//	public ChooseTeamStaticCreate getChooseTeamStaticCreate() {
//		return (ChooseTeamStaticCreate) creates2.get(0);
//	}
//
//	public ChooseTeamFrameCreate getChooseTeamFrameCreate() {
//		return (ChooseTeamFrameCreate) creates2.get(1);
//	}

	public static CreateManager getInstance() {
		return Holder.instance;
	}

	private static final class Holder {
		public static final CreateManager instance = new CreateManager();
	}
	
	private Iterator<BaseCreate> getCurrentBaseCreate(){
		Iterator<BaseCreate> it = null;
		if (UserConfig.getInstance() != null
				&& UserConfig.getInstance().getCurScene() != null)
		{
//			switch (UserConfig.getInstance().getCurScene()) {
//			case ChooseTeam:
//				it = creates2.iterator();
//				break;
//			case Main:
				it = creates1.iterator();
//				break;
//			}
		}
		return it;
	}
	
	@Override
	public void onCreateResources() {
		Iterator<BaseCreate> it = getCurrentBaseCreate();
		while (it.hasNext()) {
			it.next().onCreateResources();
		}
	}

	@Override
	public void onCreateScene(Scene scene) {
		// //初始化精灵池
		Iterator<BaseCreate> it = getCurrentBaseCreate();
		while (it.hasNext()) {
			it.next().onCreateScene(scene);
		}
	}

	@Override
	public void onRelease() {
		release();
	}

	@Override
	public void init() {
		Iterator<BaseCreate> it = getCurrentBaseCreate();
		while (it.hasNext()) {
			it.next().init();
		}
	}

	@Override
	public void release() {
		Iterator<BaseCreate> it = getCurrentBaseCreate();
		while (it.hasNext()) {
			it.next().release();
		}
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		Iterator<BaseCreate> it = getCurrentBaseCreate();
		if(it != null){
			while (it.hasNext()) {
				if(it.next().onSceneTouchEvent(pScene, pSceneTouchEvent)){
					break;
				}
			}
		}
		return false;
	}

	@Override
	public void fixedUpdate() {
		Iterator<BaseCreate> it = getCurrentBaseCreate();
		if(it != null)
		{
			while (it.hasNext()) {
				it.next().fixedUpdate();
			}
		}
	}

	@Override
	public void onReloadResources() {
		Iterator<BaseCreate> it = getCurrentBaseCreate();
		if(it != null){
			while (it.hasNext()) {
				it.next().onReloadResources();
			}
		}
	}
}
