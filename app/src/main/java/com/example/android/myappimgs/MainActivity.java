package com.example.android.myappimgs;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.myappimgs.Imgs.ImgsViewModelFactory;
import com.example.android.myappimgs.Imgs.ImgssssViewModel;
import com.example.android.myappimgs.data.ImgContract;
import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsDao;
import com.example.android.myappimgs.dataRoom.ImgsRepository;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;
import com.example.android.myappimgs.network.DataServiceGenerator;
import com.example.android.myappimgs.network.Service;
import com.example.android.myappimgs.network.ServiceGenerator;
import com.example.android.myappimgs.network.User;
import com.example.android.myappimgs.pojos.TripsModel;
import com.example.android.myappimgs.prefs.SettingsActivity;
import com.example.android.myappimgs.remote.APIUtils;
import com.example.android.myappimgs.sync.SunshineSyncIntentService;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.ViewRenderer;
import com.github.vivchar.rendererrecyclerviewadapter.binder.CompositeViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;

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
    public static MyAdapter myAdapter;
    private TextView tvm;
    private ProgressBar mLoadingIndicator;

    private final String TAG = MainActivity.class.getSimpleName();
   public static List<Imgs> topass;
    private int mPosition = RecyclerView.NO_POSITION;
    ImgsRepository imgsRepository;
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
        setContentView(R.layout.entrancescreen);
        //imgsRepository=new ImgsRepositor




//
//
//        RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();
//        adapter.registerRenderer(getParentItemViewBinder().registerRenderer(getChildItemViewBinder()));
//        adapter.setItems(getParentItems());






    //    mRecyclerView = (RecyclerView) findViewById(R.id.myrecy);
    //    mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
        if(topass==null){
        topass=new ArrayList<Imgs>();}
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        userService = APIUtils.getUserService();

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
    //    mRecyclerView.addItemDecoration(decoration);
   //     mRecyclerView.setLayoutManager(layoutManager);
        tvm = findViewById(R.id.textView);
mDb=ImgsRoomDB.getDatabase(this);
   //     mRecyclerView.setHasFixedSize(false);

    //    myAdapter = new MyAdapter(this, this/*,this*/);
   //     myAdapter.setData(topass);
    //    mRecyclerView.setAdapter(myAdapter);

        // getSupportLoaderManager().initLoader(ID_FORECAST_LOADER, null, this);
         ImgViewModel imgViewModel=ViewModelProviders.of(this).get(ImgViewModel.class);
        //      if(imgViewModel.getAllQuestions()!=null) mDb.wordDao().deleteAll();
        SharedPreferences settings = android.preference.PreferenceManager.getDefaultSharedPreferences (MainActivity.this);//getActivity().getApplicationContext().getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);
        String tripname = settings.getString(String.valueOf(R.string.PREFS_final_tripname),"null trip");
        tvm.setText(tripname);



        userService = APIUtils.getUserService();
      //  getUsersList();

        //getUsersList1();
   // fetchData();
        ///  login();
            mDb=ImgsRoomDB.getDatabase(getApplicationContext());
   //     ImgViewModel imgViewModel = ViewModelProviders.of(this).get(ImgViewModel.class);
        //  if(imgViewModel.getAllQuestions()!=null) mDb.wordDao().deleteAll();

//        imgViewModel.getbysight().observe(this, new Observer<List<Imgs>>() {
//            @Override
//            public void onChanged(@Nullable List<Imgs> strings) {
//                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
//                Log.d(TAG + "fddff5dfdfd", "Updating list of tasks from LiveData in ViewModel" + strings);
//                if(null!=strings&&myAdapter.getItemCount()<2)
//
//                myAdapter.setData(strings);
//
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
//                ///showWeatherDataView();
//               // if (strings.size() > 0) tvm.setText(taskEntries.get(0).getTrip());
//            }


      //  });
