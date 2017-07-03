package com.okwyx.client.juggle.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("DefaultLocale")
public class GameUtil {

	public static int SCREEN_WIDTH, SCREEN_HEIGHT;
	public static TextureManager textureMgr;
	public static AssetManager assetMgr;
	public static VertexBufferObjectManager vertexBufferObjectMgr;
	private static Random random = new Random();
	private static Timer timer = new Timer();
	private static final Map<String, ITexture> textures = new HashMap<String, ITexture>();

	private static final int maxBitmapHeight = 640;
	
	public static ITextureRegion createTextureRegion(final String imgName) {
		try {
			ITexture texture = null;
			if (!textures.containsKey(imgName)) {
				texture = new BitmapTexture(textureMgr, new IInputStreamOpener() {
					@Override
					public InputStream open() throws IOException {
						// TODO Auto-generated method stub
						return assetMgr.open(imgName);
					}
				});
				texture.load();
				textures.put(imgName, texture);
			} else {
				texture = textures.get(imgName);
			}
			return TextureRegionFactory.extractFromTexture(texture);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Sprite createSprite(float x, float y, ITextureRegion pTextureRegion) {
		return new Sprite(x, y, pTextureRegion, vertexBufferObjectMgr);
	}

	public static final class GameDepth {
		public static final int BackGround = 0;
		public static final int Sunshine = 1;
		public static final int Sun = 2;
		public static final int Cloud = 3;
		public static final int Plane = 4;
		public static final int Rotatable3 = 5;
		public static final int Rotatable2 = 6;
		public static final int Rotatable1 = 7;
		public static final int Ground = 8;
		// 9 预留给道具背后的光
		public static final int Obstacle = 10;
		public static final int Gold = 11;
		public static final int People = 12;
	}

	public static int nextInt() {
		return random.nextInt();
	}

	public static int nextInt(int n) {
		return random.nextInt(n);
	}

	/**
	 * 下中点坐标转换成坐上点坐标
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @return
	 */
	public static PointF middleBottom2LeftTop(ITextureRegion img, float x, float y) {
		return middleBottom2LeftTop(img.getWidth(), img.getHeight(), x, y);
	}

	/**
	 * MiddleBottom -> LeftTop
	 * 
	 * @param w
	 * @param h
	 * @param x
	 * @param y
	 * @return
	 */
	public static PointF middleBottom2LeftTop(float w, float h, float x, float y) {
		PointF pt = new PointF();
		pt.x = x - w / 2;
		pt.y = y - h;
		return pt;
	}

	/**
	 * LeftTop -> MiddleBottom
	 * 
	 * @param w
	 * @param h
	 * @param x
	 * @param y
	 * @return
	 */
	public static PointF leftTop2MiddleBottom(float w, float h, float x, float y) {
		PointF pt = new PointF();
		pt.x = x + w / 2;
		pt.y = y + h;
		return pt;
	}

	/**
	 * LeftTop -> MiddleCenter
	 * 
	 * @param w
	 * @param h
	 * @param x
	 * @param y
	 * @return
	 */
	public static PointF leftTop2MiddleCenter(float w, float h, float x, float y) {
		PointF pt = new PointF();
		pt.x = x + w / 2;
		pt.y = y + h / 2;
		return pt;
	}

	public static long getCoinTime = 0;
	public static float getCoinRate = 1;

	public static float getCoinRate() {
		if (System.currentTimeMillis() - getCoinTime < 500) {
			getCoinRate += 0.1f;
			getCoinRate = Math.min(getCoinRate, 2f);// 音调太高，就不出声了
		} else {
			getCoinRate = 1f;
		}
		getCoinTime = System.currentTimeMillis();
		return getCoinRate;
	}

	public static void schedule(TimerTask task, long delay) {
		timer.schedule(task, delay);
	}

	public static double angle(double radian) {
		return 180 / Math.PI * radian;
	}

	public static double radian(double angle) {
		return Math.PI / 180 * angle;
	}

	public static double sin(double angle) {
		return Math.sin(radian(angle));
	}

	public static double cos(double angle) {
		return Math.cos(radian(angle));
	}

	public static double tan(double angle) {
		return Math.tan(radian(angle));
	}

	public static double cot(double angle) {
		return 1 / tan(radian(angle));
	}

	/**
	 * 转换前的坐标和转换后的坐标 都是以MiddleBottom坐标算的
	 * 
	 * @param originPoint
	 * @param angle
	 * @param r
	 * @return
	 */
	public static PointF getWorldPos(PointF originPoint, float angle, float r) {
		PointF newPoint = new PointF();
		if (angle >= 0) {
			newPoint.x = (float) (originPoint.x + sin(angle) * r);
			newPoint.y = (float) (originPoint.y + (r - cos(angle) * r));
		} else {
			newPoint.x = (float) (originPoint.x - sin(-angle) * r);
			newPoint.y = (float) (originPoint.y + (r - cos(-angle) * r));
		}
		return newPoint;
	}
	
	public static void playAudio(Context context , int resId) {
		try {
			MediaPlayer player = MediaPlayer.create(context, resId);
			player.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					relaxResources(mp, true);
				}
			});
			player.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	public static void relaxResources(MediaPlayer player, boolean releaseMediaPlayer) {
		try {
			if (releaseMediaPlayer && player != null) {
				player.reset();
				player.release();
				player = null;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	
	public static Bitmap mergeBitmap(Bitmap background, Bitmap foreground) {
		if (background == null) {
			return null;
		}
		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		// int fgWidth = foreground.getWidth();
		// int fgHeight = foreground.getHeight();
		// create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
		Bitmap newbmp = Bitmap.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
		Canvas cv = new Canvas(newbmp);
		// draw bg into
		cv.drawBitmap(background, 0, 0, null);// 在 0，0坐标开始画入bg
		// draw fg into
		cv.drawBitmap(foreground, 0, 0, null);// 在 0，0坐标开始画入fg ，可以从任意位置画入
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newbmp;
	}
	

	public static interface CallBack{
		void screenShortSuccess(String path);
		
		void screenShortFailtrue();
	}
	
	public static Bitmap scaledBitmap(Bitmap src_){
		
		try {
			int width = src_.getWidth();
			int height = src_.getHeight();
			
			if (height>maxBitmapHeight) {
				width = width * maxBitmapHeight / height;
				height = maxBitmapHeight;
			}
			return Bitmap.createScaledBitmap(src_, width, height, true);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return src_;
	}
	
}
