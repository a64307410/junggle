package com.okwyx.client.juggle.create;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.okwyx.client.juggle.Constans;
import com.okwyx.client.juggle.UserConfig;
import com.okwyx.client.juggle.base.Gamelayer;
import com.okwyx.client.juggle.mananger.CreateManager;
import com.okwyx.client.juggle.mananger.ResourceManager;
import com.okwyx.client.juggle.mananger.SoundManager;
import com.okwyx.client.juggle.object.FrameGameObject;
import com.okwyx.client.juggle.object.GameObject;
import com.okwyx.client.juggle.sprite.Ball;
import com.okwyx.client.juggle.sprite.Car;
import com.okwyx.client.juggle.sprite.Plane;
import com.okwyx.client.juggle.utils.GameUtil;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.FileBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TextureRegion;

import java.io.File;

/**
 * 静态场景
 */
public class StaticCreate extends BaseCreate{
	//刷新时间
	private static final int fixUpdateTime = 500;
	
	private static final int CheerFrameTime = 320;
	
	private GameObject bg1;
	private GameObject bg2;
	
	private GameObject cloud1;
	private GameObject cloud2;
	private GameObject cloud3;
	private GameObject cloud4;
	
	private GameObject score1;
	private GameObject score2;
	private GameObject score3;
	private GameObject score4;
	
	private FrameGameObject cheerLeft;
	private FrameGameObject cheerCenter;
	private FrameGameObject cheerRight;
	
	private GameObject banner;
	
	private String adName = "ad.png";
	private Sprite ad;
	private BitmapTextureAtlas adAtlas;
	private TextureRegion sdTextureRegion;
	
	
	private Plane plane;
	private String bannerName;
	private Car car;
	
	@Override
	public void init() {
		super.init();
		bg1 = new GameObject("bg1.png", Gamelayer.bg1);
		bg2 = new GameObject("bg2.png", Gamelayer.bg2);
		
		cloud1 = new GameObject("sina_sng_juggle_cloud.png", Gamelayer.cloud);
		cloud2 = new GameObject("sina_sng_juggle_cloud.png", Gamelayer.cloud);
		cloud3 = new GameObject("sina_sng_juggle_cloud.png", Gamelayer.cloud);
		cloud4 = new GameObject("sina_sng_juggle_cloud.png", Gamelayer.cloud);
		
		score1 = new GameObject("font_score_0.png", Gamelayer.score);
		score2 = new GameObject("font_score_0.png", Gamelayer.score);
		score3 = new GameObject("font_score_0.png", Gamelayer.score);
		score4 = new GameObject("font_score_0.png", Gamelayer.score);
		
		cheerLeft = new FrameGameObject("cheer_left_1_5.png", Gamelayer.cheer);
		cheerCenter = new FrameGameObject("cheer_center_5_1.png", Gamelayer.cheer);
		cheerRight = new FrameGameObject("cheer_right_1_5.png", Gamelayer.cheer);
		bannerName = UserConfig.getInstance().getSelectedTeam().getResFlagName();
		banner = new GameObject(bannerName, Gamelayer.score);
		
//		ad = new GameObject(adName, Gamelayer.score);
		
		plane = new Plane("plane1.png", Gamelayer.plane);
		
		car = new Car("car1_2_3.png", Gamelayer.car);
	}
	
	@Override
	public void onReloadResources() {
		super.onReloadResources();
		if(!bannerName.equals(UserConfig.getInstance().getSelectedTeam().getResFlagName())){
			this.bannerName = UserConfig.getInstance().getSelectedTeam().getResFlagName();
			banner.replaceSprite(bannerName);
			banner.getSprite().setScale(0.5f);
		}
	}

