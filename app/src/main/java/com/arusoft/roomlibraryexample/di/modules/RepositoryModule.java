package com.arusoft.roomlibraryexample.di.modules;

import com.arusoft.roomlibraryexample.data.RepositoryManager;
import com.arusoft.roomlibraryexample.data.RepositoryManagerImpl;
import com.arusoft.roomlibraryexample.data.database.DataBaseManager;
import com.arusoft.roomlibraryexample.di.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @ActivityScope
    @Provides
    public RepositoryManager providesRepositoryManager(DataBaseManager dataBaseManager) {
        return new RepositoryManagerImpl(dataBaseManager);
    }
}
