package com.arusoft.roomlibraryexample.data.database.entities;

import android.arch.persistence.room.Entity;

@Entity
public class RestaurantMenuEntity {

    public int id;

    public int restaurantId;

    public String title;

    public String description;

}
