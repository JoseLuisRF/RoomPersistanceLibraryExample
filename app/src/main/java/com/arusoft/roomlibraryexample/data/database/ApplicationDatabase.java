package com.arusoft.roomlibraryexample.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.arusoft.roomlibraryexample.data.database.dao.RestaurantDao;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantMenuEntity;
import com.arusoft.roomlibraryexample.data.database.util.DataBaseConstants;

@Database(entities = {RestaurantEntity.class, RestaurantMenuEntity.class}, version = DataBaseConstants.DATA_BASE_VERSION)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static ApplicationDatabase INSTANCE;

    public abstract RestaurantDao restaurantDao();

    /**
     * Creates a Data Base
     *
     * @param context Application Context
     * @return An instance of the database
     */
    public static ApplicationDatabase createDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context, ApplicationDatabase.class, DataBaseConstants.DATA_BASE_NAME)
                    .build();
        }
        return INSTANCE;
    }
}
