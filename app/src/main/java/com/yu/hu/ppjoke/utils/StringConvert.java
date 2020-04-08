package com.yu.hu.ppjoke.utils;

/**
 * @author Hy
 * created on 2020/02/16 12:17
 **/
public class StringConvert {

    public static String StringFeedUgc(int count) {
        if (count < 10_000) {
            return String.valueOf(count);
        }

        return count / 10_000 + "万";
    }

    public static String convertFeedUgc(int count) {
        return StringFeedUgc(count);
    }
}
