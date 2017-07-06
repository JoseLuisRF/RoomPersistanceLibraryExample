package com.arusoft.roomlibraryexample.di.modules;

import com.arusoft.roomlibraryexample.data.RepositoryManager;
import com.arusoft.roomlibraryexample.data.RepositoryManagerImpl;
import com.arusoft.roomlibraryexample.data.database.DataBaseManager;
import com.arusoft.roomlibraryexample.data.mapper.DataMapper;
import com.arusoft.roomlibraryexample.di.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @ActivityScope
    @Provides
    public DataMapper providesDataMapper() {
        return new DataMapper();
    }

    @ActivityScope
    @Provides
    public RepositoryManager providesRepositoryManager(DataBaseManager dataBaseManager, DataMapper dataMapper) {
        return new RepositoryManagerImpl(dataBaseManager, dataMapper);
    }
}
