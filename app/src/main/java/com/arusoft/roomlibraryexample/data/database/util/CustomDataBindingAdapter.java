package com.arusoft.roomlibraryexample.data.database.util;

import android.databinding.BindingAdapter;
import android.widget.TextView;

public class CustomDataBindingAdapter {

    @BindingAdapter("binding:setPrice")
    public static void setPrice(TextView view, float value) {
        try {
            view.setText(String.valueOf(value));
        } catch (NumberFormatException ex) {
            view.setText("0");
        }
    }

//    @BindingAdapter("app:setPrice")
//    public static void setPrice(TextView view, CharSequence value) {
//        try {
//            view.setText(String.Float.parseFloat(value.toString()));
//        } catch (NumberFormatException ex) {
//            view.setText("0");
//        }
//    }
}
