package com.okwyx.client.framework.libs.ad.factory;

import android.app.Activity;

import com.okwyx.client.framework.libs.ad.interfaces.ADListener;
import com.okwyx.client.framework.libs.ad.interfaces.IADGeneral;
import com.okwyx.client.framework.libs.ad.interfaces.admob.ADMob;
import com.okwyx.client.framework.libs.ad.interfaces.tencent.ADTencent;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Swei on 2017/6/13 01:07<BR/>
 * 邮箱：sweilo@qq.com
 */

public class ADFactory implements IADGeneral{
    private List<IADGeneral> list = new ArrayList<>();
    public ADFactory(){
        //TODO顺序
        list.add(new ADTencent());
        list.add(new ADMob());
    }


    @Override
    public void showSplash(Activity activity, ADListener listener) {

    }

    @Override
    public void showBannner(Activity activity, ADListener listener) {

    }

    @Override
    public void loadAD(Activity activity, ADListener listener) {

    }

}
