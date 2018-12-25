package com.example.android.myappimgs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.myappimgs.data.ImgContract;
import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;
import com.example.android.myappimgs.network.DataServiceGenerator;
import com.example.android.myappimgs.network.Service;
import com.example.android.myappimgs.network.ServiceGenerator;
import com.example.android.myappimgs.network.User;
import com.example.android.myappimgs.remote.APIUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

//import com.example.android.myappimgs.sync.SunshineSyncUtils;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyAdapterOnClickHandler/*, LoaderManager.LoaderCallbacks<Cursor>*/ {
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
        getUsersList();

        //getUsersList1();
    fetchData();
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
        DataServiceGenerator/*ServiceGenerator*/ dataServiceGenerator = new DataServiceGenerator/*()ServiceGenerator*/();
        Service service = dataServiceGenerator.createService(Service.class);
        Call<List<ImgModel>> call = service.getQuestions();

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
        Call<Void> call = service.gettoken(new User("admin", "admin"));
        call.enqueue(new Callback<Void>() {


                         @Override
                         public void onResponse(Call<Void> call, Response<Void> response) {
                             ServiceGenerator.Token=response.headers().get("Authorization");

                             fetchData();
                         }

                         @Override
                         public void onFailure(Call<Void> call, Throwable t) {

                         }
                     }
        );
    }








    public void getUsersList(){
        Call<List<ImgModel>> call = userService.getQuestions();
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







}
