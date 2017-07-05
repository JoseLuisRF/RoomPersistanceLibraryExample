package com.arusoft.roomlibraryexample.data;

import com.arusoft.roomlibraryexample.presentation.RestaurantModel;

import io.reactivex.Flowable;

public interface RepositoryManager {

    Flowable<Void> registerRestaurant(RestaurantModel model);
}
