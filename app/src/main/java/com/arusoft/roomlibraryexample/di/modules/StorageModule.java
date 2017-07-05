package com.arusoft.roomlibraryexample.di.modules;

import com.arusoft.roomlibraryexample.data.database.ApplicationDatabase;
import com.arusoft.roomlibraryexample.data.database.DataBaseManager;
import com.arusoft.roomlibraryexample.data.database.DataBaseManagerImpl;
import com.arusoft.roomlibraryexample.di.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    @ActivityScope
    @Provides
    public DataBaseManager providesDataBaseManager(ApplicationDatabase database) {
        return new DataBaseManagerImpl(database);
    }

}
