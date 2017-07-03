package com.okwyx.client.framework.libs;

import android.content.Context;
import android.util.Base64;

import com.okwyx.client.framework.libs.display.ImageDisplayer;
import com.weibo.game.NetLibrary;
import com.weibo.game.utils.SystemDevice;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Swei on 2017/4/13 21:10<BR/>
 * 邮箱：sweilo@qq.com
 */

public class Libs {
    private static boolean init = false;
    private Libs(){}
    private Context context;
    private String deviceID;

    private static Map<String,String> httpHeader = new HashMap<String, String>();


    public static Libs getInstance(){
        return Holder.instance;
    }

    private static final class Holder{
        public static Libs instance = new Libs();
    }

    public String getDeviceID() {
        return deviceID;
    }

    public synchronized static Map<String, String> getHttpHeader() {
        long timestamp = System.currentTimeMillis();
        try {
            byte[] temp = (timestamp+"_"+"asdfgqwe").getBytes();
            String data = Base64.encodeToString(temp, Base64.NO_WRAP);
            String signature =data.hashCode()+"";
            httpHeader.put("signature", signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpHeader.put("deviceid",getInstance().deviceID);
        httpHeader.put("timestamp",timestamp+"");
        httpHeader.put("timestamp","");
        httpHeader.put("version","1");
        return httpHeader;
    }

    public static void init(Context context){
        if(!init){
            NetLibrary.init(context);
            ImageDisplayer.init(context);
            getInstance().context = context;
            getInstance().deviceID = SystemDevice.getDeviceId(context);
            init=true;
        }
    }

    public static boolean isInit(){
        return init;
    }

    public static Context getContext(){
        return getInstance().context;
    }

}
