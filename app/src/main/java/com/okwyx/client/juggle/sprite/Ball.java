package com.okwyx.client.juggle.sprite;

import com.okwyx.client.juggle.Configation;
import com.okwyx.client.juggle.Constans;
import com.okwyx.client.juggle.JuggleApplication;
import com.okwyx.client.juggle.UserConfig;
import com.okwyx.client.juggle.mananger.CreateManager;
import com.okwyx.client.juggle.mananger.ResourceManager;
import com.okwyx.client.juggle.mananger.SoundManager;
import com.okwyx.client.juggle.object.PhysicGameObject;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;



public class Ball extends PhysicGameObject implements IUpdateHandler {
	private static final String TAG = "Ball";
	// 加速度
	public static int g = 1150;

	// 顶上去的最小速度
	public static int forceVMin = -500;
	// 顶上去的最大速度
	public static int forceVMax = -950;
	public static int forceVMid = -680;
	
	// 高空球速度
	public final int forceSuperHighV = -1500;

	public final int UpvelocityY = -380;
	
	public static boolean allreset = false;
	// rate 0 - 1 rate越小，踢球力度越大
	public enum BallStatus {
		Ready, Game, Over,Dead ,Pause
	}
	private float rate =-1;

	private float defaultV = -800;
	private BallStatus mBallStatus = BallStatus.Ready;
	private People mPeople;
	
	private OnchangeListener onChangeListener;
	
	// 是否是彩球
	private boolean isColorBall = false;
	private Sprite colorBall;
	
	private float pauseBallSpeedRecorder = 0;
	//下次再次颠球得分
	private int lastStartScore ; 
	
	//颠球个数
	private int juggleCount = 0;
	//控制下落只激活一次参数
	private boolean isTouch = false;
	public Ball(String imgName, int depth) {
		super(imgName, depth);
	}

	public Ball(String imgName, float x, float y, int depth) {
		super(imgName, x, y, depth);
	}

	public void setOnChanger(OnchangeListener onChangeListener) {
		this.onChangeListener = onChangeListener;
	}

	public int getJuggleCount() {
		return juggleCount;
	}
	
	public boolean isTouch() {
		return isTouch;
	}

	public void setTouch(boolean isTouch) {
		this.isTouch = isTouch;
	}

	public void setPeople(People p) {
		this.mPeople = p;
	}

	public int getLastStartScore() {
		return lastStartScore;
	}
	
	public People getPeople() {
		return mPeople;
	}

	/**
	 * Bottom Y
	 */
	@Override
	public float getY() {
		return super.getY() + getH();
	}
	
	@Override
	public void onCreateScene(Scene mScene) {
		super.onCreateScene(mScene);

		getSprite().registerUpdateHandler(this);
		getSprite().setVisible(false);
		mBallStatus = BallStatus.Ready;
		
		colorBall = ResourceManager.getSpritePool("football_2.png").obtainPoolItem(0, 0);
		colorBall.setZIndex(1);
		colorBall.setVisible(false);
		mSprite.attachChild(colorBall);
	}

	public void start() {
		mBallStatus = BallStatus.Game;
		lastStartScore = 0;
		getSprite().setVisible(true);
		getSprite().registerUpdateHandler(getPhysicsHandler());
		getPhysicsHandler().setAccelerationY(g);
		setY(Constans.BallDefaultY);
		getPhysicsHandler().setVelocityY(defaultV);
		mPeople.onTouch();
	}
	
	//黄牌
	public void dead(){
		lastStartScore = UserConfig.getInstance().getGold()+8+ Constans.r .nextInt(5);
		mBallStatus = BallStatus.Dead;
		SoundManager.playGameover(1); // 结束哨声
		getPhysicsHandler().setVelocityY(UpvelocityY);
		Configation.deadNum++;
		mPeople.reset();
	}
	
	
	public void over() {
		mBallStatus = BallStatus.Over;
		getPhysicsHandler().setVelocityY(UpvelocityY);
		if(!Configation.isGameOver){
			SoundManager.playGameover(1); // 结束哨声
		}
		mPeople.over();
	}
	
	public void reset() {
		mBallStatus = BallStatus.Ready;
		juggleCount = 0;
		lastY = Constans.BallTouchDownY + 30;
		getSprite().setVisible(false);
		getPhysicsHandler().setVelocityY(defaultV);
		setY(Constans.BallDefaultY);
		getSprite().unregisterUpdateHandler(getPhysicsHandler());
		
		setColorBall(false);
		
		mPeople.reset();
	}

	//修正坐标
	private float lastY =  Constans.BallTouchDownY + 30;
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		//retry tag
		if (getPhysicsHandler().getVelocityY() < 0) {
			isTouch = false;
			mPeople.setJuggle(false);
		}
		
