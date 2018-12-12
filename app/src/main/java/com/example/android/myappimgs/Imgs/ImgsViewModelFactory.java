/*
package com.example.android.myappimgs.Imgs;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.myappimgs.ImgViewModel;
import com.example.android.myappimgs.dataRoom.ImgsRepository;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;


public class ImgsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // COMPLETED (2) Add two member variables. One for the database and one for the taskId
    private final ImgsRoomDB mDb;
    private final String mTaskId;

    // COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    public ImgsViewModelFactory(ImgsRoomDB database, String taskId) {
        mDb = database;
        mTaskId = taskId;
    }

    // COMPLETED (4) Uncomment the following method
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ImgssssViewModel(mDb, mTaskId);
    }
}
*/
