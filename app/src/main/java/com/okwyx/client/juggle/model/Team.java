package com.okwyx.client.juggle.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;

public class Team implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int cid;
	private String group;//分组
	private String teamName;//名称
	private int resFlag; //国旗的资源
	private String resFlagName; //国旗的资源
	private int resClothes; //人物的资源
	private int resName; //队伍名称的资源
	private int resGroup; //分组的资源
	private int resFansBg; //球迷背景资源
	private String playerRes;
	
	private String pid;//conver 的封面图id
	
	private boolean isSelected = false;
	
	private transient Context context;
	
	
	public Team(Context context, int cid,String teamIndex){
		super();
		this.cid = cid;
		this.context = context.getApplicationContext();
		resFlag = getDrawableId(context,"sina_sng_team_flag" + teamIndex);
		resClothes = getDrawableId(context,"sina_sng_team_clothes" + teamIndex);
		resName = getDrawableId(context,"sina_sng_team_name" + teamIndex);
		resFlagName = "sina_sng_team_flag"+ teamIndex+".png";
		playerRes = "sina_sng_player_2_4_"+ teamIndex+".png";
		pid = pids[cid-1];
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
	public String getPlayerRes() {
		return playerRes;
	}

	public Bitmap getFlagBitmap(){
		return TeamData.getBitmapByName(context, "gfx/" + resFlagName);
	}
	
	public Bitmap getFlagBitmap(Context context){
		return TeamData.getBitmapByName(context, "gfx/" + resFlagName);
	}
	
	
	public int getDrawableId(Context context, String name) {
		return context.getResources().getIdentifier(name,
				"drawable", context.getApplicationContext().getPackageName());
	}
	
	public Team(){
		super();
	}
	
	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return the cid
	 */
	public int getCid() {
		return cid;
	}
	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * @return the resFlag
	 */
	public int getResFlag() {
		return resFlag;
	}

	/**
	 * @return the resClothes
	 */
	public int getResClothes() {
		return resClothes;
	}

	/**
	 * @return the resName
	 */
	public int getResName() {
		return resName;
	}

	/**
	 * @return the resFlagName
	 */
	public String getResFlagName() {
		return resFlagName;
	}

	/**
	 * @param resFlagName the resFlagName to set
	 */
	public void setResFlagName(String resFlagName) {
		this.resFlagName = resFlagName;
	}

	/**
	 * @param resName the resName to set
	 */
	public void setResName(int resName) {
		this.resName = resName;
	}

	/**
	 * @param resFlag the resFlag to set
	 */
	public void setResFlag(int resFlag) {
		this.resFlag = resFlag;
	}

	/**
	 * @param resClothes the resClothes to set
	 */
	public void setResClothes(int resClothes) {
		this.resClothes = resClothes;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(int cid) {
		this.cid = cid;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the resGroup
	 */
	public int getResGroup() {
		return resGroup;
	}

	/**
	 * @return the resFansBg
	 */
	public int getResFansBg() {
		return resFansBg;
	}

	/**
	 * @param resGroup the resGroup to set
	 */
	public void setResGroup(int resGroup) {
		this.resGroup = resGroup;
	}

	/**
	 * @param resFansBg the resFansBg to set
	 */
	public void setResFansBg(int resFansBg) {
		this.resFansBg = resFansBg;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	private static String[] pids = new String[]{
		"a1d3feabjw1ecat7otiwqj20hs0hst9y",
		"a1d3feabjw1ecat7otiwqj20hs0hst9y",
		"a1d3feabjw1ecat7otiwqj20hs0hst9y",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf",
		"a1d3feabjw1ecat6lu6j1j20hs0hsmzf"
	};

	
}
