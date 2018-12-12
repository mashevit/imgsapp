package com.example.android.myappimgs.utilities;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.android.myappimgs.data.ImgContract;
import com.example.android.myappimgs.data.ImgDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;


public final class OpenWeatherJsonUtils {
    static ImgDbHelper mDbHelper;
    static SQLiteDatabase dbRead;
    static SQLiteDatabase dbWrite;
    static Context mContext;

    public static ContentValues[] getWeatherContentValuesFromJson(Context context, String forecastJsonStr)
            throws JSONException {


        //  ContentValues[][] finalval= new ContentValues[][];
        mContext = context;
        JSONArray forecastJson = new JSONArray(forecastJsonStr);
// JSONArray jsonWeatherArray = forecastJson.getJSONArray(OWM_LIST);

        mDbHelper = new ImgDbHelper(context);
        // Gets the data repository in write mode
        dbWrite = mDbHelper.getWritableDatabase();
        // Gets the data repository in write mode
        dbRead = mDbHelper.getReadableDatabase();
        // JSONArray jsonWeatherArray = forecastJson.getJSONArray(OWM_LIST);
        ContentValues[] weatherValues = new ContentValues[forecastJson.length()];


        ContentValues[][] sightContentValues = new ContentValues[forecastJson.length()][];


        for (int i = 0; i < forecastJson.length(); i++) {

            JSONObject jsonobtmp = forecastJson.getJSONObject(i);

            //  JSONObject cityJson = forecastJson.getJSONObject(OWM_CITY);
            String addrTmp = jsonobtmp.getString("");
            //   JSONObject picJson = forecastJson.getJSONObject("addr");


            String sightname = jsonobtmp.getString("sightname");


            //        long ind = insertNewSight(sightname,context);//addSight(sightname);

            JSONArray picJson = jsonobtmp.getJSONArray("addr");

            ContentValues[] tmpValues = new ContentValues[1 + picJson.length()];

            tmpValues[0].put(ImgContract.WeatherEntry.SIGHT_NAME, sightname);
            ContentValues[] weatherContentValues = new ContentValues[picJson.length()];
            ContentValues tmp2vals = new ContentValues();

            for (int j = 0; j < picJson.length(); j++) {


                addrTmp = picJson.getString(j);


                tmp2vals.put(ImgContract.WeatherEntry.SIGHT_NAME, sightname);
                tmp2vals.put(ImgContract.WeatherEntry.IMG_ADDR, addrTmp);
                tmp2vals.put(ImgContract.WeatherEntry.TRIP_NAME, "current trip");


                tmpValues[1 + j].put("addr", addrTmp);
             /*   ContentValues weatherValues = new ContentValues();
                weatherValues.put(ImgContract.TableProducts.COLUMN_IMG_ADDR, addrTmp);
                weatherValues.put(ImgContract.TableProducts.FORK, i*//*ind*//*);

                weatherContentValues[j] = weatherValues;*/
            }
            //  Uri uritmp = ImgContract.TableProducts.CONTENT_URI;
            //   tmpValues[1].put("addr",weatherContentValues);
            weatherValues[i] = tmp2vals;


            //context.getContentResolver().bulkInsert(uritmp, weatherContentValues);
            // return weatherContentValues;
        }
      /*  finalval[0]=weatherContentValues;
        finalval[1]=sightContentValues;
    */
        //  return sightContentValues;
        return weatherValues;
    }

}





/*    public static long addSight(String sn) {


      ContentValues values = new ContentValues();
        values.put(ImgContract.WeatherEntry.SIGHT_NAME, sn);   long newRowId;     newRowId = dbWrite.insert(
                ImgContract.WeatherEntry.TABLE_NAME,
                null,
                values);
        return newRowId;
    }

    public static long addPic(ContentValues values) {


       // ContentValues values = new ContentValues();
 long newRowId;
 newRowId = dbWrite.insert(
                ImgContract.TableProducts.TABLE_NAME,
                null,
                values);
        return newRowId;
    }





    static long insertNewSight(String task,Context context){
        ContentValues values = new ContentValues();
        values.put(ImgContract.WeatherEntry.SIGHT_NAME,task);


        Uri tmp= context.getContentResolver().
                insert(ImgContract.WeatherEntry.CONTENT_URI,values);
        return Long.parseLong(tmp.getLastPathSegment());
    }*/


//setContentView(R.layout.activity_main);
//To access your database, instantiate your subclass of SQLiteOpenHelper:


