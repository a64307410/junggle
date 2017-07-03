package com.okwyx.client.juggle;

import com.okwyx.client.framework.libs.cache.SerializationCache;
import com.okwyx.client.juggle.base.SceneName;
import com.okwyx.client.juggle.data.DefaultData;
import com.okwyx.client.juggle.model.Team;
import com.okwyx.client.juggle.sprite.People;
import com.weibo.game.storage.dir.DirType;
import com.weibo.game.storage.dir.DirectoryManager;

import java.io.Serializable;

public class UserConfig implements Serializable{
	private static final long serialVersionUID = 1L;

    private static final String RESTORE = "userconfigrestore";
    private SerializationCache<UserConfig> cacheOperate =new SerializationCache<UserConfig>(DirectoryManager.getInstance().getDir(DirType.USER));


    private transient int gold;
	private transient int ball_count;
	private transient String gameId;

	/** 默认国旗、角色、头部 */
	private String bannerName = DefaultData.countryName;
	private String playerName = DefaultData.playerBody;
	private String headName = DefaultData.playerHead;
	
	private String bannerAdvertisement;
	private transient SceneName curScene = SceneName.Main;
	
	private int maxScore = 0;

	private Team selectedTeam;
	
//	private Rank championRank;
	
	private String uid = "0";
	
//	private User user;
	
	private String showoffMessage;
	private String showoffMessageV;
	
	
	/** 新增字段，是否是微博第一次打开,false第一次打开 */
	private boolean isWeiboOpen;
	/** 是否关注官方微博 */
	private boolean feedWeibo = true;
	private UserConfig() {
	}

	public static UserConfig getInstance() {
		return Holder.instance;
	}
	
//	public static void loadConfig(){
//		Holder.loadConfig();
//	}

	static class Holder {
		public static UserConfig instance = new UserConfig();

//		public static void loadConfig(){
//			instance = UserConfigRestore.getInstance().getConfig();
//			if (instance == null) {
//				instance = new UserConfig();
//			}
//		}
	}
	
	public boolean isFeedWeibo() {
		return feedWeibo;
	}

	public void setFeedWeibo(boolean feedWeibo) {
		this.feedWeibo = feedWeibo;
	}

	public boolean isWeiboOpen() {
		return isWeiboOpen;
	}

	public void setWeiboOpen(boolean isWeiboOpen) {
		this.isWeiboOpen = isWeiboOpen;
	}

	public String getBannerAdvertisement() {
		return bannerAdvertisement;
	}

	public void setBannerAdvertisement(String bannerAdvertisement) {
		this.bannerAdvertisement = bannerAdvertisement;
	}

	public int getGold() {
		return gold;
	}
	public synchronized void addGold(int gold) {
		this.gold += gold;
	}
	
	
	public int getBallCount() {
		return ball_count;
	}
	public synchronized void addBallCount(int gold) {
		this.ball_count += gold;
	}
	
	public static void reset(){
		getInstance().gold =0;
		getInstance().ball_count =0;
		Constans.BallTouchDownY = Constans.BallTouchDownY_default;
		
		People.FrameTime2 = People.FrameTime2_default;
		People.FrameTime3 = People.FrameTime3_default;
		People.FrameTime4 = People.FrameTime4_default;
		People.FrameTime5 = People.FrameTime5_default;
		
	}

	public static String getGameId() {
		return getInstance().gameId;
	}

	public static void updateGameId(String gameId) {
		getInstance().gameId = gameId;
	}
	public static void clearGameId(){
		getInstance().gameId = null;
	}

	public String getBannerName() {
		if(selectedTeam != null){
			this.bannerName = selectedTeam.getResFlagName();
		}
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public String getPlayerName() {
		if(selectedTeam != null){
			this.playerName = selectedTeam.getPlayerRes();
		}
		
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getHeadName() {
		if(getMaxScore() >=Constans.score_wangfeng && !headName.equals(Constans.wangfeng)){
			UserConfig.getInstance().setHeadName(Constans.wangfeng);
		}
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public SceneName getCurScene() {
		return curScene;
	}
	
	public void setCurScene(SceneName curScene) {
		this.curScene = curScene;
	}
	
//	/**
//	 * @return the selectedTeam
//	 */
	public Team getSelectedTeam() {
		if(selectedTeam == null){
//			this.selectedTeam = TeamRestore.getInstance().getSelectedTeam();
		}
		if(selectedTeam == null){
			this.selectedTeam = new Team(JuggleApplication.instance , 1 , "1");
		}
		return selectedTeam;
	}
//
//	/**
//	 * @param selectedTeam the selectedTeam to set
//	 */
//	public void setSelectedTeam(Team selectedTeam) {
//		this.selectedTeam = selectedTeam;
//	}
//
//	public Rank getChampion() {
//		return championRank;
//	}
//
//	public void setChampion(Rank champion) {
//		this.championRank = champion;
//	}
//
//	/**
//	 * @return the maxScore
//	 */
	public int getMaxScore() {
		return maxScore;
	}
//
//	/**
//	 * @param maxScore the maxScore to set
//	 */
//	public void setMaxScore(int maxScore) {
//		this.maxScore = maxScore;
//	}
//
//	public String getCurrentUid(){
//		return uid;
//	}
//	public void setCurrentUid(String uid){
//		this.uid = uid;
//	}
//
//	/**
//	 * @return the user
//	 */
//	public User getUser() {
//		return user;
//	}
//
//	/**
//	 * @param user the user to set
//	 */
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	/**
//	 * @return the showoffMessage
//	 */
//	public String getShowoffMessage() {
//		return showoffMessage;
//	}
//
//	/**
//	 * @return the showoffMessageV
//	 */
//	public String getShowoffMessageV() {
//		return showoffMessageV;
//	}
//
//	/**
//	 * @param showoffMessage the showoffMessage to set
//	 */
//	public void setShowoffMessage(String showoffMessage) {
//		this.showoffMessage = showoffMessage;
//	}
//
//	/**
//	 * @param showoffMessageV the showoffMessageV to set
//	 */
//	public void setShowoffMessageV(String showoffMessageV) {
//		this.showoffMessageV = showoffMessageV;
//	}
//
//    public UserConfig getConfig(){
//        return cacheOperate.get(RESTORE);
//    }
//
//    public void saveConfig(UserConfig config){
//        cacheOperate.put(RESTORE, config);
//    }
//    public void remove(){
//        cacheOperate.remove(RESTORE);
//    }
	
}
