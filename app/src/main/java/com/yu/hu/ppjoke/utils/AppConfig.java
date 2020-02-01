package com.yu.hu.ppjoke.utils;


import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.reflect.TypeToken;
import com.yu.hu.common.utils.ResourceUtil;
import com.yu.hu.ppjoke.model.Destination;

import java.util.HashMap;

public class AppConfig {

    private static HashMap<String, Destination> sDestConfig;

    public static HashMap<String, Destination> getDestConfig() {
        if (sDestConfig == null) {
            String content = ResourceUtil.readFromAssets("destnation.json");
            sDestConfig = GsonUtils.fromJson(content, new TypeToken<HashMap<String, Destination>>() {
            }.getType());
        }
        return sDestConfig;
    }

}
