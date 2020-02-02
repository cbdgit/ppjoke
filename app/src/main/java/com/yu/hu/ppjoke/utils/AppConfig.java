package com.yu.hu.ppjoke.utils;


import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.yu.hu.common.utils.ResourceUtil;
import com.yu.hu.ppjoke.model.BottomBar;
import com.yu.hu.ppjoke.model.Destination;

import java.util.HashMap;

public class AppConfig {

    private static HashMap<String, Destination> sDestConfig;

    private static BottomBar sBottomBar;

    /**
     * 获取destnation配置项
     */
    public static HashMap<String, Destination> getDestConfig() {
        if (sDestConfig == null) {
            String content = ResourceUtil.readFromAssets("destnation.json");
            sDestConfig = GsonUtils.fromJson(content, new TypeToken<HashMap<String, Destination>>() {
            }.getType());
        }
        return sDestConfig;
    }

    /**
     * 获取底部导航配置项
     */
    public static BottomBar getBottomBar(){
        if (sBottomBar == null) {
            String content = ResourceUtil.readFromAssets("main_tabs_config.json");
            sBottomBar = GsonUtils.fromJson(content, new TypeToken<BottomBar>() {
            }.getType());
        }
        return sBottomBar;
    }
}
