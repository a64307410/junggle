package com.okwyx.plugin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RefUtil {
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }
        Class<?> c = obj.getClass();
        Object value = null;
        while (c != null) {
            try {
                Field field = c.getDeclaredField(fieldName);
                field.setAccessible(true);
                value = field.get(obj);
                if (value != null) {
                    return value;
                }
            } catch (SecurityException e) {
                // e.printStackTrace();
            } catch (NoSuchFieldException e) {
                // e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // e.printStackTrace();
            } catch (IllegalAccessException e) {
                // e.printStackTrace();
            } finally {
                c = c.getSuperclass();
            }
        }
        return null;
    }

    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        if (obj == null || fieldName == null) {
            return;
        }
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(obj, fieldValue);
                return;
            } catch (SecurityException e) {
                // e.printStackTrace();
            } catch (NoSuchFieldException e) {
                // e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // e.printStackTrace();
            } catch (IllegalAccessException e) {
                // e.printStackTrace();
            } finally {
                clazz = clazz.getSuperclass();
            }
        }
    }
    
    public static Object getStaticFieldValue(Class<?> clazz, String fieldName) {
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(null);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    
    
    public static Object invokeStaticMethod(Class<?> obj_class, String method_name,
            Class<?>[] pareTyple, Object[] pareVaules ) {

        try {
            Method method = obj_class.getDeclaredMethod(method_name, pareTyple);
            method.setAccessible(true);
            return method.invoke(null, pareVaules);
        } catch (SecurityException e) {
//            e.printStackTrace();
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        }
        return null;
    }
    
    /**
    public static Object invokeStaticMethod(Class ownerClass, String methodName, Object[] args) throws Exception {
        Class[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }

        Method method = ownerClass.getMethod(methodName, argsClass);

        return method.invoke(null, args);
    }
    */
    
    

    public static Method findMethod(Class<?> c, String methodName) {
        return findDeclaredMethod(c, methodName, true);
    }

    public static Method findDeclaredMethod(Class<?> c, String methodName) {
        return findDeclaredMethod(c, methodName, false);
    }

    private static Method findDeclaredMethod(Class<?> c, String methodName, boolean publicOnly) {
        if ((methodName == null) || (c == null)) {
            return null;
        }
        Method[] ms = publicOnly ? c.getMethods() : c.getDeclaredMethods();
        for (Method m : ms) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        return null;
    }

}
