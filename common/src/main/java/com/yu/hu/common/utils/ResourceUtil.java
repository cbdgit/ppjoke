package com.yu.hu.common.utils;


import android.content.res.AssetManager;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.google.gson.reflect.TypeToken;
import com.yu.hu.common.entity.RequestResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Hy on 2019/11/18 11:52
 * <p>
 * 资源相关Util
 *
 * @see ResourceUtils
 **/
@SuppressWarnings("unused")
public class ResourceUtil {

    /**
     * 从asserts从读取模拟数据
     *
     * @see ResourceUtils#readAssets2List(String)
     */
    @SuppressWarnings("WeakerAccess")
    public static String readAssets2String(final String assetsFilePath) {
        return ResourceUtils.readAssets2String(assetsFilePath);
    }

    /**
     * 从Assets中读取json文件
     *
     * @param fileName 文件名
     * @return 内容
     * @see #getFromAssets(String, Class)
     */
    public static String readFromAssets(String fileName) {
        AssetManager assetManager =
                AppUtils.getApplicationByReflect().getResources().getAssets();
        InputStream stream = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            stream = assetManager.open(fileName);
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            LogUtil.warn(e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                LogUtil.warn(e);
            }
        }
        return builder.toString();
    }


    /**
     * @param assetsFilePath assets文件名
     * @param <T>            response的类型
     * @see RequestResult#response
     */
    @SuppressWarnings("WeakerAccess")
    @Nullable
    public static <T> RequestResult<T> getFromAssets(final String assetsFilePath, Class<T> tClass) {
        try {
            String result = readAssets2String(assetsFilePath);
            return GsonUtils.fromJson(result, new TypeToken<RequestResult<T>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
