package com.arusoft.roomlibraryexample.data.database;

import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class DataBaseManagerImpl implements DataBaseManager {

    private ApplicationDatabase database;

    @Inject
    public DataBaseManagerImpl(ApplicationDatabase database) {
        this.database = database;
    }

    @Override
    public Flowable<Void> insertRestaurant(final RestaurantEntity entity) {
        return Flowable.create(new FlowableOnSubscribe<Void>() {
            @Override
            public void subscribe(FlowableEmitter<Void> e) throws Exception {
                database.restaurantDao.insertRestaurant(entity);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }
}
