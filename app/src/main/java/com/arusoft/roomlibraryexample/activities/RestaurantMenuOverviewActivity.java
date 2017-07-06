package com.arusoft.roomlibraryexample.activities;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.arusoft.roomlibraryexample.R;
import com.arusoft.roomlibraryexample.activities.base.BaseActivity;
import com.arusoft.roomlibraryexample.data.RepositoryManager;
import com.arusoft.roomlibraryexample.databinding.ActivityRestaurantMenuOverviewBinding;
import com.arusoft.roomlibraryexample.presentation.MenuItemModel;
import com.arusoft.roomlibraryexample.presentation.adapters.RestaurantMenuAdapter;
import com.arusoft.roomlibraryexample.util.AppConstants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class RestaurantMenuOverviewActivity extends BaseActivity implements RestaurantMenuAdapter.ItemListener {

    @Inject
    RepositoryManager repositoryManager;

    private ActivityRestaurantMenuOverviewBinding binding;
    private RestaurantMenuAdapter adapter;
    private List<MenuItemModel> menuItemModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_menu_overview);
        getActivityComponent().inject(this);
        adapter = new RestaurantMenuAdapter();
        binding.restaurantMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.restaurantMenuRecyclerView.setAdapter(adapter);

        long restaurantId = getIntent().getLongExtra(AppConstants.EXTRAS_RESTAURANT_ID, -1);
        if (restaurantId != -1) {
            repositoryManager.getRestaurantMenu(restaurantId).subscribe(new DisposableSubscriber<List<MenuItemModel>>() {
                @Override
                public void onNext(List<MenuItemModel> models) {
                    menuItemModels = models;
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onComplete() {
                    displayMenu();
                }
            });
        } else {
            finish();
        }

    }

    private void displayMenu() {
        if (menuItemModels != null && menuItemModels.size() > 0) {
            binding.restaurantNameOverviewLabel.setText(menuItemModels.get(0).getRestaurantName());
            adapter.addAll(menuItemModels);
        } else {
            //TODO: Nothing to say
        }
    }

    @Override
    public void onItemClick(View view, int position, MenuItemModel menuItemModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning!");
        builder.setMessage("Do you want to delete this item ? :C");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("ÑO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }
}
