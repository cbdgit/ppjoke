package com.yu.hu.libnetwork2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

/**
 * @author Hy
 * created on 2020/02/11 12:04
 **/
public class JsonConvert implements Convert {

    //默认的Json转 Java Bean的转换器
    @Override
    public Object Convert(String response, Type type) {
        JSONObject jsonObject = JSON.parseObject(response);
        JSONObject data = jsonObject.getJSONObject("data");
        if (data != null) {
            Object data1 = data.get("data");
            return JSON.parseObject(data1.toString(), type);
        }
        return null;
    }

}
