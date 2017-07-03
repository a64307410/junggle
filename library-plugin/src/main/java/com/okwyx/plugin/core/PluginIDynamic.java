package com.okwyx.plugin.core;

import android.app.Application;
import android.content.Context;

import java.io.File;

public interface PluginIDynamic {
    
    public void init(Application application) throws Throwable;
    
    public File getPluginFile(Context context);
    
    public ClassLoader installPlugin(Context context, File pluginFile);
    
    public void unInstallPlugin(Context activity);
    
    
}
