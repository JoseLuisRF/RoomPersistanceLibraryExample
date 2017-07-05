package com.arusoft.roomlibraryexample.data.database;

import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;

import io.reactivex.Flowable;

public interface DataBaseManager {

    Flowable<Void> insertRestaurant(RestaurantEntity entity);
}
