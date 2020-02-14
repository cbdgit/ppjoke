package com.yu.hu.libnetwork2;

import java.util.Map;

import okhttp3.FormBody;

/**
 * @author Hy
 * created on 2020/02/11 11:26
 **/
public class PostRequest<T> extends Request<T, PostRequest> {
    public PostRequest(String mUrl) {
        super(mUrl);
    }

    @Override
    protected okhttp3.Request generateRequest(okhttp3.Request.Builder builder) {
        //post请求表单提交
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            bodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return builder.url(mUrl).post(bodyBuilder.build()).build();
    }
}
