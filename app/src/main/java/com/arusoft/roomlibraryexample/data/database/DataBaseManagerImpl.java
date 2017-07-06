package com.arusoft.roomlibraryexample.data.database;

import android.util.Log;
import android.view.MenuItem;

import com.arusoft.roomlibraryexample.data.database.dao.RestaurantDao;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantEntity;
import com.arusoft.roomlibraryexample.data.database.entities.RestaurantMenuEntity;
import com.arusoft.roomlibraryexample.data.mapper.DataMapper;
import com.arusoft.roomlibraryexample.presentation.MenuItemModel;
import com.arusoft.roomlibraryexample.presentation.RestaurantModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class DataBaseManagerImpl implements DataBaseManager {

    private static final String TAG = DataBaseManagerImpl.class.getSimpleName();

    private ApplicationDatabase database;
    private DataMapper mapper;

    @Inject
    public DataBaseManagerImpl(ApplicationDatabase database, DataMapper mapper) {
        this.database = database;
        this.mapper = mapper;
    }

    @Override
    public Flowable<Long> insertRestaurant(final RestaurantModel model) {
        return Flowable.create(new FlowableOnSubscribe<Long>() {
            @Override
            public void subscribe(FlowableEmitter<Long> e) throws Exception {

                long id = database.restaurantDao().insertRestaurant(mapper.transform(model));
                Log.d(TAG, "id" + id);

                for (MenuItemModel menuItemModel : model.getMenu()) {
                    RestaurantMenuEntity menuEntity = mapper.transform(menuItemModel);
                    menuEntity.restaurantId = id;
                    database.restaurantDao().insertMenuItem(menuEntity);
                }

                e.onNext(id);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<RestaurantEntity>> getRestaurants() {
        return Flowable.create(new FlowableOnSubscribe<List<RestaurantEntity>>() {
            @Override
            public void subscribe(FlowableEmitter<List<RestaurantEntity>> e) throws Exception {
                e.onNext(database.restaurantDao().getRestaurants());
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<RestaurantDao.RestaurantMenuEntityEx>> getRestaurantMenu(final long restaurantId) {

        return Flowable.create(new FlowableOnSubscribe<List<RestaurantDao.RestaurantMenuEntityEx>>() {
            @Override
            public void subscribe(FlowableEmitter<List<RestaurantDao.RestaurantMenuEntityEx>> e) throws Exception {
                e.onNext(database.restaurantDao().getRestaurantMenu(restaurantId));
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }
}
