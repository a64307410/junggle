package com.okwyx.client.juggle.base;

import com.okwyx.client.juggle.mananger.ResourceManager;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;



public class SpritePool extends GenericPool<Sprite> {

	private ITextureRegion mTextureRegion;
	private VertexBufferObjectManager mVertexBufferObjectManager;

	public SpritePool(ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		this.mTextureRegion = pTextureRegion;
		this.mVertexBufferObjectManager = pVertexBufferObjectManager;
	}

	@Override
	protected Sprite onAllocatePoolItem() {
		return new Sprite(0, 0, this.mTextureRegion, this.mVertexBufferObjectManager);
	}

	public synchronized Sprite obtainPoolItem(final float pX, final float pY) {
		Sprite sprite = super.obtainPoolItem();
		sprite.setPosition(pX, pY);
		sprite.setRotation(0);
		sprite.setVisible(true);
		sprite.setIgnoreUpdate(false);
//		sprite.setCullingEnabled(true);
		sprite.setScale(1);
		sprite.setColor(1, 1, 1, 1);
		return sprite;
	}

	@Override
	protected void onHandleRecycleItem(final Sprite pItem) {
		super.onHandleRecycleItem(pItem);
		
		ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				pItem.detachSelf();
			}
		});
		pItem.setVisible(false);
		pItem.setIgnoreUpdate(true);
		pItem.clearEntityModifiers();
		pItem.clearUpdateHandlers();
	}

}
