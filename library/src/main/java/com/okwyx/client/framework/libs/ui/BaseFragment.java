package com.okwyx.client.framework.libs.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.okwyx.client.framework.libs.utils.Log;

/**
 * 作者：Swei on 2017/4/11 10:55<BR/>
 * 邮箱：sweilo@qq.com
 */

public class BaseFragment extends Fragment {
    protected boolean hasLoadData = false;

    public void loadData(){
        Log.d("fragment loadData");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!hasLoadData){
            loadData();
            hasLoadData = true;
        }
    }
}
