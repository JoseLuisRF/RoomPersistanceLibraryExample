package com.arusoft.roomlibraryexample.di.components;

import android.content.Context;

import com.arusoft.roomlibraryexample.data.database.ApplicationDatabase;
import com.arusoft.roomlibraryexample.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Context context();

    ApplicationDatabase applicationDatabase();
}
