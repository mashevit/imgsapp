package com.example.android.myappimgs.dataRoom;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface  ImgsDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from imgs_table")
    LiveData<List<Imgs>> getAllQuestions();

    // We do not need a conflict strategy, because the word is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert
    void insert(Imgs questions);

    @Query("DELETE FROM IMGS_TABLE")
    void deleteAll();


    @Query("SELECT * FROM IMGS_TABLE WHERE sight LIKE :name")
    List<Imgs> sightByName(String name);
   /* @Query("SELECT * FROM IMGS_TABLE WHERE sight LIKE :name")
    LiveData<List<Imgs>> getImgsForSight(String name);
*/

}
