package com.arusoft.roomlibraryexample.data.mapper;

import com.arusoft.roomlibraryexample.data.database.dao.RestaurantDao;
import com.arusoft.roomlibraryexample.data.database.entities.Location;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantMenuEntity;
import com.arusoft.roomlibraryexample.presentation.MenuItemModel;
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
        entity.location.longitude = model.getLongitude();
        entity.location.address = model.getAddress();
        return entity;
    }

    public RestaurantModel transform(RestaurantEntity entity) {
        RestaurantModel model = new RestaurantModel();
        model.setRestaurantId(entity.id);
        model.setName(entity.name);
        model.setDescription(entity.description);
        model.setOpenTime(entity.openTime);
        model.setCloseTime(entity.closeTime);

        if (entity.location != null) {
            model.setAddress(entity.location.address);
            model.setLatitude(entity.location.latitude);
            model.setLongitude(entity.location.longitude);
        }

        return model;
    }

    public RestaurantMenuEntity transform(MenuItemModel menuItemModel) {
        RestaurantMenuEntity entity = new RestaurantMenuEntity();
        entity.title = menuItemModel.getTitle();
        entity.description = menuItemModel.getDescription();
        entity.price = menuItemModel.getPrice();
        return entity;
    }

    public MenuItemModel transform(RestaurantDao.RestaurantMenuEntityEx entity) {
        MenuItemModel model = new MenuItemModel();
        model.setDescription(entity.description);
        model.setRestaurantName(entity.restaurantName);
        model.setTitle(entity.title);
        model.setPrice(entity.price);
        return model;
    }
}
