package com.yu.hu.libnetwork2.cache;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * @author Hy
 * created on 2020/02/13 12:11
 * <p>
 * 用于Room中Date与long的相互转换
 * <p>
 * 使用时一般标记在字段上 ：
 * <code> @TypeConverters(value = {DateConverter.class}) </code>
 * <p>
 * 详见{@link androidx.room.TypeConverters}  database，dao ，entity上都可以用此标记
 **/
@SuppressWarnings("unused")
public class DateConverter {

    @TypeConverter
    public static Long date2Long(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static Date long2Date(long date) {
        return new Date(date);
    }
}
