package com.arusoft.roomlibraryexample.data.database.dao;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantMenuEntity;
import com.arusoft.roomlibraryexample.data.database.util.DataBaseConstants;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface RestaurantDao {

    @Insert(onConflict = IGNORE)
    long insertRestaurant(RestaurantEntity entity);

    @Insert(onConflict = IGNORE)
    long insertMenuItem(RestaurantMenuEntity entity);

    @Query("SELECT * from "
            + DataBaseConstants.RestaurantTable.TABLE_NAME)
    List<RestaurantEntity> getRestaurants();

    @Query("SELECT * from "
            + DataBaseConstants.RestaurantTable.TABLE_NAME + " , "
            + DataBaseConstants.MenuTable.TABLE_NAME
            + " WHERE "
            + DataBaseConstants.RestaurantTable.TABLE_NAME + "." + DataBaseConstants.RestaurantTable.ID_COLUMN_NAME + " = "
            + DataBaseConstants.MenuTable.TABLE_NAME + "." + DataBaseConstants.MenuTable.RESTAURANT_ID_COLUMN_NAME
            + " AND " + DataBaseConstants.RestaurantTable.TABLE_NAME + "." + DataBaseConstants.RestaurantTable.ID_COLUMN_NAME
            + " = :restaurantId"
    )
    List<RestaurantMenuEntityEx> getRestaurantMenu(long restaurantId);

    class RestaurantMenuEntityEx extends RestaurantMenuEntity {

        @ColumnInfo(name = DataBaseConstants.RestaurantTable.NAME_COLUMN_NAME)
        public String restaurantName;

    }
}
