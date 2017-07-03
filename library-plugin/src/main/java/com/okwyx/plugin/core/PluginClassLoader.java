package com.okwyx.plugin.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PluginClassLoader extends ClassLoader {
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Map<String, ClassLoader> gMapLoader = new HashMap<String, ClassLoader>();
    public static final String dexFileName = "supportV7.jar";
    
    private ClassLoader parentClasLoader;
    
    public PluginClassLoader(ClassLoader parent) {
        super(parent);
        this.parentClasLoader = parent;
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        readWriteLock.readLock().lock();
        try {
            for (ClassLoader loader : gMapLoader.values()) {
                if (loader != null) {
                    Class<?> c = null;
                    try {
                        c = loader.loadClass(className);
                    } catch (ClassNotFoundException e) {
                    }
                    if (c != null) {
                        return c;
                    }
                }
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        Class<?> c = null;
        try {
        	 c = parentClasLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
        }
        if(c != null){
        	return c;
        }

        return super.loadClass(className);
    }

    public static void setClassLoader(ClassLoader classLoader, String modelName) {
        readWriteLock.writeLock().lock();
        try {
            gMapLoader.remove(modelName);
            gMapLoader.put(modelName, classLoader);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void removeClassloader(String modelName) {
        readWriteLock.writeLock().lock();
        try {
            gMapLoader.remove(modelName);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}
