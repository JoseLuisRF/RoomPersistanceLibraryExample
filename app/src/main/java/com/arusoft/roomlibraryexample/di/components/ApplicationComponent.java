package com.arusoft.roomlibraryexample.di.components;

import android.content.Context;

import com.arusoft.roomlibraryexample.data.database.ApplicationDatabase;
import com.arusoft.roomlibraryexample.di.modules.ApplicationModule;
import com.arusoft.roomlibraryexample.util.DeviceUtils;
import com.arusoft.roomlibraryexample.util.PermissionsManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Context context();

    ApplicationDatabase applicationDatabase();

    DeviceUtils deviceUtils();

    PermissionsManager permissionsManager();
}
