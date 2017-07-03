package com.okwyx.client.framework.libs.ad;

import android.content.Context;

import com.okwyx.client.framework.libs.utils.IOUtil;

import org.json.JSONObject;

/**
 * 作者：Swei on 2017/6/10 19:20<BR/>
 * 邮箱：sweilo@qq.com
 */

public class ADData {
    private ADData() {}
    /**
     * 广点通配置
     */
    public GDTConfig gdtConfig;
    private static final String configPath = "config/ad.json";

    public static void init(Context context) {

        try {
            String data = IOUtil.toString(context.getApplicationContext().getAssets().open(configPath), "UTF-8");
            JSONObject json = new JSONObject(data);
            getInstance().gdtConfig = parseJSON(json.optJSONObject("gdt"));
            //读取网络加载的

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static ADData getInstance() {
        return Holder.instance;
    }

    private static final class Holder {
        public static ADData instance = new ADData();
    }

    private static GDTConfig parseJSON(JSONObject json) {
        GDTConfig gdtConfig = new GDTConfig();
        gdtConfig.appId = json.optString("appId");
        gdtConfig.positionId = json.optString("positionId");
        return gdtConfig;
    }


    /**
     * 广点通配置
     */
    public final static class GDTConfig {
        /**
         * app
         */
        public String appId;

        /**
         * 广告位
         */
        public String positionId;
    }

}
