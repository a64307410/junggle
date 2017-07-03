package com.okwyx.client.framework.libs.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.okwyx.client.framework.libs.Libs;
import com.okwyx.plugin.Plugin;

/**
 * 作者：Swei on 2017/4/13 15:53<BR/>
 * 邮箱：sweilo@qq.com
 */

public class BaseFragmentActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Libs.init(getApplicationContext());
        Plugin.init(this);
        Plugin.onCreate(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Plugin.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Plugin.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Plugin.onDestory(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Plugin.onResume(this);
    }

    protected void setAccount(String uid,String channel){
        Plugin.setAccount(uid,channel);
    }

    protected void chargeRequest(String orderId, String pruductName, long currencyAmount ){
        Plugin.chargeRequest(orderId,pruductName,currencyAmount);
    }

    protected void onChargeSuccess(String orderId){
        Plugin.onChargeSuccess(orderId);
    }
}
