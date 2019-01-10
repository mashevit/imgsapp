package com.example.android.myappimgs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsRepository;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;
import com.example.android.myappimgs.prefs.SettingsActivity;
import com.example.android.myappimgs.sync.SunshineSyncIntentService;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.RendererRecyclerViewAdapter;
import com.github.vivchar.rendererrecyclerviewadapter.binder.CompositeViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder;
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewProvider;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public static MyAdapter myAdapter;
    private TextView tvm;
    private ProgressBar mLoadingIndicator;
    public static List<Imgs> topass;
    private ImgsRoomDB mDb;
    RendererRecyclerViewAdapter adapter;
    private final String TAG = MainActivity.class.getSimpleName();
    // public static List<Imgs> topass;
    private int mPosition = RecyclerView.NO_POSITION;
    ImgsRepository imgsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvm = findViewById(R.id.trip);


        final RendererRecyclerViewAdapter adapter = new RendererRecyclerViewAdapter();

        adapter.registerRenderer(
                new CompositeViewBinder<>(
                        R.layout.mainrecyhold,
                        R.id.myrecy2,
                        DefaultCompositeViewModel.class,
                        (model, finder, payloads) -> finder.setText(R.id.Sight, ((ChildItem) (model.getItems().get(0))).getSight())
                        // Collections.singletonList(new BetweenSpacesItemDecoration(10, 10))
                ).registerRenderer(getAnyViewRenderer())
        );
//		adapter.registerRenderer(...);
//		adapter.registerRenderer(...);

        adapter.setItems(getParentItems());

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myrecy);
        recyclerView.setAdapter(adapter);

        SharedPreferences settings = android.preference.PreferenceManager.getDefaultSharedPreferences(Main2Activity.this);//getActivity().getApplicationContext().getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);
        String tripname = settings.getString(String.valueOf(R.string.PREFS_final_tripname), "null trip");
        tvm.setText(tripname);
        //  recyclerView.addItemDecoration(new BetweenSpacesItemDecoration(10, 10));

        //   return view;
    }

    private ViewBinder getAnyViewRenderer() {
        return new ViewBinder<>(
                R.layout.horizrecyhold,
                ChildItem.class,
                (model, finder, payloads) -> finder.find(R.id.imageView, (ViewProvider<ImageView>) imageView -> {
                    Glide.with(this).load(model.getTitle()).into(imageView);
                })
                        .setOnClickListener(R.id.imageView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent startActivity = new Intent(Main2Activity.this, FullscreenActivity.class);
                                startActivity.putExtra(FullscreenActivity.EXTRA_TASK_ID, model.getTitle());
                                startActivity(startActivity);
                            }
                        }));
    }


    public static List<DefaultCompositeViewModel> getParentItems() {
        ArrayList<DefaultCompositeViewModel> parents = new ArrayList<DefaultCompositeViewModel>();

        for (int i = 0; i < topass.size(); i++) {
            ArrayList<ChildItem> children = new ArrayList();
            Imgs qqw = topass.get(i);
            for (int h = 0; h < qqw.getListaaddr().size(); h++) {
                // ChildItem qwq=new ChildItem(qqw.getImgaddr());
                children.add(new ChildItem(qqw.getListaaddr().get(h), qqw.getSight()));
            }
            parents.add(new DefaultCompositeViewModel(children));
        }
        return parents;
    }


    //         tvm = findViewById(R.id.trip);
