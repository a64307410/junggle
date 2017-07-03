package com.okwyx.plugin.process;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.okwyx.plugin.Plugin;

import java.util.concurrent.atomic.AtomicBoolean;

public class NewProcessService extends IntentService{
    private static final AtomicBoolean processInit = new AtomicBoolean();
    private static final String TAG = "plugin";
    
    public NewProcessService() {
        this("process");
    }
    
    public NewProcessService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        Log.d(TAG,"onHandleIntent, action:" + action);
        if(!processInit.get()){
            Plugin.install(NewProcessService.this.getApplication());
            processInit.set(true);
        }
        if(action.equals("newprocess_activity")){
            Intent callIntent = intent.getParcelableExtra("process");
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }else if(action.equals("newprocess_service")){
            Intent callIntent = intent.getParcelableExtra("process");
            startService(callIntent);
        }
    }
    
}
