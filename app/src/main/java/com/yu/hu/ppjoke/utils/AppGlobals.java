package com.yu.hu.ppjoke.utils;

import android.app.Application;

import com.yu.hu.common.utils.AppUtils;


public class AppGlobals {

    private static volatile Application sApplication;

    public static Application getApplication(){
        if (sApplication == null) {
            synchronized (AppGlobals.class) {
                sApplication = AppUtils.getApplicationByReflect();
            }
        }
        return sApplication;
    }

}
