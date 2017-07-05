package com.arusoft.roomlibraryexample.di.modules;

import android.content.Context;

import com.arusoft.roomlibraryexample.RoomExampleApplication;
import com.arusoft.roomlibraryexample.data.database.ApplicationDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ApplicationModule {

    private RoomExampleApplication mApplication;

    public ApplicationModule(RoomExampleApplication application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    public Context providesApplicationContext() {
        return this.mApplication.getApplicationContext();
    }

    @Singleton
    @Provides
    public ApplicationDatabase providesApplicationDataBase(Context context) {
        return ApplicationDatabase.createDatabase(context);
    }

}
