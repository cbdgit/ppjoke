package com.yu.hu.common.utils;

import android.app.Application;
import android.util.DisplayMetrics;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * @author Hy
 * created on 2020/02/17 13:18
 * <p>
 * 屏幕相关
 * @see com.blankj.utilcode.util.ScreenUtils
 **/
@SuppressWarnings({"WeakerAccess", "unused"})
public class ScreenUtil {

    /**
     * @return 获取屏幕宽度
     * @see ScreenUtils#getAppScreenHeight()
     */
    public static int getScreenWidth() {
        return getScreenWidth(AppUtils.getApplicationByReflect());
    }

    public static int getScreenWidth(Application application) {
        DisplayMetrics metrics = application.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * @return 获取屏幕高度
     * @see ScreenUtils#getAppScreenHeight()
     */
    public static int getScreenHeight() {
        return getScreenHeight(AppUtils.getApplicationByReflect());
    }

    public static int getScreenHeight(Application application) {
        DisplayMetrics metrics = application.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
}
