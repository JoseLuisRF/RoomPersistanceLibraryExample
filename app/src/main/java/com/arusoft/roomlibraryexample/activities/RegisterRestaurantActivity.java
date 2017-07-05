package com.arusoft.roomlibraryexample.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.arusoft.roomlibraryexample.R;
import com.arusoft.roomlibraryexample.activities.base.BaseActivity;
import com.arusoft.roomlibraryexample.data.RepositoryManager;
import com.arusoft.roomlibraryexample.databinding.ActivityRegisterRestaurantBinding;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class RegisterRestaurantActivity extends BaseActivity {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 201;

    @Inject
    RepositoryManager repositoryManager;
    private ActivityRegisterRestaurantBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_restaurant);

        getActivityComponent().inject(this);

        mBinding.registerSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.getModel() != null) {
                    showLoader();

                    repositoryManager.registerRestaurant(mBinding.getModel())
                    .subscribe(new DisposableSubscriber<Void>() {
                        @Override
                        public void onNext(Void aVoid) {

                        }

                        @Override
                        public void onError(Throwable t) {
                            t.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            dismissLoader();
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


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Toast.makeText(this, PlaceAutocomplete.getStatus(this, data).getStatusMessage(), Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void openAutoComplete() {
        try {

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }
}
