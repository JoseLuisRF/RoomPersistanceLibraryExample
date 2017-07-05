package com.arusoft.roomlibraryexample.activities.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.arusoft.roomlibraryexample.R;
import com.arusoft.roomlibraryexample.RoomExampleApplication;
import com.arusoft.roomlibraryexample.di.components.ActivityComponent;
import com.arusoft.roomlibraryexample.di.components.ApplicationComponent;
import com.arusoft.roomlibraryexample.di.components.DaggerActivityComponent;
import com.arusoft.roomlibraryexample.di.modules.RepositoryModule;
import com.arusoft.roomlibraryexample.di.modules.StorageModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;
    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .storageModule(new StorageModule())
                .repositoryModule(new RepositoryModule())
                .build();
        setUpProgressDialog();

    }

    private void setUpProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
    }

    protected ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected ApplicationComponent getApplicationComponent() {
        return RoomExampleApplication.getComponent(this);
    }

    protected void showLoader() {
        mProgressDialog.show();
    }

    protected void dismissLoader() {
        mProgressDialog.dismiss();
    }
}
