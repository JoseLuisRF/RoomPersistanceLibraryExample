package com.arusoft.roomlibraryexample.presentation.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arusoft.roomlibraryexample.R;
import com.arusoft.roomlibraryexample.databinding.AdapterItemMenuBinding;
import com.arusoft.roomlibraryexample.presentation.MenuItemModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.RestaurantMenuViewHolder> {

    private List<MenuItemModel> mDataSet = new ArrayList<>();
    private ItemListener listener;

    public interface ItemListener {
        void onItemClick(View view, int position, MenuItemModel menuItemModel);
    }

    @Override
    public RestaurantMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        AdapterItemMenuBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_item_menu, parent, false);
        return new RestaurantMenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RestaurantMenuViewHolder holder, final int position) {
        holder.binding.setModel(mDataSet.get(position));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position, mDataSet.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setItemListener(ItemListener listener) {
        this.listener = listener;
    }

    public void add(MenuItemModel item) {
        mDataSet.add(item);
        notifyItemInserted(mDataSet.size() - 1);
    }

    public void addAll(List<MenuItemModel> values) {
        for (MenuItemModel video : values) {
            add(video);
        }
    }

    public void remove(MenuItemModel item) {
        int position = mDataSet.indexOf(item);
        if (position > -1) {
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public MenuItemModel getItem(int position) {
        return mDataSet.get(position);
    }


    public class RestaurantMenuViewHolder extends RecyclerView.ViewHolder {

        public AdapterItemMenuBinding binding;

        public RestaurantMenuViewHolder(AdapterItemMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
