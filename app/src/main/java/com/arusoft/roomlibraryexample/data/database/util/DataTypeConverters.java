package com.arusoft.roomlibraryexample.data.database.util;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DataTypeConverters {
    @TypeConverter
    public static Long convertDateToLong(Date value) {
        return value == null ? null : new Long(value.getTime());
    }

    @TypeConverter
    public static Date convertLongToDate(Long value) {
        return value == null ? null : new Date(value);
    }
}