	@Override
	public void onCreateScene(Scene pScene) {
		super.onCreateScene(pScene);
		bg1.setPosition(0, 0);
		bg2.setPosition(0, Constans.CAMERA_HEIGHT - bg2.getH());
		
		cloud1.setPosition(Constans.CAMERA_WIDTH + cloud1.getW(), 20);
		cloud1.registerEntityModifier(new LoopEntityModifier(new MoveXModifier(30, Constans.CAMERA_WIDTH, -cloud1.getW()-200)));
		cloud2.setPosition(Constans.CAMERA_WIDTH + cloud1.getW(), 80);
		cloud2.registerEntityModifier(new LoopEntityModifier(new MoveXModifier(34, Constans.CAMERA_WIDTH +130, -cloud2.getW()-200)));
		cloud3.setPosition(Constans.CAMERA_WIDTH + cloud1.getW(), 140);
		cloud3.registerEntityModifier(new LoopEntityModifier(new MoveXModifier(30, Constans.CAMERA_WIDTH + 80, -cloud3.getW()-200)));
		cloud4.setPosition(Constans.CAMERA_WIDTH + cloud1.getW(), 180);
		cloud4.registerEntityModifier(new LoopEntityModifier(new MoveXModifier(40, Constans.CAMERA_WIDTH + 200, -cloud4.getW()-200)));
		
		score1.setPosition(130, 400);
		score2.setPosition(230, 400);
		score3.setPosition(330, 400);
		score4.setPosition(430, 400);
		score1.getSprite().setVisible(false);
		score4.getSprite().setVisible(false);
		
		cheerLeft.setPosition(-20, Constans.cheerHeight);
		cheerCenter.setPosition(70, Constans.cheerHeight-20);
		cheerRight.setPosition(530, Constans.cheerHeight);
		
		banner.setPosition(90, 330);
		banner.getSprite().setScale(0.5f);
		
		refreshAd();
		
		plane.setPosition(Constans.CAMERA_WIDTH + plane.getW(), 100);
		car.setPosition(Constans.CAMERA_WIDTH + plane.getW() , 570);
	}
	
	public void setScore(int score) {
		score = Math.min(score, 9999);
		String str_score;
		if (score < 10) {
			str_score = "000" + score;
		} else if (score < 100) {
			str_score = "00" + score;
		} else if (score < 1000) {
			str_score = "0" + score;
		} else {
			str_score = "" + score;
		}
		char[] char_score = str_score.toCharArray();
		if (score < 100) { // < 100 分的时候只显示两个号牌
			score1.getSprite().setVisible(false);
			score4.getSprite().setVisible(false);
			score2.setImg("font_score_" + Integer.parseInt(char_score[2] + "") + ".png");
			score3.setImg("font_score_" + Integer.parseInt(char_score[3] + "") + ".png");
		} else {
			score1.getSprite().setVisible(true);
			score4.getSprite().setVisible(true);
			score1.setImg("font_score_" + Integer.parseInt(char_score[0] + "") + ".png");
			score2.setImg("font_score_" + Integer.parseInt(char_score[1] + "") + ".png");
			score3.setImg("font_score_" + Integer.parseInt(char_score[2] + "") + ".png");
			score4.setImg("font_score_" + Integer.parseInt(char_score[3] + "") + ".png");
		}
	}
	
	public void smallCheer() {
		cheerLeft.getSprite().animate(new long[] {CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime}, 
				new int[] {0, 1, 2, 1, 2, 1, 2, 1, 0}, false);
		cheerCenter.getSprite().animate(new long[] {CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime}, 
				new int[] {0, 1, 2, 1, 2, 1, 2, 1, 0}, false);
		cheerRight.getSprite().animate(new long[] {CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime}, 
				new int[] {0, 1, 2, 1, 2, 1, 2, 1, 0}, false);
	}
	
