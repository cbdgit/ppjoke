package com.yu.hu.ppjoke.utils;


import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.yu.hu.common.utils.ResourceUtil;
import com.yu.hu.ppjoke.model.BottomBar;
import com.yu.hu.ppjoke.model.Destination;
import com.yu.hu.ppjoke.model.SofaTab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class AppConfig {

    private static HashMap<String, Destination> sDestConfig;

    private static BottomBar sBottomBar;

    private static SofaTab sSofaTab;

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

    public static SofaTab getSofaTabConfig() {
        if (sSofaTab == null) {
            String content = ResourceUtil.readFromAssets("sofa_tabs_config.json");
            sSofaTab = JSON.parseObject(content, SofaTab.class);
            Collections.sort(sSofaTab.tabs, (o1, o2) -> o1.index < o2.index ? -1 : 1);
        }
        return sSofaTab;
    }

    private static String parseFile(String fileName) {
        AssetManager assets = AppGlobals.getApplication().getAssets();
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        try {
            is = assets.open(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {

            }
        }

        return builder.toString();
    }
}
