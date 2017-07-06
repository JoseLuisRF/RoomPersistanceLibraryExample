package com.arusoft.roomlibraryexample.activities;

import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.arusoft.roomlibraryexample.R;
import com.arusoft.roomlibraryexample.activities.base.BaseLocationActivity;
import com.arusoft.roomlibraryexample.data.RepositoryManager;
import com.arusoft.roomlibraryexample.databinding.ActivityMainBinding;
import com.arusoft.roomlibraryexample.presentation.MenuItemModel;
import com.arusoft.roomlibraryexample.presentation.Navigator;
import com.arusoft.roomlibraryexample.presentation.RestaurantModel;
import com.arusoft.roomlibraryexample.util.DeviceUtils;
import com.arusoft.roomlibraryexample.util.PermissionsManager;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends BaseLocationActivity
        implements OnMapReadyCallback,
        LocationListener,
        ResultCallback<LocationSettingsResult> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding mBinding;
    private GoogleMap googleMap;

    private MarkerOptions currentLocationMarker;

    @Inject
    PermissionsManager permissionsManager;

    @Inject
    DeviceUtils deviceUtils;

    @Inject
    Navigator navigator;

    @Inject
    RepositoryManager repositoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setLocationLister(this);
        setResultResultCallback(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.registerRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigator.navigateToRegisterRestaurant();
            }
        });

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!deviceUtils.isGpsEnabled()) {
            navigator.openLocationSettings();
        }

        if (!permissionsManager.isLocationPermissionGranted()) {
            permissionsManager.showLocationPermissionsDialog(this, PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mGoogleApiClient.connect();
            checkLocationSettings();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults != null && grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        if (mCurrentLocation != null) {
            removeLocationUpdates();
        }
        getRegisteredRestaurants();
    }


    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                Log.i(TAG, "All location settings are satisfied.");
                if (permissionsManager.isLocationPermissionGranted()) {
                    startLocationUpdates();
                } else {
                    permissionsManager.showLocationPermissionsDialog(MainActivity.this, PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE);
                }

                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                navigator.openLocationSettings();
                break;
        }
    }

    private void displayMarkers(List<RestaurantModel> models) {
        if (googleMap != null) {
            googleMap.clear();


            LatLngBounds.Builder builder = LatLngBounds.builder();

            if (currentLocationMarker != null) {
                googleMap.addMarker((currentLocationMarker));
            } else if (createCurrentLocationMarker()) {
                googleMap.addMarker((currentLocationMarker));
            }

            builder.include(currentLocationMarker.getPosition());

            if (models != null && models.size() > 0) {
                for (RestaurantModel model : models) {

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(model.getLatitude(), model.getLongitude()))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title(model.getName());

                    googleMap.addMarker(markerOptions).setTag(model.getRestaurantId());
                    builder.include(markerOptions.getPosition());
                }
            }

            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 20));

        }
    }

    private List<RestaurantModel> restaurantModels;

    private void getRegisteredRestaurants() {

        repositoryManager.getRestaurants().subscribe(new DisposableSubscriber<List<RestaurantModel>>() {
            @Override
            public void onNext(List<RestaurantModel> models) {

                restaurantModels = models;
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                displayMarkers(restaurantModels);
            }
        });
    }

    private boolean createCurrentLocationMarker() {
        if (mCurrentLocation != null && googleMap != null) {
            LatLng myCurrentLocation = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            currentLocationMarker = new MarkerOptions()
                    .position(myCurrentLocation)
                    .title("I'm here vatos!");
            return true;
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        createCurrentLocationMarker();

        googleMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                long restaurantId = (long) marker.getTag();
                navigator.navigateToRestaurantMenu(restaurantId);

            }
        });
    }
}
