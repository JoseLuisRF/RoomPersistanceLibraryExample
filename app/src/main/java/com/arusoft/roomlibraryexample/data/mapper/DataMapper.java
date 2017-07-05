package com.arusoft.roomlibraryexample.data.mapper;

import com.arusoft.roomlibraryexample.data.database.entities.Location;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;
import com.arusoft.roomlibraryexample.presentation.RestaurantModel;

import javax.inject.Inject;

public class DataMapper {

    @Inject
    public DataMapper() {

    }

    public RestaurantEntity transform(RestaurantModel model) {
        RestaurantEntity entity = new RestaurantEntity();
        entity.name = model.getName();
        entity.description = model.getDescription();

        entity.location = new Location();
        entity.location.latitude = model.getLatitude();
        entity.location.latitude = model.getLongitude();
        entity.location.address = model.getAddress();


        return entity;
    }
}
