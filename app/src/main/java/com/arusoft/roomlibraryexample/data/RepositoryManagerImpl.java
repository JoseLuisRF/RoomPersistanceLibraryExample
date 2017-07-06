package com.arusoft.roomlibraryexample.data;

import com.arusoft.roomlibraryexample.data.database.DataBaseManager;
import com.arusoft.roomlibraryexample.data.database.dao.RestaurantDao;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;
import com.arusoft.roomlibraryexample.data.mapper.DataMapper;
import com.arusoft.roomlibraryexample.presentation.MenuItemModel;
import com.arusoft.roomlibraryexample.presentation.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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
    public Flowable<Long> registerRestaurant(RestaurantModel model) {
        return dataBaseManager
                .insertRestaurant(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<RestaurantModel>> getRestaurants() {
        return dataBaseManager.getRestaurants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<RestaurantEntity>, List<RestaurantModel>>() {
                    @Override
                    public List<RestaurantModel> apply(@NonNull List<RestaurantEntity> restaurantEntities) throws Exception {
                        List<RestaurantModel> models = new ArrayList<>(restaurantEntities.size());

                        for (RestaurantEntity entity : restaurantEntities) {
                            models.add(mapper.transform(entity));
                        }

                        return models;
                    }
                });
    }

    @Override
    public Flowable<List<MenuItemModel>> getRestaurantMenu(long restaurantId) {
        return dataBaseManager.getRestaurantMenu(restaurantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<RestaurantDao.RestaurantMenuEntityEx>, List<MenuItemModel>>() {
                    @Override
                    public List<MenuItemModel> apply(@NonNull List<RestaurantDao.RestaurantMenuEntityEx> entities) throws Exception {
                        List<MenuItemModel> models = new ArrayList<MenuItemModel>();
                        for(RestaurantDao.RestaurantMenuEntityEx entity :  entities){
                            models.add(mapper.transform(entity));
                        }
                        return models;
                    }
                });
    }
}
