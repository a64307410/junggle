package com.okwyx.client.juggle.mananger;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.TextUtils;

import com.okwyx.client.juggle.UserConfig;
import com.okwyx.client.juggle.base.AnimatedSpritePool;
import com.okwyx.client.juggle.base.ILifecycle;
import com.okwyx.client.juggle.base.SpritePool;
import com.okwyx.client.juggle.utils.GameUtil;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.HashMap;
import java.util.Map.Entry;


public class ResourceManager implements ILifecycle {

	private BaseGameActivity activity;
	private Engine mEngine;
	private Context mContext;
	private static final int width = 1024, height = 1024;
	private boolean init = false;
	
	private BuildableBitmapTextureAtlas atlas2 ;
	/**
	private String res2[] = new  String[] { 
			"sina_sng_juggle_bg.png","football.png" }; 
	*/
	private BuildableBitmapTextureAtlas atlas3 ;
	
	private String res[][] = new String[][] { 
			   { "football.png", "football_2.png", 
				 "font_score_0.png", "font_score_9.png",
				 "font_score_1.png", "font_score_2.png",
				 "font_score_3.png", "font_score_4.png", 
				 "font_score_5.png", "font_score_6.png", 
				 "font_score_7.png", "font_score_8.png",
				  }, 
				{ "bg1.png", "sina_sng_juggle_cloud.png", 
				  "ad.png"}, 
				{ "bg2.png", "plane1.png", "plane2.png" ,"red.png","yellow.png"},
			     // 默认国旗
			    {"sina_sng_team_flag1.png" } 
				};
	private BuildableBitmapTextureAtlas[] atlas = new BuildableBitmapTextureAtlas[res.length];
	private HashMap<String, ITextureRegion> regions = new HashMap<String, ITextureRegion>();
	public HashMap<String, SpritePool> spritePools = new HashMap<String, SpritePool>();

	private String frameRes[][] = new String[][] { 
			{ "cheer_left_1_5.png" ,"head2_2_4.png"}, 
			{ "head_2_4.png" ,"cheer_right_1_5.png"}, 
			{ "car1_2_3.png","cheer_center_5_1.png" }, 
			{"car2_2_3.png"},
			// 默认国家
			{ "sina_sng_player_2_4_1.png" } };
	private BuildableBitmapTextureAtlas[] frameAtlas = new BuildableBitmapTextureAtlas[frameRes.length];
	private HashMap<String, ITiledTextureRegion> frameRegions = new HashMap<String, ITiledTextureRegion>();
	private HashMap<String, AnimatedSpritePool> frameSpritePools = new HashMap<String, AnimatedSpritePool>();

	private ResourceManager() {
	};

	public static ResourceManager getInstance() {
		return Holder.rm;
	}

	private final static class Holder {
		public static ResourceManager rm = new ResourceManager();
	}

	public static BaseGameActivity getActivity() {
		return getInstance().activity;
	}

	public static Engine getEngine() {
		return getInstance().mEngine;
	}

	public static Context getContext() {
		return getInstance().mContext;
	}

	public static void setup(BaseGameActivity activity, Engine pEngine, Context pContext) {
		getInstance().activity = activity;
		getInstance().mEngine = pEngine;
		getInstance().mContext = pContext;
	}

	@Override
	public void init() {
		loadStaticResTextures();
		initSpritePool();
	}
	
	@Override
	public void release() {
		for (BuildableBitmapTextureAtlas atl : getInstance().atlas) {
			if(atl != null){
				atl.clearTextureAtlasSources();
				atl.unload();
			}
		}
		if(atlas2 != null){
			atlas2.clearTextureAtlasSources();
			atlas2.unload();
		}
		
		if(atlas3 != null){
			atlas3.clearTextureAtlasSources();
			atlas3.unload();
		}
		getInstance().regions.clear();
		getInstance().spritePools.clear();

		for (BuildableBitmapTextureAtlas at2 : getInstance().frameAtlas) {
			if(at2 !=null){
				at2.clearTextureAtlasSources();
				at2.unload();
			}
		}
		getInstance().frameRegions.clear();
		getInstance().frameSpritePools.clear();
		init = false;
	}
	
	public static void initSpritePool() {
		for (Entry<String, ITextureRegion> entry : getInstance().regions.entrySet()) {
			ITextureRegion region = entry.getValue();
			if (!getInstance().spritePools.containsKey(entry.getKey())) {
				SpritePool spritePool = new SpritePool(region, getInstance().mEngine.getVertexBufferObjectManager());
				getInstance().spritePools.put(entry.getKey(), spritePool);
			}
		}
		for (Entry<String, ITiledTextureRegion> entry : getInstance().frameRegions.entrySet()) {
			ITiledTextureRegion region = entry.getValue();
			if (!getInstance().frameSpritePools.containsKey(entry.getKey())) {
				AnimatedSpritePool asp = new AnimatedSpritePool(region, getInstance().mEngine.getVertexBufferObjectManager());
				getInstance().frameSpritePools.put(entry.getKey(), asp);
			}
		}
	}
	
