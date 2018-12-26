package com.example.android.myappimgs.sync;

import android.app.IntentService;
import android.content.Intent;



import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.android.myappimgs.dataRoom.ImgsRoomDB;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */

public class SunshineSyncIntentService extends IntentService {
    ImgsRoomDB mdb= ImgsRoomDB.getDatabase(this);
    public SunshineSyncIntentService() {
        super("SunshineSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("taggg6g12","ssaa cc" );
        cleanclass.clean(mdb);
    }
}
