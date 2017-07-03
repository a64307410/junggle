package com.okwyx.plugin.core;

import dalvik.system.DexClassLoader;

public class PluginDexLoader extends DexClassLoader {

    public PluginDexLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        Class<?> clazz = null;
        try {
            clazz = findClass(className);
        } catch (ClassNotFoundException e) {
//             e.printStackTrace();
        }
        if (clazz != null) {
            return clazz;
        }
        return super.loadClass(className);
    }

}
