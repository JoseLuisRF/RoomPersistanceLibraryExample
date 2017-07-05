package com.arusoft.roomlibraryexample.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.arusoft.roomlibraryexample.R;
import com.arusoft.roomlibraryexample.activities.base.BaseActivity;
import com.arusoft.roomlibraryexample.databinding.ActivityMainBinding;
import com.arusoft.roomlibraryexample.util.AppConstants;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.registerRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegisterRestaurant();
            }
        });
    }

    private void navigateToRegisterRestaurant() {
        Intent intent = new Intent(this, RegisterRestaurantActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_REGISTER_RESTAURANT);
    }
}
