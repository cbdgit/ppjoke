package com.yu.hu.common.utils;

import android.annotation.SuppressLint;
import android.app.Application;


@SuppressWarnings("unused")
public class AppUtils {

    /**
     * 通过反射拿到Application
     *
     * @return Application
     * @throws RuntimeException when application is null or exception throws
     */
    public static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (Exception e) {
            LogUtil.warn(e);
        }
        throw new NullPointerException("u should init first");
    }
}
