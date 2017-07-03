package com.okwyx.plugin.core;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import com.okwyx.plugin.utils.RefUtil;

import java.io.File;
import java.lang.reflect.Field;

public class PluginDynamicImpl implements PluginIDynamic {
	public static ClassLoader ORIGINAL_CLASSLOADER = null;
	public static PluginClassLoader SUPPORTV7_CLASSLOADER = null;

	@Override
	public void init(Application application) throws Throwable {
		if (application == null) {
			return;
		}
		if (ORIGINAL_CLASSLOADER != null) {
			return;
		}
		Context mBase = (Context) RefUtil.getFieldValue(application, "mBase");
		Object mPackageInfo = RefUtil.getFieldValue(mBase, "mPackageInfo");
		ORIGINAL_CLASSLOADER = (ClassLoader) RefUtil.getFieldValue(mPackageInfo, "mClassLoader");
		SUPPORTV7_CLASSLOADER = new PluginClassLoader(ORIGINAL_CLASSLOADER);
		RefUtil.setFieldValue(mPackageInfo, "mClassLoader",SUPPORTV7_CLASSLOADER);
	}

	@Override
	public File getPluginFile(Context context) {
		if (PluginRes.hasNewPlugin(context)) {
			return PluginRes.getPluginFromConfig(context);
		}else{
			File plugin = PluginRes.getDefaultPlugin(context);
			return plugin;
		}
	}

	@Override
	public ClassLoader installPlugin(Context context,File pluginFile) {
		if (ORIGINAL_CLASSLOADER == null) {
			return null;
		}
		if (pluginFile == null || !pluginFile.exists()) {
			return ORIGINAL_CLASSLOADER;
		}

		PluginDexLoader dexLoader = new PluginDexLoader(pluginFile.getAbsolutePath(),
				PluginRes.getPluginDir2(context), getNativeLibraryPath(context),ORIGINAL_CLASSLOADER);
		PluginClassLoader.setClassLoader(dexLoader,PluginClassLoader.dexFileName);
		return dexLoader;
	}

	@Override
	public void unInstallPlugin(Context context) {

	}

	private static String getNativeLibraryPath(Context context) {
		ApplicationInfo appInfo = context.getApplicationInfo();
		String libPath = "";
		if (Build.VERSION.SDK_INT >= 9) { // 2.3
			Class<?> cls = appInfo.getClass();
			try {
				Field NativeLibraryDirField = cls.getDeclaredField("nativeLibraryDir");
				libPath = (String) NativeLibraryDirField.get(appInfo);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			File libPathFile = new File(appInfo.dataDir, "lib");
			libPath = libPathFile.getAbsolutePath();
		}
		return libPath;
	}

}
