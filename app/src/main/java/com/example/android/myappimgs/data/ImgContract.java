/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.myappimgs.data;

import android.net.Uri;
import android.provider.BaseColumns;



/**
 * Defines table and column names for the weather database. This class is not necessary, but keeps
 * the code organized.
 */
public class ImgContract {


    public static final String CONTENT_AUTHORITY = "com.example.android.imgs";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_WEATHER = "weather";

    public static final String PATH_PICS = "pics";

    public static final String PATH_WEATHER_dis = "weather_dis";











    public static abstract class TableProducts implements BaseColumns {


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PICS)
                .build();
        public static final String TABLE_NAME = "product";
        public static final String COLUMN_NAME_WORKER_ID = "id";
        public static final String COLUMN_IMG_ADDR = "pname";
        public static final String FORK = "fk";



    }
    /* Inner class that defines the table contents of the weather table */
    public static final class WeatherEntry implements BaseColumns {

        /* The base CONTENT_URI used to query the Weather table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER)
                .build();
        public static final Uri CONTENT_URI_dis = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_WEATHER_dis)
                .build();
        /* Used internally as the name of our weather table. */
        public static final String TABLE_NAME = "weather";



        public static final String IMG_ADDR = "address";


        public static final String TRIP_NAME = "tripname";



        public static final String SIGHT_NAME = "nameofsight";


        public static Uri buildWeatherUriWithDate(long date) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Long.toString(date))
                    .build();
        }

        public static Uri buildWeatherUriWithSight(String sight) {
            return CONTENT_URI.buildUpon()
                    .appendPath(sight)
                    .build();
        }

    }
}