		//guide
		if( (CreateManager.getInstance().getFrameCreate().isFirst()&& getY() >Constans.guideBallTouchUpMinY&& getY() < Constans.guideBallTouchUpMaxY && getPhysicsHandler().getVelocityY() > 0 )){
			if(getBallStatus() == BallStatus.Dead){
				return;
			}
			juggle(forceVMid);
			return;
		}
		// over logic
		if (getY() > lastY ) {
			lastY = getY() ;
			if( Configation.deadNum>=1 && getBallStatus() != BallStatus.Dead){
				if (getBallStatus() != BallStatus.Over) {
					over();
				} else {
					if (getPhysicsHandler().getVelocityY() > 0) {
						getSprite().unregisterUpdateHandler(getPhysicsHandler());
					}
				}
			}else{
				if (getBallStatus() != BallStatus.Dead){
					dead();
				}else{
					getSprite().unregisterUpdateHandler(getPhysicsHandler());
					setY( 30 );
					getSprite().setVisible(false);
				}
			}
		}
		// juggle logic
		if ((mPeople.isjunggle() && getY() > Constans.BallTouchUpY && getY() < Constans.BallTouchDownY && getPhysicsHandler().getVelocityY() > 0 ) ) {
			//成功踢到球的个数
			float force = getForce();
			juggle(force);
		}
	}

	private void juggle(float force) {
		// 普通球，踢一下加1分
		addScore(1);
		
		UserConfig.getInstance().addBallCount(1);
		
		juggleCount ++;
		// 高空球
		if (isSuperHighBall()) {
			getPhysicsHandler().setVelocityY(forceSuperHighV);
			addScore(9); // 额外9分
			SoundManager.playJugglePefect(1); // 高球欢呼
		} else {
				getPhysicsHandler().setVelocityY(force);
		}
		// 彩球
		if (isColorBall) {
			addScore(1); // 额外1分
			colorBallLogic();
		}
		
		if(rate < 0.5 ){
			// 踢的比较好
			SoundManager.playJuggleLow(1);
		} else {
			// 踢的一般
			SoundManager.playJuggle(1);
		}
		if (UserConfig.getInstance().getGold() % 10 == 0) {
			bigCheer();
		} else if (UserConfig.getInstance().getGold() % 5 == 0) {
			smallCheer();
		}
	}
	private float getForce() {
		long startJuggleTime = mPeople.startJuggleTime();
		long perfectTime = startJuggleTime + People.FrameTime2;
		long curTime = System.currentTimeMillis();
		//NOTICE 是有可能 >100的，因此force有大于0   1000
		rate = Math.abs(perfectTime - curTime) * 1.0f / People.FrameTime3;
		float force = forceVMax - (forceVMax - forceVMin) * rate;
		if(force >0){
			force = forceVMid;
		}
		return force;
	}
	
	private void addScore(int score) {
		if(UserConfig.getInstance().getGold() >= 9999){
			return;
		}
		UserConfig.getInstance().addGold(score);
        //TODO
//		GameStatusObserver.hitRefreshScore(UserConfig.getInstance().getGold());
		CreateManager.getInstance().getStaticCreate().setScore((int)UserConfig.getInstance().getGold());
		if(UserConfig.getInstance().getGold() >= Constans.score_wangfeng && !mPeople.getHead().getImgName().equals(Constans.wangfeng)){
			if(onChangeListener != null){
				onChangeListener.onchange(UserConfig.getInstance().getGold());
			}
		}
	}
	
	private void smallCheer() {
        JuggleApplication.mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				SoundManager.playSmallCheer(1);
				CreateManager.getInstance().getStaticCreate().smallCheer();
			}
		}, 500);
	}
	
	private void bigCheer() {
        JuggleApplication.mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				SoundManager.playBigCheer(1);
				CreateManager.getInstance().getStaticCreate().bigCheer();
			}
		}, 500);
	}

	public BallStatus getBallStatus() {
		return mBallStatus;
	}
	
	public static interface OnchangeListener{
		void onchange(int score);
	}

	public boolean isColorBall() {
		return isColorBall;
	}

	public void setColorBall(boolean isColorBall) {
		this.isColorBall = isColorBall;
		colorBall.setVisible(isColorBall);
	}
	
	/**
	 * 高空球 2%接近完美
	 * @return
	 */
	public boolean isSuperHighBall() {
		return rate != -1 && rate < Constans.SuperHighBallRamdom;
	}
	
	private void colorBallLogic() {
		CreateManager.getInstance().getFrameCreate().add();
		if(CreateManager.getInstance().getFrameCreate().getColorBallCount() >=10){
			CreateManager.getInstance().getFrameCreate().resetBall();
		}
	}
	
	public void startBall(){
		getPhysicsHandler().setVelocityY(pauseBallSpeedRecorder);
		mSprite.registerUpdateHandler(getPhysicsHandler());
		mPeople.onTouch();
//		isTouch = true;
	}
	
	public void resume(){
		mBallStatus = BallStatus.Game;
		getPhysicsHandler().setVelocityY(pauseBallSpeedRecorder);
		mSprite.registerUpdateHandler(getPhysicsHandler());
		mPeople.onTouch();
	}
	
	public void pause(boolean isPause) {
		if(isPause){
			mBallStatus = BallStatus.Pause;
			pauseBallSpeedRecorder = getPhysicsHandler().getVelocityY();
			getPhysicsHandler().setVelocityY(0);
			mSprite.unregisterUpdateHandler(getPhysicsHandler());
		}
		else{
			mBallStatus = BallStatus.Game;
			getPhysicsHandler().setVelocityY(pauseBallSpeedRecorder);
			mSprite.registerUpdateHandler(getPhysicsHandler());
		}
	}
}
