package com.okwyx.client.framework.libs.http;

import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Swei on 2017/4/12 17:06<BR/>
 * 邮箱：sweilo@qq.com
 */

public class HttpConstants {
    public static final String sngversion = "710";

    public static final String versionName = "7.10";

    private static Map<String,String> httpHeader = new HashMap<String, String>();

    static {
        httpHeader.put("version", "1");
    }

    public static Map<String,String> getHeader(){
        long timestamp = System.currentTimeMillis();
        httpHeader.put("timestamp",timestamp+"");
        try {
            byte[] temp = (timestamp+"_"+"asdfgqwe").getBytes();
            String data = Base64.encodeToString(temp, Base64.NO_WRAP);
            String signature =data.hashCode()+"";
            httpHeader.put("signature", signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpHeader;
    }




}
