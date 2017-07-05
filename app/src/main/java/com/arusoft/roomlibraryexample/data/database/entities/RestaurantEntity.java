package com.arusoft.roomlibraryexample.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.arusoft.roomlibraryexample.data.database.util.DataBaseConstants;
import com.arusoft.roomlibraryexample.data.database.util.DataTypeConverters;

import java.util.Date;

@Entity(tableName = DataBaseConstants.RestaurantTable.TABLE_NAME)
public class RestaurantEntity {
    @PrimaryKey
    @ColumnInfo(name = DataBaseConstants.RestaurantTable.ID_COLUMN_NAME)
    public int id;

    @ColumnInfo(name = DataBaseConstants.RestaurantTable.NAME_COLUMN_NAME)
    public String name;

    @ColumnInfo(name = DataBaseConstants.RestaurantTable.DESCRIPTION_COLUMN_NAME)
    public String description;

    @TypeConverters(DataTypeConverters.class)
    @ColumnInfo(name = DataBaseConstants.RestaurantTable.OPEN_TIME_COLUMN_NAME)
    public Date openTime;

    @TypeConverters(DataTypeConverters.class)
    @ColumnInfo(name = DataBaseConstants.RestaurantTable.CLOSE_TIME_COLUMN_NAME)
    public Date closeTime;

    @Embedded(prefix = DataBaseConstants.RestaurantTable.RESTAURANT_LOCATION_PREFIX)
    public Location location;

}
