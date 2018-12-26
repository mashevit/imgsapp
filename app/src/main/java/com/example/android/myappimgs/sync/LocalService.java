package com.example.android.myappimgs.sync;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.android.myappimgs.dataRoom.ImgsRoomDB;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class LocalService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
//    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** method for clients */














    public static void cleanAll(ImgsRoomDB mDb){

        mDb.wordDao().deleteAll();
        mDb.wordDao().nukeTable();

    }

}