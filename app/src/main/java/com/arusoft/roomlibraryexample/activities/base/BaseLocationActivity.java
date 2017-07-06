package com.arusoft.roomlibraryexample.activities.base;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

/**
 * Created by joseluisrf on 9/10/16.
 */
public abstract class BaseLocationActivity
        extends BaseActivity
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = BaseLocationActivity.class.getSimpleName();
    // Keys for storing activity state in the Bundle.
    protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    protected final static String KEY_LOCATION = "location";
    private static final long POLLING_FREQ = 5000;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int REQUESTED_PERMISSION_INDEX = 0;
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    protected Location mCurrentLocation;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    private LocationListener locationListener;
    private ResultCallback<LocationSettingsResult> resultResultCallback;
    protected LocationSettingsRequest mLocationSettingsRequest;

    private boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateValuesFromBundle(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /***********************************************************************************************
     * Class Method
     ************************************************************************************************/


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            removeLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            switch (requestCode) {
                case LOCATION_PERMISSION_REQUEST: {
                    if (grantResults[REQUESTED_PERMISSION_INDEX] == PackageManager.PERMISSION_GRANTED) {
                        checkLocationSettings();
                    } else {
                        //TODO:
                    }
                    return;
                }
            }
        }
    }




    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        outState.putParcelable(KEY_LOCATION, mCurrentLocation);
        super.onSaveInstanceState(outState);
    }

    /***********************************************************************************************
     * Protected Methods
     ************************************************************************************************/
    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() throws SecurityException {
        if (mRequestingLocationUpdates) {
            return;
        }

        if (locationListener != null) {

            requestLastKnownLocation();
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    locationListener
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    Log.i(TAG, "FusedLocationApi.onResult-Status.isSuccess:" + status.isSuccess());
                    mRequestingLocationUpdates = true;
                }
            });
        }

    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );

        if (resultResultCallback != null) {
            result.setResultCallback(resultResultCallback);
        }
    }

    protected void removeLocationUpdates() {
        if (locationListener != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient,
                    locationListener
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    mRequestingLocationUpdates = false;
                }
            });
        }
    }
    protected Location requestLastKnownLocation() throws SecurityException {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            checkLocationSettings();
        }
        return location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLocationSettings();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setLocationLister(LocationListener listener) {
        this.locationListener = listener;
    }

    public void setResultResultCallback(ResultCallback<LocationSettingsResult> callback) {
        this.resultResultCallback = callback;
    }
}
