package com.okwyx.plugin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.okwyx.plugin.core.PluginDynamicImpl;
import com.okwyx.plugin.core.PluginIDynamic;
import com.okwyx.plugin.utils.PropertyUtils;
import com.okwyx.plugin.utils.RefUtil;

import java.io.File;
import java.util.Map;
import java.util.Properties;

public class Plugin {

    /** 线上测试版本为2,加载线上则修改为1,加载时会看到【加载插件】 */
    public static final int VER_PLUGIN = 3;
    private static final String API_PKG_NAME = "com.okwyx.plugin.api.PluginAPI";
    private static ClassLoader pluginClassLoader = null;


    public static void install(Application application) {
        PluginIDynamic v7 = new PluginDynamicImpl();
        try {
            v7.init(application);
            File pluginFile = v7.getPluginFile(application);
            pluginClassLoader = v7.installPlugin(application, pluginFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void init(Context context) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "init", new Class[]{Context.class}, new Object[]{context});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onCreate(Activity activity) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "onCreate", new Class[]{Activity.class}, new Object[]{activity});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onPause(Activity activity) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "onPause", new Class[]{Activity.class}, new Object[]{activity});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onStop(Activity activity) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "onStop", new Class[]{Activity.class}, new Object[]{activity});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onResume(Activity activity) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "onResume", new Class[]{Activity.class}, new Object[]{activity});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onDestory(Activity activity) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "onDestory", new Class[]{Activity.class}, new Object[]{activity});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setAccount(String uid, String channel) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "setAccount", new Class[]{String.class, String.class}, new Object[]{uid, channel});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void chargeRequest(String orderId, String pruductName, long currencyAmount) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "chargeRequest", new Class[]{String.class, String.class, long.class}, new Object[]{orderId, pruductName, currencyAmount});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onChargeSuccess(String orderId) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "onChargeSuccess", new Class[]{String.class}, new Object[]{orderId});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onEvent(String event) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "onEvent", new Class[]{String.class}, new Object[]{event});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onEvent(String event, Map<String, String> map) {
        try {
            Class<?> clzz = Class.forName(API_PKG_NAME, false, pluginClassLoader);
            RefUtil.invokeStaticMethod(clzz, "onEvent", new Class[]{String.class, Map.class}, new Object[]{event, map});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getChannel(Context context) {
        String channel = "default";
        try {
            AssetManager am = context.getAssets();
            Properties p = PropertyUtils.createFromInputstream(am.open("config.properties"));
            channel = p.getProperty("Channel", channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }


}
