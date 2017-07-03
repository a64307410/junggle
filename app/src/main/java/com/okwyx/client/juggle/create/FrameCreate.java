package com.okwyx.client.juggle.create;

import com.okwyx.client.juggle.Configation;
import com.okwyx.client.juggle.Constans;
import com.okwyx.client.juggle.JuggleApplication;
import com.okwyx.client.juggle.UserConfig;
import com.okwyx.client.juggle.base.Gamelayer;
import com.okwyx.client.juggle.base.ILifecycle;
import com.okwyx.client.juggle.mananger.CreateManager;
import com.okwyx.client.juggle.mananger.ResourceManager;
import com.okwyx.client.juggle.object.GameObject;
import com.okwyx.client.juggle.sprite.Ball;
import com.okwyx.client.juggle.sprite.Ball.BallStatus;
import com.okwyx.client.juggle.sprite.Head;
import com.okwyx.client.juggle.sprite.People;
import com.okwyx.client.juggle.utils.PreferencesUtils;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;

import java.util.List;




public class FrameCreate extends BaseCreate implements IOnSceneTouchListener , ILifecycle, Ball.OnchangeListener {
	private static final String guid_first_1 = "guid_first_1";
	private static final String guid_first_2 = "guid_first_2";
	private static final int countMin =1;
	protected GameObject redcard;
	protected GameObject yellowcard;
	
	protected Head head1;
	protected Head head2;
	protected People people1;
	protected People people2;
	protected Ball ball1;
	protected Ball ball2;
	
	private String playerName;
	
	private int colorBallCount = 0;
	
	/** 向导模式 */
	private boolean isFirst;
	/** 第一次向导标志 */
	private boolean isFirst1;
	/** 第二次向导标志 */
	private boolean isFirst2;
	
	private boolean tag;
	private int guidCount1 ;
	private int guidCount2;
	private boolean dead;
	
	private int nextBall_juggle = getNextRandom();
	private static int getNextRandom(){
		if(UserConfig.getInstance().getMaxScore() >= 30){
			return 6 + Constans.r.nextInt(7);
		}else{
			return 12 + Constans.r.nextInt(4);
		}
	}
	
	public boolean isFirst() {
		return isFirst;
	}

	public void resetBall(){
		ball1.setColorBall(false);
		ball2.setColorBall(false);
		colorBallCount = 0;
	}
	public void add(){
		colorBallCount++;
	}
	
	public int getColorBallCount() {
		return colorBallCount;
	}
	@Override
	public void init() {
		super.init();
		isFirst1 =  PreferencesUtils.loadPrefBoolean(JuggleApplication.instance, guid_first_1, true);
		isFirst2 = PreferencesUtils.loadPrefBoolean(JuggleApplication.instance, guid_first_2, true);
		tag = isFirst1;
		isFirst = isFirst1;
		
		playerName = UserConfig.getInstance().getPlayerName();
		String headName = UserConfig.getInstance().getHeadName();
		
		redcard =  new GameObject("red.png",  Gamelayer.score);
		yellowcard =  new GameObject("yellow.png",  Gamelayer.score);
		
		head1 = new Head(headName, Gamelayer.head);
		head2 = new Head(headName, Gamelayer.head);
		
		people1 = new People(playerName, Gamelayer.people);
		people2 = new People(playerName, Gamelayer.people);
		
		ball1 = new Ball("football.png", Gamelayer.football);
		ball2 = new Ball("football.png", Gamelayer.football);
		
		ball1.setOnChanger(this);
		ball2.setOnChanger(this);
	}

	@Override
	public List<GameObject> getObjects() {
		return declearObject();
	}

	@Override
	public void onCreateResources() {
		super.onCreateResources();
	}

	@Override
	public void onCreateScene(Scene pScene) {
		super.onCreateScene(pScene);
		redcard.setPosition(433,330);
		redcard.getSprite().setScale(0.5f);
		yellowcard.setPosition(433,330);
		yellowcard.getSprite().setScale(0.5f);
		redcard.getSprite().setVisible(false);
		yellowcard.getSprite().setVisible(false);
		
		head1.setPosition(Constans.PeopleX1, Constans.PeopleY);
		head2.setPosition(Constans.PeopleX2, Constans.PeopleY);
		head2.getSprite().setScaleX(-1);
		
		people1.setPosition(Constans.PeopleX1, Constans.PeopleY);
		people2.setPosition(Constans.PeopleX2, Constans.PeopleY);
		people2.getSprite().setScaleX(-1);
		
		people1.setHead(head1);
		people2.setHead(head2);
		
		ball1.setPosition(Constans.BallDefaultX1, Constans.BallDefaultY);
		ball2.setPosition(Constans.BallDefaultX2, Constans.BallDefaultY);
		
		ball1.setPeople(people1);
		ball2.setPeople(people2);
	}

	@Override
	public void onTouchLeft() {
		if(ball1.getBallStatus() == Ball.BallStatus.Game){
			people1.onTouch();
		}else{
			people1.start();
		}
		
	}
	
	@Override
	public void onTouchRight() {
		if(ball2.getBallStatus() == Ball.BallStatus.Game){
			people2.onTouch();
		}else{
			people2.start();
		}
	}

