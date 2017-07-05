package com.arusoft.roomlibraryexample.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface RestaurantDao {

    @Insert(onConflict = IGNORE)
    void insertRestaurant(RestaurantEntity entity);
}
