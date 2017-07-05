package com.arusoft.roomlibraryexample;

import android.app.Application;
import android.content.Context;

import com.arusoft.roomlibraryexample.di.components.ApplicationComponent;
import com.arusoft.roomlibraryexample.di.components.DaggerApplicationComponent;
import com.arusoft.roomlibraryexample.di.modules.ApplicationModule;

public class RoomExampleApplication extends Application {

    private static RoomExampleApplication roomExampleApplication;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        roomExampleApplication = this;
        initComponent();

    }

    /***********************************************************************************************
     * Private Methods
     ***********************************************************************************************/
    private void initComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    /***********************************************************************************************
     * Public Methods
     ***********************************************************************************************/

    public static ApplicationComponent getComponent(Context context) {
        return ((RoomExampleApplication) context.getApplicationContext()).applicationComponent;
    }

    public static Context getInstance() {
        return roomExampleApplication;
    }
}