//        //    tvm.setText(questionsModelList.get(0).getTrip());


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
           // showLoading();
            Intent intentToSyncImmediately = new Intent(MainActivity.this, SunshineSyncIntentService.class);
            intentToSyncImmediately.putExtra(SunshineSyncIntentService.EXTRA_DATA_ID, "clean");
            // intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
            //startService(intentToSyncImmediately);
            MainActivity.this.startService(intentToSyncImmediately);
            Log.d("taggg6g11", "ssaa cc");

//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
            return true;
        }  else if (id == R.id.action_login) {
            login();
            return true;}
         else if (id == R.id.showall) {
//            Intent intentToSyncImmediately = new Intent(MainActivity.this, SunshineSyncIntentService.class);
//            intentToSyncImmediately.putExtra(SunshineSyncIntentService.EXTRA_DATA_ID,"fetchdb");
//            // intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
//            //startService(intentToSyncImmediately);
//            MainActivity.this.startService(intentToSyncImmediately);
            Log.d("taggg6g11","ssaa cc" );

            //new fetchAsyncTask(mDb.wordDao()).execute();


            Intent intentToSyncImmediately2 = new Intent(MainActivity.this, Main2Activity.class);
           // intentToSyncImmediately.putExtra(SunshineSyncIntentService.EXTRA_DATA_ID,"fetchdb");
            // intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
            //startService(intentToSyncImmediately);
            MainActivity.this.startActivity(intentToSyncImmediately2);


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





    private CompositeViewBinder getParentItemViewBinder() {
        return new CompositeViewBinder<>(
                R.layout.activity_main,
                R.id.myrecy,
                DefaultCompositeViewModel.class /* imported from library */
                //new ViewBinder.Binder(){...} /* if you need bind something else */
        );
    }



    private List<DefaultCompositeViewModel> getParentItems() {
        ArrayList<DefaultCompositeViewModel> parents = new ArrayList();
        for (int i=0;i<topass.size();i++) {
            ArrayList<ChildItem> children = new ArrayList();
            Imgs qqw=topass.get(i);
            for (int h=0;h<qqw.getImgaddr().length();h++) {
               // ChildItem qwq=new ChildItem(qqw.getImgaddr());
                children.add(new ChildItem(qqw.getListaaddr().get(h)));
            }
            parents.add(new DefaultCompositeViewModel(children));
        }
        return parents;
    }




    private ViewBinder getChildItemViewBinder() {
//        return new ViewBinder<>(
//                        R.layout.horizrecyhold,
//                        ChildItem.class,
//                        (model, finder, payloads) -> finder {
//                    ImageView imageView = finder.find(R.id.imageView);
//        Glide.with(getContext()).load(model.getUrl()).into(imageView);
//                }
//        )

        return new ViewBinder<>(
                R.layout.horizrecyhold,
                ChildItem.class,
                (model, finder, payloads) -> finder
                        //.setText(R.id.title, model.getTitle())
                        //.setTextColor(R.id.text, model.getTitleColor())
                        //.setImageBitmap(R.id.image, model.getImageBitmap())
                        //.setOnClickListener(R.id.button, new OnClickListener() {...})
                       .find(R.id.imageView, (ViewProvider<ImageView>) imageView -> {
			Glide.with(this).load(model.getTitle()).into(imageView);
		})
        );
    }
    private void fetchData() {
        //relativeLayout.setVisibility(View.VISIBLE);

/*        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImgViewModel.delAll();
                //    takeAction();

            }
        }, 3000);*///showLoading();
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

                    if (response != null) {topass=new ArrayList<Imgs>();
                        List<ImgModel> questionsModelList = response.body();
                        if (questionsModelList.size() > 0)
//                            tvm.setText(questionsModelList.get(0).getTrip());
                        for (int i = 0; i < questionsModelList.size(); i++) {
                            // String addr = questionsModelList.get(i).getImgaddr();
                            String sightname = questionsModelList.get(i).getSight();
                            String trip = questionsModelList.get(i).getTrip();
                            //   List<String> listaaddr= questionsModelList.get(i).getAddr();
                            ArrayList<String> p = new ArrayList(response.body().get(i).getAddr());
                            Log.d(TAG + "fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel" + p);

                            Imgs questions = new Imgs(trip, "", sightname, p);
                           topass.add(questions);
                            ImgViewModel.insert(questions);
                           // new insertAsyncTask1(mDb.wordDao(),"","").execute(questions);
                        }

                      //  Log.d(TAG + "7dfdfd", "Updating list of tasks from LiveData in ViewModel" +topass);
                        Log.d(TAG + "fddffdfdfdfd", "topass" + topass);
                       // myAdapter.setData(topass);

                        Intent intentToSyncImmediately2 = new Intent(MainActivity.this, Main2Activity.class);
                        // intentToSyncImmediately.putExtra(SunshineSyncIntentService.EXTRA_DATA_ID,"fetchdb");
                        // intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
                        //startService(intentToSyncImmediately);

                        Main2Activity.topass=topass;
                        MainActivity.this.startActivity(intentToSyncImmediately2);
                        Toast.makeText(MainActivity.this,"fetched!!!!",Toast.LENGTH_LONG).show();
   /*                    Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            //    takeAction();

                            }
                        }, 3000);*/
                    }
                }
//                Intent intent = getIntent();
//                finish();
//                startActivity(intent);
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
      ServiceGenerator dataServiceGenerator = new ServiceGenerator();
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
                        Toast.makeText(MainActivity.this,"Got Trips",Toast.LENGTH_LONG).show();
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
                             String auth=response.headers().get("Authorization");
                             if(null==auth)  Toast.makeText(MainActivity.this,"NOT AUTHORIZED",Toast.LENGTH_LONG).show();
                             else {
                                 ServiceGenerator.Token = auth;//response.headers().get("Authorization");

                                 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences (MainActivity.this);//getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);

                                 SharedPreferences.Editor editor = settings.edit();
                                 editor.putString(String.valueOf(R.string.auth),auth);
                                 editor.commit();





                                 Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_LONG).show();
                             }
                            // fetchData();
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




    private  class insertAsyncTask extends AsyncTask<Imgs, Void, Void> {

        private ImgsDao mAsyncTaskDao;

        insertAsyncTask(ImgsDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Imgs... imgs) {

            mAsyncTaskDao.insert(imgs[0]);
            return null;
        }
    }
    private  class fetchAsyncTask extends AsyncTask<String, Void, List<Imgs>> {

        private ImgsDao mAsyncTaskDao;

        fetchAsyncTask(ImgsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Imgs> doInBackground(final String... params) {
            List<Imgs> ans= mAsyncTaskDao.getAllQuestionsreg();
            return ans;
        }
        @Override
        protected void onPostExecute(List<Imgs> result) {
            myAdapter.setData(result);

        }
    }





    private static class insertAsyncTask1 extends AsyncTask<Imgs, Void, Void> {

        private ImgsDao mAsyncTaskDao;
        private String sightname;
        private String tripname;
        insertAsyncTask1(ImgsDao dao,String name,String tn) {
            mAsyncTaskDao = dao;sightname=name;tripname=tn;
        }

        @Override
        protected Void doInBackground(final Imgs... params) {
            if (mAsyncTaskDao.sightByName(params[0].getSight()).size()==0)
                mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
//
//    private void showWeatherDataView() {
//        /* First, hide the loading indicator */
//        mLoadingIndicator.setVisibility(View.INVISIBLE);
//        /* Finally, make sure the weather data is visible */
//        mRecyclerView.setVisibility(View.VISIBLE);
//    }
//
//    /**
//     * This method will make the loading indicator visible and hide the weather View and error
//     * message.
//     * <p>
//     * Since it is okay to redundantly set the visibility of a View, we don't need to check whether
//     * each view is currently visible or invisible.
//     */
//    private void showLoading() {
//        /* Then, hide the weather data */
//        mRecyclerView.setVisibility(View.INVISIBLE);
//        /* Finally, show the loading indicator */
//        mLoadingIndicator.setVisibility(View.VISIBLE);
//    }


}
