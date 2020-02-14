package com.yu.hu.libnetwork2;

import java.lang.reflect.Type;

/**
 * @author Hy
 * created on 2020/02/11 12:03
 **/
public interface Convert<T> {
    T Convert(String response, Type type);
}
