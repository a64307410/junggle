package com.okwyx.client.framework.libs.network;

import android.content.Context;

import com.weibo.game.NetLibrary;

/**
 * 网络模块准备重构,暂时使用14年写的
 * 作者：Swei on 2017/4/12 16:53<BR/>
 * 邮箱：sweilo@qq.com
 */

public class NetworkCore {

    private NetworkCore(){}

    public static void init(Context context){
        NetLibrary.init(context);
    }




}