	public void bigCheer() {
		cheerLeft.getSprite().animate(new long[] {CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime}, 
				new int[] {0, 3, 4, 3, 4, 3, 4, 3, 0}, false);
		cheerCenter.getSprite().animate(new long[] {CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime}, 
				new int[] {0, 3, 4, 3, 4, 3, 4, 3, 0}, false);
		cheerRight.getSprite().animate(new long[] {CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime, CheerFrameTime}, 
				new int[] {0, 3, 4, 3, 4, 3, 4, 3, 0}, false);
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pTouchEvent) {
		
		switch (pTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			if (plane.getSprite().contains(pTouchEvent.getX(), pTouchEvent.getY())) {
				SoundManager.playClick(1);
				plane.onPress();
				return true;
			}
			if(car.getSprite().contains(pTouchEvent.getX(), pTouchEvent.getY())){
				SoundManager.playClick(1);
				car.onPress();
				return true;
			}
			break;
		}
		
		return super.onSceneTouchEvent(pScene, pTouchEvent);
	}

	@Override
	public void onTouchLeft() {
		
	}

	@Override
	public void onTouchRight() {
		
	}

	public Plane getPlane() {
		return plane;
	}
	
	public Car getCar() {
		return car;
	}

	private void initAdResource() {
		if (UserConfig.getInstance().getBannerAdvertisement() != null) {
			if (adAtlas != null) {
				adAtlas.clearTextureAtlasSources();
			}
			File file =null;
			try {
				file = new File(UserConfig.getInstance().getBannerAdvertisement());  
				if(file.exists()){
					Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath(),new BitmapFactory.Options());
					bitmap.recycle();
					bitmap = null;
					adAtlas = new BitmapTextureAtlas(ResourceManager.getEngine().getTextureManager(), 1024, 512);
					FileBitmapTextureAtlasSource adSource = FileBitmapTextureAtlasSource.create(file);  
					sdTextureRegion = (TextureRegion) BitmapTextureAtlasTextureRegionFactory.createFromSource(adAtlas, adSource, 0, 0);  
					adAtlas.load();
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(file.exists()){
					file.delete();
				}
			}
			
		}
	}
	
	public void refreshAd() {
		ResourceManager.getEngine().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				try {
					if (ad != null) {
						ad.detachSelf();
					}
					if (UserConfig.getInstance().getBannerAdvertisement() == null) {
						ad = ResourceManager.getSpritePool(adName).obtainPoolItem(0, 585);
					} else {
						initAdResource();
						ad = new Sprite(0, 585, sdTextureRegion, ResourceManager.getEngine().getVertexBufferObjectManager());
					}
					ad.setZIndex(Gamelayer.score);
					mScene.attachChild(ad);
					mScene.sortChildren(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void reset() {
		plane.getSprite().setVisible(false);
		car.getSprite().setVisible(false);
	}
	
	private long lastTime = 0;
	/**
	 * 随机到飞机彩蛋 1%
	 * BUG 独立对象ball
	 */
	private void randomPlane() {
		long time = System.currentTimeMillis() - lastTime;
		if (GameUtil.nextInt(100) < Constans.ColorBallRandom && (time >=Constans.delayTime) ) {
			// 出飞机 0,1
			int random = GameUtil.nextInt(2);
			switch (random) {
			case 0:
			    getPlane().flyAnim();
				break;
			case 1:
				getCar().moveAnim();
				break;
			default:
				break;
			}
			lastTime = System.currentTimeMillis();
		}
	}
	private long lastFixUpdate;
	@Override
	public void fixedUpdate() {
		super.fixedUpdate();
		if(System.currentTimeMillis() - lastFixUpdate > fixUpdateTime){

			if (getPlane().getSprite() != null && getCar().getSprite() != null ) {
				if(CreateManager.getInstance().getFrameCreate().ball1.getBallStatus() == Ball.BallStatus.Game ||CreateManager.getInstance().getFrameCreate().ball2.getBallStatus()== Ball.BallStatus.Game ){
					randomPlane();
				}
			}
			lastFixUpdate = System.currentTimeMillis();
		}
	}
}
