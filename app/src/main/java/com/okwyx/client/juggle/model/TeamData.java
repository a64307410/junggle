package com.okwyx.client.juggle.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TeamData {

    static List<Team> listTeam = null;

    public static List<Team> getTeamList(Context context) {
        if (listTeam == null) {
            listTeam = new ArrayList<Team>();
            for (int i = 1; i <= 33; i++) {
                listTeam.add(new Team(context, map.get(i), i + ""));
            }
        }
        return listTeam;
    }

    public static HashMap<Integer, Integer> map;

    static {
        map = new HashMap<Integer, Integer>();
        map.put(1, 1); //巴西
        map.put(2, 4); //喀麦隆
        map.put(3, 3); //墨西哥
        map.put(4, 2); //克罗地亚
        map.put(5, 5); //西班牙
        map.put(6, 7); //智利
        map.put(7, 8); //澳大利亚
        map.put(8, 6); //荷兰
        map.put(9, 9); //哥伦比亚
        map.put(10, 11); //科特迪瓦
        map.put(11, 12); //日本
        map.put(12, 10); //希腊
        map.put(13, 13); //乌拉圭
        map.put(14, 15); //英格兰
        map.put(15, 14); //哥斯达黎加
        map.put(16, 16); //意大利
        map.put(17, 17); //瑞士
        map.put(18, 18); //厄瓜多尔
        map.put(19, 20); //洪都拉斯
        map.put(20, 19); //法国
        map.put(21, 21); //阿根廷
        map.put(22, 24); //尼日利亚
        map.put(23, 23); //伊朗
        map.put(24, 22); //波黑
        map.put(25, 25); //德国
        map.put(26, 27); //加纳
        map.put(27, 28); //美国
        map.put(28, 26); //葡萄牙
        map.put(29, 29); //比利时
        map.put(30, 30); //阿尔及利亚
        map.put(31, 32); //韩国
        map.put(32, 31); //俄罗斯
        map.put(33, 33); //中国
    }


//	private static void initCids(){
//		map = new HashMap<Integer, Integer>();
//		map.put( 1 , 1 ); //巴西
//		map.put( 2 , 4 ); //喀麦隆
//		map.put( 3 , 3 ); //墨西哥
//		map.put( 4 , 2 ); //克罗地亚
//		map.put( 5 , 5 ); //西班牙
//		map.put( 6 , 7 ); //智利
//		map.put( 7 , 8 ); //澳大利亚
//		map.put( 8 , 6 ); //荷兰
//		map.put( 9 , 9 ); //哥伦比亚
//		map.put( 10 , 11 ); //科特迪瓦
//		map.put( 11 , 12 ); //日本
//		map.put( 12 , 10 ); //希腊
//		map.put( 13 , 13 ); //乌拉圭
//		map.put( 14 , 15 ); //英格兰
//		map.put( 15 , 14 ); //哥斯达黎加
//		map.put( 16 , 16 ); //意大利
//		map.put( 17 , 17 ); //瑞士
//		map.put( 18 , 18 ); //厄瓜多尔
//		map.put( 19 , 20 ); //洪都拉斯
//		map.put( 20 , 19 ); //法国
//		map.put( 21 , 21 ); //阿根廷
//		map.put( 22 , 24 ); //尼日利亚
//		map.put( 23 , 23 ); //伊朗
//		map.put( 24 , 22 ); //波黑
//		map.put( 25 , 25 ); //德国
//		map.put( 26 , 27 ); //加纳
//		map.put( 27 , 28 ); //美国
//		map.put( 28 , 26 ); //葡萄牙
//		map.put( 29 , 29 ); //比利时
//		map.put( 30 , 30 ); //阿尔及利亚
//		map.put( 31 , 32 ); //韩国
//		map.put( 32 , 31 ); //俄罗斯
//		map.put( 33 , 33 ); //中国
//	}

    public static int[] cids = new int[]{

    };


//	/**
//	 * 根据Cid 获取Team
//	 * @author lvxuejun
//	 * @date 2014-4-30
//	 * @param context
//	 * @param cid
//	 * @return
//	 */
//	public static Team getTeamByCid(Context context,int cid){
//		if (cid>=Constans.game_cid_base) {
//			cid = cid - Constans.game_cid_base;
//		}
//		for (Team team : getTeamList(context)) {
//			if (team.getCid() == cid) {
//				return team;
//			}
//		}
//		return listTeam.get(0);
//	}
//
//	public static int getTeamGroup(Context context,int index){
//		int resid = getDrawableId(context,"sina_sng_team_group"+ index);
//		if (resid<1) {
//			resid = R.drawable.sina_sng_team_group1;
//		}
//		return resid;
//	}

    public static int getDrawableId(Context context, String name) {

        return context.getResources().getIdentifier(name,
                "drawable", context.getApplicationContext().getPackageName());
    }

    public static Bitmap getBitmapByName(Context context, String assetPath) {
        Bitmap b = null;
        if (imageCache.containsKey(assetPath)) {
            if (imageCache.get(assetPath) != null) {
                b = imageCache.get(assetPath).get();
            }
        }
        if (b == null) {
            b = getImageFromAssetsFile(context, assetPath);
            if (b != null) {
                imageCache.put(assetPath, new SoftReference<Bitmap>(b));
            }
        }
        return b;
    }


    /**
     * 从Assets中读取图片
     */
    private static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;

    }

    // 为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
    public static Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();


}
