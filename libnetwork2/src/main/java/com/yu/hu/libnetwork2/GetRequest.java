package com.yu.hu.libnetwork2;

/**
 * @author Hy
 * created on 2020/02/10 13:31
 * <p>
 * 用于处理同步请求
 **/
public class GetRequest<T> extends Request<T, GetRequest> {

    public GetRequest(String mUrl) {
        super(mUrl);
    }

    @Override
    protected okhttp3.Request generateRequest(okhttp3.Request.Builder builder) {
        //get 请求把参数拼接在 url后面
        String url = UrlCreator.createUrlFromParams(mUrl, params);
        return builder.get().url(url).build();
    }
}
