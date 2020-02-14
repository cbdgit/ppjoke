package com.yu.hu.libnetwork2;

/**
 * @author Hy
 * created on 2020/02/10 13:23
 **/
@SuppressWarnings("unused")
public abstract class JsonCallback<T> {
    public void onSuccess(ApiResponse<T> response) {

    }

    public void onError(ApiResponse<T> response) {

    }

    public void onCacheSuccess(ApiResponse<T> response) {

    }
}
