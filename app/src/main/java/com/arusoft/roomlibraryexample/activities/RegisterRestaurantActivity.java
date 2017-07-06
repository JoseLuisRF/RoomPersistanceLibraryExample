package com.arusoft.roomlibraryexample.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.arusoft.roomlibraryexample.R;
import com.arusoft.roomlibraryexample.activities.base.BaseActivity;
import com.arusoft.roomlibraryexample.data.RepositoryManager;
import com.arusoft.roomlibraryexample.databinding.ActivityRegisterRestaurantBinding;
import com.arusoft.roomlibraryexample.presentation.MenuItemModel;
import com.arusoft.roomlibraryexample.presentation.RestaurantModel;
import com.arusoft.roomlibraryexample.presentation.adapters.RestaurantMenuAdapter;
import com.arusoft.roomlibraryexample.util.AppConstants;
import com.arusoft.roomlibraryexample.util.DeviceUtils;
import com.arusoft.roomlibraryexample.util.PermissionsManager;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.IOException;
import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class RegisterRestaurantActivity extends BaseActivity {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 201;
    private static final int REGISTER_MENU_ITEM_REQUEST_CODE = 202;

    @Inject
    PermissionsManager permissionsManager;

    @Inject
    RepositoryManager repositoryManager;

    @Inject
    DeviceUtils deviceUtils;

    private ActivityRegisterRestaurantBinding mBinding;

    private RestaurantMenuAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_restaurant);
        getActivityComponent().inject(this);
        mBinding.setModel(new RestaurantModel());
        adapter = new RestaurantMenuAdapter();
        mBinding.registerRestaurantMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.registerRestaurantMenuRecyclerView.setAdapter(adapter);
        setupListeners();
    }

    private void setupListeners() {

        mBinding.addMenuItemLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterRestaurantActivity.this, RegisterMenuActivity.class);
                RegisterRestaurantActivity.this.startActivityForResult(intent, REGISTER_MENU_ITEM_REQUEST_CODE);
            }
        });

        mBinding.openTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar openTimeCalendar = Calendar.getInstance();
                openTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                openTimeCalendar.set(Calendar.MINUTE, minute);
                mBinding.getModel().setOpenTime(openTimeCalendar.getTime());
            }
        });

        mBinding.closeTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar closeTimeCalendar = Calendar.getInstance();
                closeTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                closeTimeCalendar.set(Calendar.MINUTE, minute);
                mBinding.getModel().setCloseTime(closeTimeCalendar.getTime());
            }
        });

        mBinding.registerSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!permissionsManager.isStoragePermissionGranted()) {
                    permissionsManager.showMediaPermissionsDialog(RegisterRestaurantActivity.this, PermissionsManager.MEDIA_PERMISSION_REQUEST_CODE);
                    return;
                }

                if (mBinding.getModel() != null) {
                    showLoader();

                    Log.d("JLRF", "mBinding.getModel()" + mBinding.getModel().toString());

                    repositoryManager.registerRestaurant(mBinding.getModel())
                            .subscribe(new DisposableSubscriber<Long>() {
                                @Override
                                public void onNext(Long value) {

                                }

                                @Override
                                public void onError(Throwable t) {
                                    t.printStackTrace();
                                    dismissLoader();
                                }

                                @Override
                                public void onComplete() {

                                    dismissLoader();
                                    finish();
                                    try {
                                        deviceUtils.exportDataBases();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });

        mBinding.addressLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutoComplete();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                mBinding.restaurantAddressLabel.setText(place.getAddress());
                mBinding.getModel().setLatitude(place.getLatLng().latitude);
                mBinding.getModel().setLongitude(place.getLatLng().longitude);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Toast.makeText(this, PlaceAutocomplete.getStatus(this, data).getStatusMessage(), Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == REGISTER_MENU_ITEM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data.getSerializableExtra(AppConstants.EXTRAS_MENU_ITEM) != null) {
                    MenuItemModel model = (MenuItemModel) data.getSerializableExtra(AppConstants.EXTRAS_MENU_ITEM);
                    mBinding.getModel().getMenu().add(model);
                    adapter.add(model);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsManager.MEDIA_PERMISSION_REQUEST_CODE) {

        }
    }

    private void openAutoComplete() {
        try {

            Intent intent = new PlaceAutocomplete
                    .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

}
