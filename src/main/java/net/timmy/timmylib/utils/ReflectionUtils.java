/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package net.timmy.timmylib.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

    public static <T> T createInstance(Class<? extends T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean fieldExists(Class<?> clazz, Class<?> type) {
        for (Field field : clazz.getFields()) {
            if (field.getType().equals(type)) {
                return true;
            }
        }

        return false;
    }

    public static void invokeMethod(Method method, Object clazzInstance, Object... params) {
        try {
            method.invoke(clazzInstance, params);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void invokeMethod(Method method, Class<?> clazz, Object... params) {
        try {
            method.invoke(clazz, params);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getField(Field field, Class<?> type) {
        try {
            return field.get(type);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setField(Field field, Class<?> type, Object value) {
        try {
            field.set(type, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Method getMethodForAnnotation(Class<? extends Annotation> annotation, Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                return method;
            }
        }

        return null;
    }

    public static Field getFieldForAnnotation(Class<? extends Annotation> annotation, Class<?> clazz) {
        for (Field field : clazz.getFields()) {
            if (field.isAnnotationPresent(annotation)) {
                return field;
            }
        }

        return null;
    }

}
