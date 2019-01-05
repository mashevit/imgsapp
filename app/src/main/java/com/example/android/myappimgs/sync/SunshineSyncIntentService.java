package com.example.android.myappimgs.sync;

import android.app.IntentService;
import android.content.Intent;



import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.android.myappimgs.Main2Activity;
import com.example.android.myappimgs.MainActivity;
import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */

public class SunshineSyncIntentService extends IntentService {


    public static final String EXTRA_DATA_ID="ljjhjhqq";

    ImgsRoomDB mdb= ImgsRoomDB.getDatabase(this);
    public SunshineSyncIntentService() {
        super("SunshineSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String todohere="";//="syncdish";
      ///./  AppDB mdb= AppDB.getInstance(this);

        if (intent != null && intent.hasExtra(EXTRA_DATA_ID)) {
            todohere = intent.getStringExtra(EXTRA_DATA_ID);
        }
        if(todohere.equals("clean")){
        Log.d("taggg6g12","ssaa cc" );
        cleanclass.clean(mdb);todohere="";}

        else{List<Imgs> ans= cleanclass.ret(mdb);
            Main2Activity.topass= ans;
      //  MainActivity.myAdapter.setData(ans);
            Toast.makeText(this,"Passed Data to views",Toast.LENGTH_LONG);}
    }}

