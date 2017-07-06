package com.arusoft.roomlibraryexample.di.components;


import com.arusoft.roomlibraryexample.activities.MainActivity;
import com.arusoft.roomlibraryexample.activities.RegisterRestaurantActivity;
import com.arusoft.roomlibraryexample.activities.RestaurantMenuOverviewActivity;
import com.arusoft.roomlibraryexample.di.modules.NavigationModule;
import com.arusoft.roomlibraryexample.di.modules.RepositoryModule;
import com.arusoft.roomlibraryexample.di.modules.StorageModule;
import com.arusoft.roomlibraryexample.di.scopes.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {StorageModule.class, RepositoryModule.class, NavigationModule.class})
public interface ActivityComponent {

    void inject(RegisterRestaurantActivity registerRestaurantActivity);

    void inject(MainActivity mainActivity);

    void inject(RestaurantMenuOverviewActivity restaurantMenuOverviewActivity);
}
