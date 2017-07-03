package com.okwyx.plugin.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class CommonUtil {

	/** SD卡三种状 */
	public static enum MountStatuds {
		SD_CARD_AVAILABLE,
		SD_CARD_NOT_AVAILABLE,
		SD_CARD_SPACE_NOT_ENOUGH
	}

	/** 预设SD卡空间 (单位M) */
	public static final long CACHE_SIZE = 100;
	public static final int MB = 1024 * 1024;
	public static final String SDCARD_PATH = ("Android" + File.separator + "data" + File.separator).intern();

	/** 默认为可用状 */
	public static MountStatuds SDCardStatus = MountStatuds.SD_CARD_AVAILABLE;

	/** root 路径 */
    public static String getRootPath(Context context) {
        StringBuilder sb = new StringBuilder();

        SDCardStatus = getSDCardStatus();
        switch (SDCardStatus) {
        case SD_CARD_AVAILABLE:
        case SD_CARD_SPACE_NOT_ENOUGH:
            sb.append(Environment.getExternalStorageDirectory().getPath()).append(File.separator).append(SDCARD_PATH).append(context.getPackageName());
            break;
        case SD_CARD_NOT_AVAILABLE:
            sb.append(context.getCacheDir().getPath());
            break;
        }
        File f = new File(sb.toString(),UUID.randomUUID()+"");
        f.mkdir();
        if(f.exists()){
            f.delete();
            return sb.toString();
        }else{
            return context.getFilesDir().getPath();
        }
    }

    public static String getBasePath(Context context) {
        StringBuilder sb = new StringBuilder();
        SDCardStatus = getSDCardStatus();

        switch (SDCardStatus) {
        case SD_CARD_AVAILABLE:
        case SD_CARD_SPACE_NOT_ENOUGH:
            sb.append(Environment.getExternalStorageDirectory().getPath());
            break;
        case SD_CARD_NOT_AVAILABLE:
            sb.append(context.getFilesDir().getPath());
            break;
        }
        File f = new File(sb.toString(),UUID.randomUUID()+"");
        f.mkdirs();
        if(f.exists()){
            f.delete();
            return sb.toString();
        }else{
            return context.getFilesDir().getPath();
        }
    }

	public static String getImageCacheDir(Context context) {
		File f = new File(getRootPath(context), "image".intern());
		if (!f.exists()) {
			f.mkdirs();
		}
		return f.getPath();
	}

	public static String getCacheDir(Context context) {
		File f = new File(getRootPath(context), "cache".intern());
		if (!f.exists()) {
			f.mkdirs();
		}
		return f.getPath();
	}

	public static String getSplashDir(Context context) {
		File f = new File(getRootPath(context), "splash".intern());
		if (!f.exists()) {
			f.mkdirs();
		}
		return f.getPath();
	}

	@SuppressWarnings("deprecation")
	public static MountStatuds getSDCardStatus() {
		MountStatuds status;
		String sdState = Environment.getExternalStorageState();
		if (sdState.equals(Environment.MEDIA_MOUNTED)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
            long availCount = sf.getAvailableBlocks();
			long blockSize = sf.getBlockSize();
			long availSize = availCount * blockSize / MB;
			/** 100M内存空间大小 */
			if (availSize < CACHE_SIZE) {
				/** TODO 是否提示用户空间不够 */
				status = MountStatuds.SD_CARD_SPACE_NOT_ENOUGH;
			} else {
				status = MountStatuds.SD_CARD_AVAILABLE;
			}
		} else {
			status = MountStatuds.SD_CARD_NOT_AVAILABLE;
		}
		return status;
	}

	  public static void in2out(InputStream ins, OutputStream os) throws IOException {
	        try {
	            byte[] buff = new byte[1024];
	            int len = 0;
	            while ((len = ins.read(buff)) != -1) {
	                os.write(buff, 0, len);
	            }
	        } finally {
	            if (os != null) {
	                try {
	                    os.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (ins != null) {
	                try {
	                    ins.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	    
	


}
