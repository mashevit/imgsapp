package com.example.android.myappimgs.dataRoom;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;


@Database(entities = {Imgs.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class ImgsRoomDB extends RoomDatabase{


    public abstract ImgsDao wordDao();

    private static ImgsRoomDB INSTANCE;

    public static ImgsRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ImgsRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ImgsRoomDB.class, "questions11")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };



}
