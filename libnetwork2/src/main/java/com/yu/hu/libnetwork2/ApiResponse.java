package com.yu.hu.libnetwork2;

/**
 * @author Hy
 * created on 2020/02/10 13:22
 **/
@SuppressWarnings("unused")
public class ApiResponse<T> {
    public boolean success;
    public int status;
    public String message;
    public T body;
}
