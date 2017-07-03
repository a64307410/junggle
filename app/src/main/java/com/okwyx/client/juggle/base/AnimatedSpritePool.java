package com.okwyx.client.juggle.base;

import com.okwyx.client.juggle.mananger.ResourceManager;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;



public class AnimatedSpritePool extends GenericPool<AnimatedSprite> {
	
	private ITiledTextureRegion mTiledRegion;
	private VertexBufferObjectManager mVertexBufferObjectMgr;
	
	public AnimatedSpritePool(ITiledTextureRegion pTiledRegion, VertexBufferObjectManager pVertexBufferObjectMgr) {
		this.mTiledRegion = pTiledRegion;
		this.mVertexBufferObjectMgr = pVertexBufferObjectMgr;
	}
	
	@Override
	protected AnimatedSprite onAllocatePoolItem() {
		return new AnimatedSprite(0, 0, mTiledRegion, mVertexBufferObjectMgr);
	}
	
	public synchronized AnimatedSprite obtainPoolItem(final float pX, final float pY) {
		AnimatedSprite sprite = super.obtainPoolItem();
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
	protected void onHandleRecycleItem(final AnimatedSprite pItem) {
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