	@Override
	public void fixedUpdate() {
		super.fixedUpdate();
		if(Configation.isGameOver ){
			return;
		}
		//死亡
		if(ball1.getBallStatus() == BallStatus.Over || ball2.getBallStatus() == BallStatus.Over){
			Configation.isGameOver = true;
			yellowcard.getSprite().setVisible(false);
			redcard.getSprite().setVisible(true);
//TODO			GameStatusObserver.hitGameOver();
			return;
		}
		
		//随机出现第二个球
		if(ball2.getBallStatus() == BallStatus.Ready){
			if(ball1.getJuggleCount() == nextBall_juggle || ball1.getBallStatus() == BallStatus.Dead){
				if (ball1.getBallStatus() == Ball.BallStatus.Dead && !yellowcard.getSprite().isVisible()) {
					yellowcard.getSprite().setVisible(true);
				}
				//向导
				if(isFirst2){
					if(ball1.getBallStatus() == BallStatus.Game){
						isFirst1 = true;
					}
					isFirst = true;
				}
				ball2.start();
			}
		}else{
			mayBeRestartBall(ball1);
			mayBeRestartBall(ball2);
		}
		/**向导模式 */
		//第一个球死亡
		if(ball1.getBallStatus() == BallStatus.Dead){
			dead =true;
			if(!tag){
				isFirst2 = false;
				isFirst = false;
			}
		}
		
		if(isFirst1  && isDown(ball1) && needPause(ball1)){
			isFirst = true;
			guidCount1++;
			if(guidCount1 ==countMin ){
				isFirst1 = false;
				guidCount1 = 0;
				PreferencesUtils.savePrefBoolean(JuggleApplication.instance, guid_first_1, false);
			}
			ball1.setTouch(true);
			ResourceManager.getEngine().stop();
//TODO			GameStatusObserver.hitGameGuideListener(1);
		}
		if(isFirst2 && isDown(ball2) && needPause(ball2)){
			guidCount2++;
			isFirst = true;
			//two ball
			if(!dead){
				PreferencesUtils.savePrefBoolean(JuggleApplication.instance, guid_first_2, false);
			}
			if(guidCount2 == countMin){
				isFirst2 = false;
			}
			ball2.setTouch(true);
			ResourceManager.getEngine().stop();
//TODO			GameStatusObserver.hitGameGuideListener(2);
		}
		

		if (isFirst) {
			if (ball1.getJuggleCount() == countMin) {
				isFirst = false;
			}
			if (dead) {
				if (ball2.getJuggleCount() == countMin) {
					isFirst = false;
				}
			} else {
				if (ball1.getJuggleCount() >= nextBall_juggle+countMin && ball2.getJuggleCount() >=countMin) {
					isFirst = false;
				}
			}
		}
		
	}
	
	private void mayBeRestartBall(Ball ball){
		if (ball.getBallStatus() == BallStatus.Dead) {
			if (!yellowcard.getSprite().isVisible()) {
				yellowcard.getSprite().setVisible(true);
			}
			if (UserConfig.getInstance().getGold() >= ball.getLastStartScore()) {
				ball.start();
			}
		}
	}
	
	private boolean needPause(Ball ball){
		float ms = (People.FrameTime1 + People.FrameTime2 + Constans.FixedTime_2)/1000f;
		float offsetTime = Constans.FixedTime_2/1000f;
		float offset = ball.getPhysicsHandler().getVelocityY() * offsetTime + (0.5f) *  Ball.g * offsetTime*offsetTime;
		float h = Constans.BallTouchUpY - ball.getY() -offset;
		float h2 = ball.getPhysicsHandler().getVelocityY()* ms + (0.5f) *  Ball.g * ms*ms;
		float h3 =  Constans.BallTouchDownY - ball.getY() + offset;
		return h2 >=h && h2 <=h3;
	}
	private boolean isDown(Ball ball){
		return ball.getBallStatus() == Ball.BallStatus.Game  &&!ball.isTouch()&& ball.getPhysicsHandler().getVelocityY() > 0 && ResourceManager.getEngine().isRunning();
	}
	
	public void start(){
		ball1.start();
	}

	public void reset(){
		//
		ball1.reset();
		ball2.reset();
		dead = false;
		guidCount1 = 0;
		guidCount2 = 0;
		resetBall();
		redcard.getSprite().setVisible(false);
		yellowcard.getSprite().setVisible(false);
		Configation.deadNum = 0;
		nextBall_juggle = getNextRandom();
		CreateManager.getInstance().getStaticCreate().setScore(0);
	}

	@Override
	public void release() {
	}

	@Override
	public void onReloadResources() {
		if(!playerName.equals(UserConfig.getInstance().getPlayerName())){
				this.playerName = UserConfig.getInstance().getPlayerName();
				people1.replaceSprite(playerName);
				people2.replaceSprite(playerName);
				people2.getSprite().setScaleX(-1);
			}
	}

	@Override
	public void onchange(int score) {
		head1.replaceSprite(Constans.wangfeng);
		head2.replaceSprite(Constans.wangfeng);
	}
	
	public void setColorBall(boolean isColorBall) {
		ball1.setColorBall(isColorBall);
		ball2.setColorBall(isColorBall);
	}
	
	public void pause(boolean isPause){
		ball1.pause(isPause);
		if(ball2.getSprite().isVisible()){
			ball2.pause(isPause);
		}
	}
	
	public void resumeAll(){
		pause(false);
	}
	
	public void resumeBall(int type){
		ResourceManager.getEngine().start();
		switch (type) {
		case 1:
			ball1.getPeople().onTouch();
			break;
		case 2:
			ball2.getPeople().onTouch();
			break;
		}
		
	}
	
}
