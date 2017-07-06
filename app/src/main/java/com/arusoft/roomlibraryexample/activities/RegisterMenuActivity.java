package com.arusoft.roomlibraryexample.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.arusoft.roomlibraryexample.R;
import com.arusoft.roomlibraryexample.activities.base.BaseActivity;
import com.arusoft.roomlibraryexample.databinding.ActivityRegisterMenuBinding;
import com.arusoft.roomlibraryexample.presentation.MenuItemModel;
import com.arusoft.roomlibraryexample.util.AppConstants;

public class RegisterMenuActivity extends BaseActivity {
    ActivityRegisterMenuBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_menu);
        binding.setModel(new MenuItemModel());

        binding.saveMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.getModel() != null) {
                    if (TextUtils.isEmpty(binding.getModel().getTitle())) {
                        Toast.makeText(RegisterMenuActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(binding.getModel().getDescription())) {
                        Toast.makeText(RegisterMenuActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(AppConstants.EXTRAS_MENU_ITEM, binding.getModel());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                }
            }
        });
    }
}
