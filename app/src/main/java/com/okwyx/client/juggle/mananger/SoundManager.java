package com.okwyx.client.juggle.mananger;

import com.okwyx.client.juggle.base.ILifecycle;
import com.okwyx.client.juggle.utils.PreferencesUtils;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.util.debug.Debug;

import java.io.IOException;


public class SoundManager extends Object implements ILifecycle {
	private static final SoundManager INSTANCE = new SoundManager();
	private static final String key = "sound";
	private static final String key2 = "volume";

	private static Music bgMusic;

	private static Sound juggle;
	
	private static Sound juggleLow;
	
	private static Sound jugglePefect;
	private static Sound cheer1;
	private static Sound cheer2;
	private static Sound gameover;
	
	private static Sound click;
	
	private static final float musicVolume = 0.5f;
	
	private boolean init;

	private boolean soundOpen;
	
	private float volume = 1.0f;

	public static SoundManager getInstance() {
		return INSTANCE;
	}

	private SoundManager() {
		MusicFactory.setAssetBasePath("sounds/");
		SoundFactory.setAssetBasePath("sounds/");
	}

	private static void setVolumeForAllSounds(final float pVolume) {
		juggle.setVolume(pVolume);
		juggleLow.setVolume(pVolume);
		jugglePefect.setVolume(pVolume);
		cheer1.setVolume(pVolume);
		cheer2.setVolume(pVolume);
		gameover.setVolume(pVolume);
		click.setVolume(pVolume);
	}

	public static boolean isSoundOpen() {
		return getInstance().soundOpen;
	}

	public static boolean toggleSoundMuted() {
		getInstance().soundOpen = !getInstance().soundOpen;
		setVolumeForAllSounds((getInstance().soundOpen ? 0f : 1f));
		if(getInstance().soundOpen){
			resumeMusic();
		}else{
			pauseMusic();
		}
		PreferencesUtils.savePrefBoolean(ResourceManager.getContext(), key, getInstance().soundOpen);
		return getInstance().soundOpen;
	}
	
	public static void playClick(final float pRate){
		playSound(click, pRate, getInstance().volume);
	}
	
	public static void playJuggle(final float pRate) {
		playSound(juggle, pRate, getInstance().volume);
	}
	public static void playJuggleLow(final float pRate) {
		playSound(juggleLow, pRate, getInstance().volume);
	}


	public static void playJugglePefect(final float pRate) {
		playSound(jugglePefect, pRate, getInstance().volume);
	}
	
	public static void playSmallCheer(final float pRate) {
		playSound(cheer1, pRate, getInstance().volume);
	}

	public static void playBigCheer(final float pRate) {
		playSound(cheer2, pRate, getInstance().volume);
	}
	
	public static void playGameover(final float pRate) {
		playSound(gameover, pRate, getInstance().volume);
	}

	private static void playSound(final Sound pSound, final float pRate, final float pVolume) {
		if (!SoundManager.isSoundOpen() || pSound.isReleased())
			return;
		pSound.setRate(pRate);
		pSound.setVolume(pVolume);
		pSound.play();
	}

	public static void playMusic() {
		bgMusic.play();
		if (SoundManager.isSoundOpen()){
		}else{
			pauseMusic();
		}
	}

	public static void pauseMusic() {
		bgMusic.pause();
	}

	public static void resumeMusic() {
		if (SoundManager.isSoundOpen()){
			bgMusic.resume();
		}
	}

	public static float getMusicVolume() {
		return bgMusic.getVolume();
	}

	public static void setMusicVolume(final float pVolume) {
		bgMusic.setVolume(pVolume);
	}

	@Override
	public void init() {
		if (getInstance().init) {
			return;
		}
		try {
			bgMusic = MusicFactory.createMusicFromAsset(ResourceManager.getActivity().getMusicManager(), ResourceManager.getActivity(), "Bg.ogg");
			bgMusic.setLooping(true);
			setMusicVolume(musicVolume);
		} catch (final IOException e) {
			Debug.e(e);
		}

		try {
			juggle = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "Juggle.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		try {
			juggleLow = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "Juggle_low.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		try {
			jugglePefect = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "Juggle_perfect.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		try {
			cheer1 = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "Cheer1.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		try {
			cheer2 = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "Cheer2.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		try {
			gameover = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "Gameover.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		
		try {
			click = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "Juggle_click.ogg");
		} catch (final IOException e) {
			Debug.e(e);
		}
		soundOpen = PreferencesUtils.loadPrefBoolean(ResourceManager.getContext(), key, true);
		if (!soundOpen) {
			setVolumeForAllSounds(0);
		}
		volume = PreferencesUtils.loadPrefFloat(ResourceManager.getContext(), key2, 1.0f);
		
		getInstance().init = true;

	}
	
	public static void add(){
		if(getInstance().volume <=1.0f){
			getInstance().volume+=0.2f;
			if(getInstance().volume >=1.0f){
				getInstance().volume = 1.0f;
			}
			setMusicVolume(musicVolume * getInstance().volume);
			PreferencesUtils.savePrefFloat(ResourceManager.getContext(), key2, getInstance().volume);
		}
	}
	
	public static void remove(){
		if(getInstance().volume >=0f){
			getInstance().volume-=0.2f;
			if(getInstance().volume <=0f){
				getInstance().volume = 0f;
			}
			setMusicVolume(musicVolume * getInstance().volume);
			PreferencesUtils.savePrefFloat(ResourceManager.getContext(), key2, getInstance().volume);
		}
	}
	
	@Override
	public void release() {
//		if (juggle != null && !juggle.isReleased())
//			juggle.release();
//		if(juggleLow != null && !juggleLow.isReleased())
//			juggleLow.release();
//		if (cheer1 != null && !cheer1.isReleased())
//			cheer1.release();
//		if (cheer2 != null && !cheer2.isReleased())
//			cheer2.release();
//		if (bgMusic != null && !bgMusic.isReleased())
//			bgMusic.release();
//		getInstance().init = false;
	}

}