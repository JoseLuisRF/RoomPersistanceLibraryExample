package com.arusoft.roomlibraryexample.data;

import com.arusoft.roomlibraryexample.presentation.MenuItemModel;
import com.arusoft.roomlibraryexample.presentation.RestaurantModel;

import java.util.List;

import io.reactivex.Flowable;

public interface RepositoryManager {

    Flowable<Long> registerRestaurant(RestaurantModel model);

    Flowable<List<RestaurantModel>> getRestaurants();

    Flowable<List<MenuItemModel>>  getRestaurantMenu(long restaurantId);
}
