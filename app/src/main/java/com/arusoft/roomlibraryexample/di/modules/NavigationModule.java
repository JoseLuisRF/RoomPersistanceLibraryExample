package com.arusoft.roomlibraryexample.di.modules;

import android.content.Context;

import com.arusoft.roomlibraryexample.di.scopes.ActivityScope;
import com.arusoft.roomlibraryexample.presentation.Navigator;
import com.arusoft.roomlibraryexample.presentation.NavigatorImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class NavigationModule {

    @ActivityScope
    @Provides
    Navigator providesNavigator(Context context) {
        return new NavigatorImpl(context);
    }
}
