package com.arusoft.roomlibraryexample.data.database.util;

public class DataBaseConstants {

    public static final String DATA_BASE_NAME = "room_example.db";
    public static final int DATA_BASE_VERSION = 1;

    public static class RestaurantTable {

        public static final String TABLE_NAME = "restaurant";

        public static final String ID_COLUMN_NAME = "_id";
        public static final String NAME_COLUMN_NAME = "name";
        public static final String DESCRIPTION_COLUMN_NAME = "description";
        public static final String OPEN_TIME_COLUMN_NAME = "open_time";
        public static final String CLOSE_TIME_COLUMN_NAME = "close_time";
        public static final String RESTAURANT_LOCATION_PREFIX = "restaurant_";
    }
}
