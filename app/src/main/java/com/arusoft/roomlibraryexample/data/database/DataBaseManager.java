package com.arusoft.roomlibraryexample.data.database;

import com.arusoft.roomlibraryexample.data.database.dao.RestaurantDao;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;
import com.arusoft.roomlibraryexample.presentation.RestaurantModel;

import java.util.List;

import io.reactivex.Flowable;

public interface DataBaseManager {

    Flowable<Long> insertRestaurant(RestaurantModel model);

    Flowable<List<RestaurantEntity>> getRestaurants();

    Flowable<List<RestaurantDao.RestaurantMenuEntityEx>> getRestaurantMenu(long restaurantId);
}
