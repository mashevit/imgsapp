package com.example.android.myappimgs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myappimgs.data.ImgContract;
import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;
import com.example.android.myappimgs.network.DataServiceGenerator;
import com.example.android.myappimgs.network.Service;
import com.example.android.myappimgs.network.ServiceGenerator;
import com.example.android.myappimgs.network.User;
import com.example.android.myappimgs.pojos.TripsModel;
import com.example.android.myappimgs.prefs.SettingsActivity;
import com.example.android.myappimgs.remote.APIUtils;
import com.example.android.myappimgs.sync.SunshineSyncIntentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

//import com.example.android.myappimgs.sync.SunshineSyncUtils;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyAdapterOnClickHandler, SharedPreferences.OnSharedPreferenceChangeListener/*, LoaderManager.LoaderCallbacks<Cursor>*/ {
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private TextView tvm;
    private final String TAG = MainActivity.class.getSimpleName();

    private int mPosition = RecyclerView.NO_POSITION;

    Service userService;
    private final int ID_FORECAST_LOADER = 44;
    private final int ID_IMG_LOADER = 72;

    public final String[] MAIN_FORECAST_PROJECTION = {
            ImgContract.WeatherEntry.TRIP_NAME,
            ImgContract.WeatherEntry.IMG_ADDR,
            ImgContract.WeatherEntry.SIGHT_NAME,

    };
    public final String[] NAMES_FORECAST_PROJECTION = {
            ImgContract.WeatherEntry.TRIP_NAME,
            ImgContract.WeatherEntry.SIGHT_NAME,

    };


    private ImgsRoomDB mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.myrecy);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        userService = APIUtils.getUserService();

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(layoutManager);
        tvm = findViewById(R.id.trip);

        mRecyclerView.setHasFixedSize(true);

        myAdapter = new MyAdapter(this, this);

        mRecyclerView.setAdapter(myAdapter);

        // getSupportLoaderManager().initLoader(ID_FORECAST_LOADER, null, this);
        // ImgViewModel imgViewModel=ViewModelProviders.of(this).get(ImgViewModel.class);
        //      if(imgViewModel.getAllQuestions()!=null) mDb.wordDao().deleteAll();


        userService = APIUtils.getUserService();
      //  getUsersList();

        //getUsersList1();
   // fetchData();
        ///  login();
        //    mDb=ImgsRoomDB.getDatabase(getApplicationContext());
        ImgViewModel imgViewModel = ViewModelProviders.of(this).get(ImgViewModel.class);
        //  if(imgViewModel.getAllQuestions()!=null) mDb.wordDao().deleteAll();

        imgViewModel.getAllQuestions().observe(this, new Observer<List<Imgs>>() {
            @Override
            public void onChanged(@Nullable List<Imgs> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                myAdapter.setData(taskEntries);
                if (taskEntries.size() > 0) tvm.setText(taskEntries.get(0).getTrip());
            }
        });
        //    tvm.setText(questionsModelList.get(0).getTrip());


        //   SunshineSyncUtils.startImmediateSync(this);

    }

    @Override
    public void onClick(String pos) {

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.mymenu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    /**
     * Callback invoked when a menu item was selected from this Activity's menu.
     *
     * @param item The menu item that was selected by the user
     *
     * @return true if you handle the menu click here, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_update) {

            fetchtrips();



            return true;
//            SharedPreferences settings = getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);
//
//            SharedPreferences.Editor editor = settings.edit();
//
//            editor.putStringSet(String.valueOf(R.string.PREFS_Arr_vals),);
        }else if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
           return true;
        }else if (id == R.id.action_fetch) {
            fetchData();//getUsersList();//fetchData();
            return true;
        }
        else if (id == R.id.action_clear) {

            Intent intentToSyncImmediately = new Intent(MainActivity.this, SunshineSyncIntentService.class);
           // intentToSyncImmediately.putExtra(SunshineSyncIntentServiceclean");
            //intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
            //startService(intentToSyncImmediately);
            MainActivity.this.startService(intentToSyncImmediately);
            Log.d("taggg6g11","ssaa cc" );
            return true;
        }  else if (id == R.id.action_login) {
            login();
            return true;
        }


        //else if (id == R.id.syncFull) {
//        Intent intentToSyncImmediately = new Intent(MainActivity.this, foodSyncIntentSevice.class);
//            intentToSyncImmediately.putExtra(foodSyncIntentSevice.EXTRA_DATA_ID, "full");
//            //intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
//            //startService(intentToSyncImmediately);
//
////        MainActivity.this.startService(intentToSyncImmediately);
//            getUsersList();
//            //getUsersList();
//            return true;
//        }else if (id == R.id.cleanAll) {
//            //    mDb.clearAllTables();
//            Intent intentToSyncImmediately = new Intent(MainActivity.this, foodSyncIntentSevice.class);
//            intentToSyncImmediately.putExtra(foodSyncIntentSevice.EXTRA_DATA_ID, "clean");
//            //intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
//            //startService(intentToSyncImmediately);
//            MainActivity.this.startService(intentToSyncImmediately);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }









   /* @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {


        switch (id) {

            case ID_FORECAST_LOADER:
                Uri forecastQueryUri = ImgContract.WeatherEntry.CONTENT_URI;
                String sortOrder = ImgContract.WeatherEntry.SIGHT_NAME + " ASC";

                return new CursorLoader(this,
                        ImgContract.WeatherEntry.CONTENT_URI_dis,
                        MAIN_FORECAST_PROJECTION,
                        null,
                        null,
                        sortOrder);

            case ID_IMG_LOADER:
                Uri forecastQueryUri1 = ImgContract.WeatherEntry.buildWeatherUriWithSight("sight");//todo: add sight
                String sortOrder1 = ImgContract.WeatherEntry.SIGHT_NAME + " ASC";

                return new CursorLoader(this,
                        forecastQueryUri1,
                        NAMES_FORECAST_PROJECTION,
                        null,
                        null,
                        sortOrder1);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {


        myAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        mRecyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0) ;//showWeatherDataView();
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        *//*
         * Since this Loader's data is now invalid, we need to clear the Adapter that is
         * displaying the data.
         *//*
        myAdapter.swapCursor(null);

    }
*/









    private void fetchData() {
        //relativeLayout.setVisibility(View.VISIBLE);

/*        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImgViewModel.delAll();
                //    takeAction();

            }
        }, 3000);*/
        ServiceGenerator  /*DataServiceGenerator*/ dataServiceGenerator = new ServiceGenerator();//DataServiceGenerator/*()
        Service service = dataServiceGenerator.createService(Service.class);

        SharedPreferences settings = android.preference.PreferenceManager.getDefaultSharedPreferences (MainActivity.this);//getActivity().getApplicationContext().getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);
        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int finalind = Integer.parseInt(settings.getString(String.valueOf(R.string.PREFS_final_ind),"3"));
        Log.d("taggg6g","ssaa"+ finalind+"cc" );
        Call<List<ImgModel>> call = service.getQuestions(finalind);

      //  Call<List<ImgModel>> call = service.getQuestions();

        call.enqueue(new Callback<List<ImgModel>>() {
            @Override
            public void onResponse(Call<List<ImgModel>> call, Response<List<ImgModel>> response) {
                Log.d(TAG + "fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel" + "5");

                if (response.isSuccessful()) {
                    Log.d(TAG + "fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel" + "7");

                    if (response != null) {
                        List<ImgModel> questionsModelList = response.body();
                        if (questionsModelList.size() > 0)
                            tvm.setText(questionsModelList.get(0).getTrip());
                        for (int i = 0; i < questionsModelList.size(); i++) {
                            // String addr = questionsModelList.get(i).getImgaddr();
                            String sightname = questionsModelList.get(i).getSight();
                            String trip = questionsModelList.get(i).getTrip();
                            //   List<String> listaaddr= questionsModelList.get(i).getAddr();
                            ArrayList<String> p = new ArrayList(response.body().get(i).getAddr());
                            Log.d(TAG + "fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel" + p);

                            Imgs questions = new Imgs(trip, "", sightname, p);

                            ImgViewModel.insert(questions);
                        }

   /*                    Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            //    takeAction();

                            }
                        }, 3000);*/
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ImgModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"maybe wrong credentials",Toast.LENGTH_LONG).show();
            }
        });
    }




    private void fetchtrips() {
        //relativeLayout.setVisibility(View.VISIBLE);

/*        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImgViewModel.delAll();
                //    takeAction();

            }
        }, 3000);*/
        DataServiceGenerator/*ServiceGenerator*/ dataServiceGenerator = new DataServiceGenerator/*()ServiceGenerator*/();
        Service service = dataServiceGenerator.createService(Service.class);
        Call<List<TripsModel>> call = service.getTrips();

        call.enqueue(new Callback<List<TripsModel>>() {
            @Override
            public void onResponse(Call<List<TripsModel>> call, Response<List<TripsModel>> response) {
                Log.d(TAG + "fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel" + "5");

                if (response.isSuccessful()) {
                    Log.d(TAG + "fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel" + "7");

                    if (response != null) {
                        List<TripsModel> questionsModelList = response.body();
                        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences (MainActivity.this);//getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);

                        SharedPreferences.Editor editor = settings.edit();


                        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                    //    int[] list = new int[10];
                        StringBuilder str = new StringBuilder();
//                        for (int i = 0; i < list.length; i++) {
//
//                        }
                        prefs.edit().putString("string", str.toString());

                        Set<String> vals=new HashSet<String>();
                        str.append(questionsModelList.size()).append("@#");
                        for (int i = 0; i < questionsModelList.size(); i++) {
                            // String addr = questionsModelList.get(i).getImgaddr();
                            int id = questionsModelList.get(i).getId();
                            String trip = questionsModelList.get(i).getTripname();
                            str.append(id).append("@#").append(trip).append("@#");
                         //   String trip = questionsModelList.get(i).getTripname();
                            vals.add(trip);
                            //   List<String> listaaddr= questionsModelList.get(i).getAddr();
                        }
                        editor.putStringSet(String.valueOf(R.string.PREFS_Arr_vals),vals);
                        editor.putString(String.valueOf(R.string.PREFS_Arr_inds),str.toString());

                        editor.commit();
                        String indexes1 = settings.getString(String.valueOf(R.string.PREFS_Arr_inds),"");
                        Set<String> vals1=settings.getStringSet(String.valueOf(R.string.PREFS_Arr_vals), null);
//                        StringTokenizer st = new StringTokenizer(indexes, ",");
                        String[] savedList = new String[3];

                        Log.d("tagggg0","ssaa"+ vals1+"cc" + indexes1);
   /*                    Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            //    takeAction();

                            }
                        }, 3000);*/
                    }
                }
            }

            @Override
            public void onFailure(Call<List<TripsModel>> call, Throwable t) {

            }
        });
    }






    private void login() {
        //relativeLayout.setVisibility(View.VISIBLE);

/*        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImgViewModel.delAll();
                //    takeAction();

            }
        }, 3000);*/
        DataServiceGenerator dataServiceGenerator = new DataServiceGenerator();
        Service service = dataServiceGenerator.createService(Service.class);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Call<Void> call = service.gettoken(new User(sharedPreferences.getString(String.valueOf(R.string.PREFS_user),"admin"),sharedPreferences.getString(String.valueOf(R.string.PREFS_pass),
                "admin")));
        call.enqueue(new Callback<Void>() {


                         @Override
                         public void onResponse(Call<Void> call, Response<Void> response) {
                             ServiceGenerator.Token=response.headers().get("Authorization");

                             fetchData();
                         }

                         @Override
                         public void onFailure(Call<Void> call, Throwable t) {
                             Toast.makeText(MainActivity.this,"maybe wrong credentials from login",Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }








    public void getUsersList(){
        SharedPreferences settings = android.preference.PreferenceManager.getDefaultSharedPreferences (MainActivity.this);//getActivity().getApplicationContext().getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);
        // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int finalind = Integer.parseInt(settings.getString(String.valueOf(R.string.PREFS_final_ind),"3"));
        Call<List<ImgModel>> call = userService.getQuestions(finalind);
        call.enqueue(new Callback<List<ImgModel>>() {
            @Override
            public void onResponse(Call<List<ImgModel>> call, Response<List<ImgModel>> response) {
                if(response.isSuccessful()){


                    Log.d(TAG + "fddffdfdfdfdw2122121", "Updating list of tasks from LiveData in ViewModel" + response.body());


                    if (response != null) {
                        List<ImgModel> questionsModelList = response.body();
                        if (questionsModelList.size() > 0)
                            tvm.setText(questionsModelList.get(0).getTrip());
                        for (int i = 0; i < questionsModelList.size(); i++) {
                            // String addr = questionsModelList.get(i).getImgaddr();
                            String sightname = questionsModelList.get(i).getSight();
                            String trip = questionsModelList.get(i).getTrip();
                            //   List<String> listaaddr= questionsModelList.get(i).getAddr();
                            ArrayList<String> p = new ArrayList(response.body().get(i).getAddr());
                            Log.d(TAG + "fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel" + p);

                            Imgs questions = new Imgs(trip, "", sightname, p);

                            ImgViewModel.insert(questions);



                   /* list = response.body();
                    listView.setAdapter(new UserAdapter(MainActivity.this, R.layout.list_user, list));*/
                        }
                    }


                }

            }

            @Override
            public void onFailure(Call<List<ImgModel>> call, Throwable t) {

            }

        });}


    public void getUsersList1(){
        Call<List<Object>> call = userService.getQuestions1();
        call.enqueue(new Callback<List<Object>>() {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response) {
                if(response.isSuccessful()) {


                    Log.d(TAG + "fddffdfdfdfdw2122121", "Updating list of tasks from LiveData in ViewModel" + response.body());

                }
                }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t) {

            }

        }

      );}


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if (key.equals("CityList")){
//            //SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//            String selected = sharedPreferences.getString("CityList",
//                    "default string"
//            );
//            ListPreference mylistpreference= (ListPreference) sharedPreferences.findPreference(key);
//            int index = mylistpreference.findIndexOfValue(selected)
//            int index = mylistpreference.findIndexOfValue(selected)
//            initcity(sharedPreferences);
//        }
    }

    private void initcity(SharedPreferences sharedPreferences) {

    }
}
