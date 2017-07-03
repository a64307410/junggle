package com.okwyx.client.juggle;

import java.util.Random;



public class Constans {

	
	public static Random r = new Random();
	

	public static final int CAMERA_WIDTH = 640;
	public static final int CAMERA_HEIGHT = 1136;
	
	public static final int FixedTime = 30; // 毫秒
	public static final int FixedTime_2 = 25;

	public static final int Ball1Num = 5;
	
	public static final int PeopleX1 = 50;
	public static final int PeopleX2 = 450;
	public static final int PeopleY = 800;
	public static final int BallDefaultX1 = 135;
	public static final int BallDefaultX2 = 455;
	public static final int BallDefaultY = 850;
	public static  int BallTouchUpY = 900;
	
	public static final int guideBallTouchUpMinY = 880;
	public static final int guideBallTouchUpMaxY = 1040;
	
	public static int BallTouchDownY = 960;
	
	public static int BallTouchDownY_default = 960;
	
	public static final int score_wangfeng = 150;
	public static final String wangfeng = "head2_2_4.png";
	
	public static final int ColorBallRandom = 4; // 0 - 100
	public static final float SuperHighBallRamdom = 0.02f; // 0 - 1
	
	public static final String HasTeached = "HasTeached";
	
	public static final int delayTime = 12000;
	public static final int cheerHeight = 280;
	

	/**
	 * 游戏随机cid的基础值
	 */
	public static int game_cid_base = 50;
	
}
