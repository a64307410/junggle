package com.okwyx.client.juggle.object;

import org.andengine.engine.handler.physics.PhysicsHandler;

public class PhysicGameObject extends GameObject {
	
	
	private PhysicsHandler mPhysicsHandler;
	
	public PhysicGameObject(String imgName, float x, float y, int depth) {
		super(imgName, x, y, depth);
	}

	public PhysicGameObject(String imgName, int depth) {
		super(imgName, depth);
	}

	
	public PhysicsHandler getPhysicsHandler(){
		if(mPhysicsHandler == null){
			if(getSprite() != null){
				mPhysicsHandler = new PhysicsHandler(getSprite());
			}
		}
		return mPhysicsHandler;
	}
	
	
	
	
	public static enum PhysicStatus{
		up,
		down,
	}
	
	
}
