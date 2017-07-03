package com.okwyx.client.juggle.utils;





import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {
	
	private static SharedPreferences pref = null;
	private static SharedPreferences.Editor editor = null;
	
	private static final String name = "football_preference";
	
	public static String loadPrefString(Context context, String key) {
		return loadPrefString(context, key, null);
	}

	public static String loadPrefString(Context context, String key,
			String defaultValue) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}
		return pref.getString(key, defaultValue);
	}

	public static void savePrefString(Context context, String key, String value) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}
		if (editor == null) {
			editor = pref.edit();
		}
		editor.putString(key, value);
		editor.commit();
	}
	
	public static int loadPrefInt(Context context, String key, int defaultValue) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}

		return pref.getInt(key, defaultValue);
	}

	public static void savePrefInt(Context context, String key, int value) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}

		if (editor == null) {
			editor = pref.edit();
		}
		editor.putInt(key, value);
		editor.commit();
	}

	public static long loadPrefLong(Context context, String key,
			long defaultValue) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}
		return pref.getLong(key, defaultValue);
	}

	public static void savePrefLong(Context context, String key, long value) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}
		if (editor == null) {
			editor = pref.edit();
		}
		editor.putLong(key, value);
		editor.commit();
	}

	public static float loadPrefFloat(Context context, String key,
			float defaultValue) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}
		return pref.getFloat(key, defaultValue);
	}

	public static void savePrefFloat(Context context, String key, float value) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}
		if (editor == null) {
			editor = pref.edit();
		}
		editor.putFloat(key, value);
		editor.commit();
	}

	public static boolean loadPrefBoolean(Context context, String key,
			boolean defaultValue) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}
		return pref.getBoolean(key, defaultValue);
	}

	public static void savePrefBoolean(Context context, String key,boolean value) {
		if (pref == null) {
			pref = context.getSharedPreferences(name,Context.MODE_PRIVATE);
		}
		if (editor == null) {
			editor = pref.edit();
		}
		editor.putBoolean(key, value);
		editor.commit();
	}

}
