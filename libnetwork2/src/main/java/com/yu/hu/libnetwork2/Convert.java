package com.yu.hu.libnetwork2;

import java.lang.reflect.Type;

public interface Convert<T> {
    T convert(String response, Type type);
}
