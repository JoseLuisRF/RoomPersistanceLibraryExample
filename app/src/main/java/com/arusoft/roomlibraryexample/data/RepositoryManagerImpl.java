package com.arusoft.roomlibraryexample.data;

import com.arusoft.roomlibraryexample.data.database.DataBaseManager;
import com.arusoft.roomlibraryexample.data.mapper.DataMapper;
import com.arusoft.roomlibraryexample.presentation.RestaurantModel;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class RepositoryManagerImpl implements RepositoryManager {

    private DataBaseManager dataBaseManager;
    private DataMapper mapper;

    @Inject
    public RepositoryManagerImpl(DataBaseManager dataBaseManager, DataMapper mapper) {
        this.dataBaseManager = dataBaseManager;
        this.mapper = mapper;
    }

    @Override
    public Flowable<Void> registerRestaurant(RestaurantModel model) {
        return dataBaseManager
                .insertRestaurant(mapper.transform(model))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
