package com.example.android.myappimgs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.android.myappimgs.dataRoom.ImgsRepository;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;
import com.example.android.myappimgs.network.DataServiceGenerator;
import com.example.android.myappimgs.network.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

//import com.example.android.myappimgs.sync.SunshineSyncUtils;

public class MainActivity extends AppCompatActivity implements MyAdapter.MyAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor>{
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private TextView tvm;
    private final String TAG = MainActivity.class.getSimpleName();

    private int mPosition = RecyclerView.NO_POSITION;

    public static final int INDEX_SIGHT_MAIN = 3;
    public  final int INDEX_WEATHER_MAX_TEMP = 1;
    public  final int INDEX_WEATHER_MIN_TEMP = 2;
    public  final int INDEX_WEATHER_CONDITION_ID = 3;
    private  final int ID_FORECAST_LOADER = 44;
    private  final int ID_IMG_LOADER = 72;

    public  final String[] MAIN_FORECAST_PROJECTION = {
            ImgContract.WeatherEntry.TRIP_NAME ,
            ImgContract.WeatherEntry.IMG_ADDR,
            ImgContract.WeatherEntry.SIGHT_NAME,

    };
    public  final String[] NAMES_FORECAST_PROJECTION = {
            ImgContract.WeatherEntry.TRIP_NAME ,
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

        /* setLayoutManager associates the LayoutManager we created above with our RecyclerView */

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(layoutManager);
tvm=findViewById(R.id.trip);
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        /*
         * The ForecastAdapter is responsible for linking our weather data with the Views that
         * will end up displaying our weather data.
         *
         * Although passing in "this" twice may seem strange, it is actually a sign of separation
         * of concerns, which is best programming practice. The ForecastAdapter requires an
         * Android Context (which all Activities are) as well as an onClickHandler. Since our
         * MainActivity implements the ForecastAdapter ForecastOnClickHandler interface, "this"
         * is also an instance of that type of handler.
         */
        myAdapter = new MyAdapter(this, this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(myAdapter);

       // getSupportLoaderManager().initLoader(ID_FORECAST_LOADER, null, this);
       // ImgViewModel imgViewModel=ViewModelProviders.of(this).get(ImgViewModel.class);
/*        if(imgViewModel.getAllQuestions()!=null) mDb.wordDao().deleteAll();*/
        fetchData();

    //    mDb=ImgsRoomDB.getDatabase(getApplicationContext());
        ImgViewModel imgViewModel=ViewModelProviders.of(this).get(ImgViewModel.class);
      //  if(imgViewModel.getAllQuestions()!=null) mDb.wordDao().deleteAll();

            imgViewModel.getAllQuestions().observe(this, new Observer<List<Imgs>>() {
            @Override
            public void onChanged(@Nullable List<Imgs> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                myAdapter.setData(taskEntries);
              if(taskEntries.size()>0)  tvm.setText(taskEntries.get(0).getTrip());
            }
        });
    //    tvm.setText(questionsModelList.get(0).getTrip());


     //   SunshineSyncUtils.startImmediateSync(this);

    }

    @Override
    public void onClick(String pos) {

    }
/*

    private List<Imgs> subscribeToModel(String sight,int i) {
        //.  final ImgsRoomDB databaseCreator = ImgsRoomDB.getInstance();
        LiveData<List<Imgs>> itemLiveData = mDb.wordDao().getImgsForSight(sight);
        if(itemLiveData != null) {
            itemLiveData.observe(this, new Observer<List<Imgs>>() {
                @Override
                public void onChanged(@Nullable List<Imgs> items) {
                   myAdapter.
                    // Do something useful
                }
            });
        }
        return null;
    }
*/




    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {


        switch (id) {

            case ID_FORECAST_LOADER:
                /* URI for all rows of weather data in our weather table */
                Uri forecastQueryUri = ImgContract.WeatherEntry.CONTENT_URI;
                /* Sort order: Ascending by date */
                String sortOrder = ImgContract.WeatherEntry.SIGHT_NAME + " ASC";
                /*
                 * A SELECTION in SQL declares which rows you'd like to return. In our case, we
                 * want all weather data from today onwards that is stored in our weather table.
                 * We created a handy method to do that in our WeatherEntry class.
                 */
            //    String selection = ImgContract.WeatherEntry.getSqlSelectForTodayOnwards();

                return new CursorLoader(this,
                        ImgContract.WeatherEntry.CONTENT_URI_dis,
                        MAIN_FORECAST_PROJECTION,
                        null,
                        null,
                        sortOrder);
//////*
//
//
//
//
//
// String selection = "(" + column1 + " NOT NULL) GROUP BY (" + column1 + ")";
//
// */
            case ID_IMG_LOADER:
                /* URI for all rows of weather data in our weather table */
                Uri forecastQueryUri1 = ImgContract.WeatherEntry.buildWeatherUriWithSight("sight");//todo: add sight
                /* Sort order: Ascending by date */
                String sortOrder1 = ImgContract.WeatherEntry.SIGHT_NAME + " ASC";
                /*
                 * A SELECTION in SQL declares which rows you'd like to return. In our case, we
                 * want all weather data from today onwards that is stored in our weather table.
                 * We created a handy method to do that in our WeatherEntry class.
                 */
                //    String selection = ImgContract.WeatherEntry.getSqlSelectForTodayOnwards();

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

    /**
     * Called when a previously created loader is being reset, and thus making its data unavailable.
     * The application should at this point remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        /*
         * Since this Loader's data is now invalid, we need to clear the Adapter that is
         * displaying the data.
         */
        myAdapter.swapCursor(null);

    }




    private void fetchData(){
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
        Call<List<ImgModel>> call = service.getQuestions();

        call.enqueue(new Callback<List<ImgModel>>() {
            @Override
            public void onResponse(Call<List<ImgModel>> call, Response<List<ImgModel>> response) {
                Log.d(TAG+"fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel"+"5");

                if (response.isSuccessful()){
                    Log.d(TAG+"fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel"+"7");

                    if (response != null){
                        List<ImgModel> questionsModelList = response.body();
                        if(questionsModelList.size()>0) tvm.setText(questionsModelList.get(0).getTrip());
                        for (int i = 0; i < questionsModelList.size(); i++){
                           // String addr = questionsModelList.get(i).getImgaddr();
                            String sightname = questionsModelList.get(i).getSight();
                            String trip = questionsModelList.get(i).getTrip();
                         //   List<String> listaaddr= questionsModelList.get(i).getAddr();
                            ArrayList<String> p= new ArrayList(response.body().get(i).getAddr());
                            Log.d(TAG+"fddffdfdfdfd", "Updating list of tasks from LiveData in ViewModel"+p);

                            Imgs questions = new Imgs(trip,"", sightname,p  );

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


/*
    public void takeAction(){
        relativeLayout.setVisibility(View.INVISIBLE);
        currentQ=quesList.get(qid);
        txtQuestion=(TextView)findViewById(R.id.textView1);
        rda=(RadioButton)findViewById(R.id.radio0);
        rdb=(RadioButton)findViewById(R.id.radio1);
        rdc=(RadioButton)findViewById(R.id.radio2);
        butNext=(Button)findViewById(R.id.button1);
        setQuestionView();
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup grp=(RadioGroup)findViewById(R.id.radioGroup1);

                if (grp.getCheckedRadioButtonId() == -1){
                    return;
                }

                RadioButton answer=(RadioButton)findViewById(grp.getCheckedRadioButtonId());

                grp.clearCheck();
                //Log.d("yourans", currentQ.getANSWER()+" "+answer.getText());

                if(currentQ.getAnswer().equals(answer.getText()))
                {
                    score++;
                    Log.d("score", "Your score"+score);
                }
                if(qid<5){
                    currentQ=quesList.get(qid);
                    setQuestionView();
                }else{
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("score", score); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
*/


}
