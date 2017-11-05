package com.arusoft.roomlibraryexample.presentation;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.arusoft.roomlibraryexample.activities.RegisterRestaurantActivity;
import com.arusoft.roomlibraryexample.activities.RestaurantMenuOverviewActivity;
import com.arusoft.roomlibraryexample.util.AppConstants;

public class NavigatorImpl implements Navigator {

    private Context mContext;

    public NavigatorImpl(Context context) {
        this.mContext = context;
    }

    public void openLocationSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mContext.startActivity(intent);
    }

    public void navigateToRegisterRestaurant() {
        Intent intent = new Intent(mContext, RegisterRestaurantActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    @Override
    public void navigateToRestaurantMenu(long restaurantId) {
        Intent intent = new Intent(mContext, RestaurantMenuOverviewActivity.class);
        intent.putExtra(AppConstants.EXTRAS_RESTAURANT_ID, restaurantId);
        mContext.startActivity(intent);
    }
}
