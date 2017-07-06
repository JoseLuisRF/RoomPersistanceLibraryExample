package com.arusoft.roomlibraryexample.di.modules;

import android.content.Context;

import com.arusoft.roomlibraryexample.RoomExampleApplication;
import com.arusoft.roomlibraryexample.data.database.ApplicationDatabase;
import com.arusoft.roomlibraryexample.util.DeviceUtils;
import com.arusoft.roomlibraryexample.util.PermissionsManager;

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

    @Singleton
    @Provides
    public DeviceUtils providesDeviceUtils(Context context) {
        return new DeviceUtils(context);
    }

    @Singleton
    @Provides
    public PermissionsManager providesPermissionsManager(Context context) {
        return new PermissionsManager(context);
    }

}