//
//
//        adapter = new RendererRecyclerViewAdapter();
//        adapter.registerRenderer(getParentItemViewBinder().registerRenderer(getChildItemViewBinder()));
//        adapter.setItems(getParentItems());
//
////        mRecyclerView = (RecyclerView) findViewById(R.id.myrecy);
////        mLoadingIndicator = (ProgressBar) findViewById(R.id.progressBar);
////        if(topass==null){
////            topass=new ArrayList<Imgs>();}
////        LinearLayoutManager layoutManager =
////                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
////       // userService = APIUtils.getUserService();
////
////        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
////        mRecyclerView.addItemDecoration(decoration);
////        mRecyclerView.setLayoutManager(layoutManager);
////        tvm = findViewById(R.id.trip);
////        mDb=ImgsRoomDB.getDatabase(this);
////        mRecyclerView.setHasFixedSize(false);
////
////        myAdapter = new MyAdapter(this, this);
////        myAdapter.setData(MainActivity.topass);
////        mRecyclerView.setAdapter(myAdapter);
//
//        // getSupportLoaderManager().initLoader(ID_FORECAST_LOADER, null, this);
//        //ImgViewModel imgViewModel=ViewModelProviders.of(this).get(ImgViewModel.class);
//        //      if(imgViewModel.getAllQuestions()!=null) mDb.wordDao().deleteAll();
//        SharedPreferences settings = android.preference.PreferenceManager.getDefaultSharedPreferences (Main2Activity.this);//getActivity().getApplicationContext().getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);
//        String tripname = settings.getString(String.valueOf(R.string.PREFS_final_tripname),"null trip");
////        tvm.setText(tripname);
//
//
//
//
//    }
//
//
//
//    private CompositeViewBinder getParentItemViewBinder() {
//        return new CompositeViewBinder<>(
//                R.layout.parent_item_layout,
//                R.id.recycler_view11,
//                DefaultCompositeViewModel.class /* imported from library */
//                //new ViewBinder.Binder(){...} /* if you need bind something else */
//        );
//    }
//
//
//
//    private List<DefaultCompositeViewModel> getParentItems() {
//        ArrayList<DefaultCompositeViewModel> parents = new ArrayList();
//
//        for (int i=0;i<5;i++) {
//            ArrayList<ChildItem> children = new ArrayList();
//            //Imgs qqw=topass.get(i);
//            for (int h=0;h<7;h++) {
//                // ChildItem qwq=new ChildItem(qqw.getImgaddr());
//                children.add(new ChildItem("kjkjjk"));
//            }
//            parents.add(new DefaultCompositeViewModel(children));
//        }
//        return parents;
//    }
//
//
//
//
//    private ViewBinder getChildItemViewBinder() {
////        return new ViewBinder<>(
////                        R.layout.horizrecyhold,
////                        ChildItem.class,
////                        (model, finder, payloads) -> finder {
////                    ImageView imageView = finder.find(R.id.imageView);
////        Glide.with(getContext()).load(model.getUrl()).into(imageView);
////                }
////        )
//
//        return new ViewBinder<>(
//                R.layout.horizrecyhold,
//                ChildItem.class,
//                (model, finder, payloads) -> finder
//                        //.setText(R.id.title, model.getTitle())
//                        .setText(R.id.info_text, model.getTitle())
//                        //.setImageBitmap(R.id.image, model.getImageBitmap())
//                        //.setOnClickListener(R.id.button, new OnClickListener() {...})
////                        .find(R.id.imageView, (ViewProvider<ImageView>) imageView -> {
////                            Glide.with(this).load(model.getTitle()).into(imageView);
////                        })
//        );
//    }
//
//
//
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
     * @return true if you handle the menu click here, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_update) {

            //    fetchtrips();


            return true;
//            SharedPreferences settings = getSharedPreferences(String.valueOf(R.string.PREFS_NAME), 0);
//
//            SharedPreferences.Editor editor = settings.edit();
//
//            editor.putStringSet(String.valueOf(R.string.PREFS_Arr_vals),);
        } else if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        } else if (id == R.id.action_fetch) {
            // fetchData();//getUsersList();//fetchData();
            adapter.setItems(getParentItems());

            return true;
        } else if (id == R.id.action_clear) {
            // showLoading();
            Intent intentToSyncImmediately = new Intent(Main2Activity.this, SunshineSyncIntentService.class);
            intentToSyncImmediately.putExtra(SunshineSyncIntentService.EXTRA_DATA_ID, "clean");
            // intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
            //startService(intentToSyncImmediately);
            Main2Activity.this.startService(intentToSyncImmediately);
            Log.d("taggg6g11", "ssaa cc");

//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
            return true;
        } else if (id == R.id.action_login) {
            //login();
            return true;
        } else if (id == R.id.showall) {
            Intent intentToSyncImmediately = new Intent(Main2Activity.this, SunshineSyncIntentService.class);
            intentToSyncImmediately.putExtra(SunshineSyncIntentService.EXTRA_DATA_ID, "fetchdb");
            // intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
            //startService(intentToSyncImmediately);
            Main2Activity.this.startService(intentToSyncImmediately);
            Log.d("taggg6g11", "ssaa cc");

            //new fetchAsyncTask(mDb.wordDao()).execute();


            Intent intentToSyncImmediately2 = new Intent(Main2Activity.this, Main2Activity.class);
            // intentToSyncImmediately.putExtra(SunshineSyncIntentService.EXTRA_DATA_ID,"fetchdb");
            // intent.putExtra(MyService.EXTRA_DATA_ID1, bool);
            //startService(intentToSyncImmediately);
            Main2Activity.this.startActivity(intentToSyncImmediately2);


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


//
//


}
