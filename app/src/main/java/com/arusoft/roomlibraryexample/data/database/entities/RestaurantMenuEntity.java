package com.arusoft.roomlibraryexample.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.arusoft.roomlibraryexample.data.database.util.DataBaseConstants;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = DataBaseConstants.MenuTable.TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = RestaurantEntity.class,
                parentColumns = DataBaseConstants.RestaurantTable.ID_COLUMN_NAME,
                childColumns = DataBaseConstants.MenuTable.RESTAURANT_ID_COLUMN_NAME,
                onDelete = CASCADE),
        indices = {@Index(DataBaseConstants.MenuTable.RESTAURANT_ID_COLUMN_NAME)})
public class RestaurantMenuEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DataBaseConstants.MenuTable.ID_COLUMN_NAME)
    public long id;

    @ColumnInfo(name = DataBaseConstants.MenuTable.RESTAURANT_ID_COLUMN_NAME)
    public long restaurantId;

    @ColumnInfo(name = DataBaseConstants.MenuTable.TITLE_COLUMN_NAME)
    public String title;

    @ColumnInfo(name = DataBaseConstants.MenuTable.DESCRIPTION_COLUMN_NAME)
    public String description;

    @ColumnInfo(name = DataBaseConstants.MenuTable.PRICE_COLUMN_NAME)
    public float price;

}