	public void reLoad(String newResFlag, String newPlayerName) {
		// 国旗
		int resLength = res.length;
		String oldResFlag = res[resLength - 1][res[resLength - 1].length - 1];
		if (!newResFlag.equals(oldResFlag)) {
			// replace
			res[resLength - 1][res[resLength - 1].length - 1] = newResFlag;
			atlas[atlas.length - 1].unload();
			regions.remove(oldResFlag);
			spritePools.remove(oldResFlag);
			
			atlas[atlas.length - 1] = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), width, height);
			loadITextureRegion(atlas[atlas.length - 1], newResFlag);
			try {
				atlas[atlas.length - 1].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
				atlas[atlas.length - 1].load();
			} catch (TextureAtlasBuilderException e) {
				e.printStackTrace();
			}
		}
		String oldPlayerName = frameRes[frameAtlas.length - 1][frameRes[frameAtlas.length - 1].length - 1];
		if (!oldPlayerName.equals(newPlayerName)) {
			frameRes[frameAtlas.length - 1][frameRes[frameAtlas.length - 1].length - 1] = newPlayerName;
			// frameAtlas[frameAtlas.length-1].unload();
			frameRegions.remove(oldPlayerName);
			frameSpritePools.remove(oldPlayerName);
			frameAtlas[frameAtlas.length - 1] = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), width, height);
			loadPlayerTextureRegion(frameAtlas[frameAtlas.length - 1], newPlayerName);
			try {
				frameAtlas[frameAtlas.length - 1].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
				frameAtlas[frameAtlas.length - 1].load();
			} catch (TextureAtlasBuilderException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadStaticResTextures() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		if (init) {
			reLoad(UserConfig.getInstance().getBannerName(), UserConfig.getInstance().getPlayerName());
		}
		// 装载静态资源
		for (int i = 0; i < atlas.length; i++) {
			if (i == 0) {
				atlas[i] = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), width * 2, height * 2);
			} else {
				atlas[i] = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), width, height);
			}
			for (int j = 0; j < res[i].length; j++) {
				// 最后一个纹理给可变资源-国家
				if (i == atlas.length - 1) {
					// 加载国旗图片资源
					res[i][j] = UserConfig.getInstance().getSelectedTeam().getResFlagName();
				}
				loadITextureRegion(atlas[i], res[i][j]);
			}
			try {
				atlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
				atlas[i].load();
			} catch (TextureAtlasBuilderException e) {
				e.printStackTrace();
			}
		}
		// 装载frame动画资源
		for (int i = 0; i < frameAtlas.length; i++) {
			frameAtlas[i] = new BuildableBitmapTextureAtlas(mEngine.getTextureManager(), width, height);
			for (int j = 0; j < frameRes[i].length; j++) {
				if (i == frameAtlas.length - 1) {
					frameRes[i][j] = UserConfig.getInstance().getPlayerName();
					loadPlayerTextureRegion(frameAtlas[i], frameRes[i][j]);
				} else {
					loadFrameTextureRegion(frameAtlas[i], frameRes[i][j]);
				}
			}
			try {
				frameAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
				frameAtlas[i].load();
			} catch (TextureAtlasBuilderException e) {
				e.printStackTrace();
			}
		}
		init = true;
	}

	private static ITextureRegion loadITextureRegion(BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas, String resName) {
		ITextureRegion region = getInstance().regions.get(resName);
		if (region == null) {
			region = getInstance().loadResTexture(mBuildableBitmapTextureAtlas, resName);
			getInstance().regions.put(resName, region);
		}
		return region;
	}

	private ITextureRegion loadResTexture(BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas, String name) {
		ITextureRegion region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBuildableBitmapTextureAtlas, mContext, name);
		return region;
	}

	private static ITiledTextureRegion loadPlayerTextureRegion(BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas, String resName) {
		ITiledTextureRegion region = getInstance().frameRegions.get(resName);
		if (region == null) {
			region = getInstance().loadPlayerResTexture(mBuildableBitmapTextureAtlas, resName);
			getInstance().frameRegions.put(resName, region);
		}
		return region;
	}

	private static ITiledTextureRegion loadFrameTextureRegion(BuildableBitmapTextureAtlas mBuildableBitmapTextureAtlas, String resName) {
		ITiledTextureRegion region = getInstance().frameRegions.get(resName);
		if (region == null) {
			region = getInstance().loadFrameResTexture(mBuildableBitmapTextureAtlas, resName);
			getInstance().frameRegions.put(resName, region);
		}
		return region;
	}

	private ITiledTextureRegion loadPlayerResTexture(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, String name) {
		// sina_sng_player_2_4_1.png
		name = name.substring(0, name.indexOf(".png"));
		String[] strs = name.split("_");
		int rows = Integer.parseInt(strs[strs.length - 3]); // 行
		int columns = Integer.parseInt(strs[strs.length - 2]); // 列
		ITiledTextureRegion region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(pBuildableBitmapTextureAtlas, GameUtil.assetMgr, name + ".png", columns, rows);
		return region;
	}

	private ITiledTextureRegion loadFrameResTexture(BuildableBitmapTextureAtlas pBuildableBitmapTextureAtlas, String name) {
		// sina_sng_football_item_stars_1_3.png
		name = name.substring(0, name.indexOf(".png"));
		String[] strs = name.split("_");
		int rows = Integer.parseInt(strs[strs.length - 2]); // 行
		int columns = Integer.parseInt(strs[strs.length - 1]); // 列
		ITiledTextureRegion region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(pBuildableBitmapTextureAtlas, GameUtil.assetMgr, name + ".png", columns, rows);
		return region;
	}

	public static ITextureRegion getITextureRegion(String resName) {
		return getInstance().regions.get(resName);
	}

	public static SpritePool getSpritePool(String resName) {
		return getInstance().spritePools.get(resName);
	}

	public static ITiledTextureRegion getTitledRegion(String resName) {
		return getInstance().frameRegions.get(resName);
	}

	public static AnimatedSpritePool getAnimatedSpritePool(String resName) {
		return getInstance().frameSpritePools.get(resName);
	}

	public static Spanned getScoreHtml(String resName, long score) {
		if (score < 0) {
			score = 0;
		}
		String strScore = String.valueOf(score);
		String html = "<span style=\"font-size:2px; color:#000000\">&nbsp;</span>";
		for (int i = 0; i < strScore.length(); i++) {
			html += "<img src=\"" + resName + strScore.charAt(i) + "\"/>";
		}
		return Html.fromHtml(html, imgGetter, null);
	}
	
	public static Spanned getScoreHtml(String resName , String fuhao,long score ){
		if (score < 0) {
			score = 0;
		}
		String strScore = String.valueOf(score);
		String html = "<span style=\"font-size:2px; color:#000000\">&nbsp;</span>";
		for (int i = 0; i < strScore.length(); i++) {
			html += "<img src=\"" + resName + strScore.charAt(i) + "\"/>";
		}
		html +="<img src=\"" +fuhao +"\"/>";
		
		return Html.fromHtml(html, imgGetter, null);
	}
	
	public static Spanned getPercentHtml(String percent) {
		if(TextUtils.isEmpty(percent)){
			percent  = "0%";
		}
		char[] charArray = percent.toCharArray();
		String html = "<span style=\"font-size:2px; color:#000000\">&nbsp;</span>";
		for (int i = 0; i < charArray.length; i++) {
			String name = null ;
			if(charArray[i] == '%'){
				name = "<img src=\"sina_sng_football_score_percent" + "\"/>";
			}else if(charArray[i] == '.'){
				name = "<img src=\"sina_sng_football_score_dian" + "\"/>";
			}else{
				name = "<img src=\"sina_sng_football_score_s_" +charArray[i] + "\"/>";
			}
			html +=name;
		}
		return Html.fromHtml(html, imgGetter, null);
	}
	
	
	public static Spanned getScoreHtml(long score) {
		if (score < 0) {
			score = 0;
		}
		String strScore = String.valueOf(score);
		String html = "<span style=\"font-size:2px; color:#000000\">&nbsp;</span>";
		for (int i = 0; i < strScore.length(); i++) {
			html += "<img src=\"sina_sng_football_score_" + strScore.charAt(i) + "\"/>";
		}
		return Html.fromHtml(html, imgGetter, null);
	}
	
	public static Spanned getScoreHtmlBig(long score) {
		if (score < 0) {
			score = 0;
		}
		String strScore = String.valueOf(score);
		String html = "<span style=\"font-size:2px; color:#000000\">&nbsp;</span>";
		for (int i = 0; i < strScore.length(); i++) {
			html += "<img src=\"sina_sng_football_score_l_" + strScore.charAt(i) + "\"/>";
		}
		return Html.fromHtml(html, imgGetter, null);
	}
	
	static ImageGetter imgGetter = new ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			try {
				int id = getInstance().getDrawable(source);
				drawable = getContext().getResources().getDrawable(id);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			return drawable;
		}
	};

	public int getDrawable(String name) {
		return getContext().getResources().getIdentifier(name, "drawable", getContext().getPackageName());
	}
}
