package com.arusoft.roomlibraryexample.di.components;


import com.arusoft.roomlibraryexample.activities.RegisterRestaurantActivity;
import com.arusoft.roomlibraryexample.di.modules.RepositoryModule;
import com.arusoft.roomlibraryexample.di.modules.StorageModule;
import com.arusoft.roomlibraryexample.di.scopes.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {StorageModule.class, RepositoryModule.class})
public interface ActivityComponent {

    void inject(RegisterRestaurantActivity registerRestaurantActivity);
}
