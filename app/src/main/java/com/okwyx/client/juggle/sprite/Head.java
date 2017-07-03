package com.okwyx.client.juggle.sprite;


import com.okwyx.client.juggle.object.FrameGameObject;

public class Head extends FrameGameObject {

	public Head(String imgName, int depth) {
		super(imgName, depth);
	}

	public Head(String imgName, float x, float y, int depth) {
		super(imgName, x, y, depth);
	}
}
