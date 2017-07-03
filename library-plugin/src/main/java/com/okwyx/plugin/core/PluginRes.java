package com.okwyx.plugin.core;

import android.content.Context;

import com.okwyx.plugin.Plugin;
import com.okwyx.plugin.utils.CommonUtil;
import com.okwyx.plugin.utils.FileMD5;
import com.okwyx.plugin.utils.PropertyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class PluginRes {
    //加密文件大小
//	public static final long pngLength = 1859;
	public static final long LOCAL_FILE_LENGTH = 0;

	private static final String ROOT_DIR = ".okwyxplugin";
	private static final String DEX_DIR = ROOT_DIR+"/"+".dex";
	private static final String CONFIG_DIR=ROOT_DIR + "/"+".config";
	private static final String CONFIG_NAME="config.property";
	private static final int PLUGIN_VER = Plugin.VER_PLUGIN;
    
	private static final String PLUGIN_NAME ="plugin_statistics.jar";	
	
	private static final String PLUGIN_NAME_ASSETS ="plugin.dat";
	
    public static String getPluginDir(Context context){
        File f = new File(CommonUtil.getBasePath(context),DEX_DIR);
        if(!f.exists()){
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }
    
    public static String getPluginDir2(Context context){
        File f = new File(context.getFilesDir().getPath(),DEX_DIR);
        if(!f.exists()){
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }
    
    public static File getDefaultPlugin(Context context) {
		File data = null;
		InputStream ins = null;
		try {
			// 复制目录
			data = new File(getPluginDir(context) , PLUGIN_NAME);
			ins = openPluginFromAssets(context,PLUGIN_NAME_ASSETS);
			if (ins == null) {
				return null;
			} else {
				if (data.exists() && data.length() +LOCAL_FILE_LENGTH  != ins.available()) {
					data.delete();
				}
				if (!data.exists()) {
				    File temp = new File(getPluginDir(context), "temp");
					FileOutputStream fout = new FileOutputStream(temp);
					CommonUtil.in2out(ins, fout);
					seekFile(temp, data, LOCAL_FILE_LENGTH);
					temp.delete();
				} else {
					ins.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
    
    public static boolean hasNewPlugin(Context context){
        int sdkversion = getVersionFromConfig(context);
        File plugin = getPluginFromConfig(context);
        
        if(sdkversion > PLUGIN_VER && plugin.exists()){
            try {
                String pluginMD5 = FileMD5.getFileMD5String(plugin);
                if (pluginMD5.equals(getPluginMD5FromConfig(context))) {
                    return true;
                }else{
                    File configFile = getConfigFile(context);
                    configFile.delete();
                    plugin.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static File getPluginFromConfig(Context context){
        File configFile = getConfigFile(context);
        String path = PropertyUtils.loadPropString(configFile.getAbsolutePath(), "path", getDefaultPlugin(context).getAbsolutePath());
        return new File(path);
    }
    
    private static String getPluginMD5FromConfig(Context context){
    	File configFile = getConfigFile(context);
    	return PropertyUtils.loadPropString(configFile.getAbsolutePath(), "md5", "");
    }
    
    private static int getVersionFromConfig(Context context){
    	File configFile = getConfigFile(context);
    	int ver = PLUGIN_VER;
    	String version = PropertyUtils.loadPropString(configFile.getAbsolutePath(), "ver", ""+ver);
    	try{
    		ver = Integer.valueOf(version);
    	}catch(Throwable t){
    		t.printStackTrace();
    	}
    	return ver;
    }

    
    private static File getConfigDir(Context mContext){
        String path = CommonUtil.getBasePath(mContext);
        File dir = new File(path , CONFIG_DIR);
        return dir;
    }
    
    private static File getConfigFile(Context context){
    	return  new File(getConfigDir(context) , CONFIG_NAME);
    }
    
    
    private static InputStream openPluginFromAssets(Context context,String name){
    	try {
			return context.getAssets().open(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static void seekFile(File srcFile , File destFile, long seekLength){
        RandomAccessFile raf = null;
        FileOutputStream fout  = null;
        try {
            raf = new RandomAccessFile(srcFile, "rw");
            raf.seek(seekLength);
            fout = new FileOutputStream(destFile);
            int length = -1;
            byte[] buffer = new byte[2048];
            while((length = raf.read(buffer) ) != -1){
                fout.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(raf != null){
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fout != null){
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
}
