/*
package com.example.android.myappimgs.Imgs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsRepository;
import com.example.android.myappimgs.dataRoom.ImgsRoomDB;

import java.util.List;

// COMPLETED (5) Make this class extend ViewModel
public class ImgssssViewModel extends ViewModel {

    // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    private LiveData<List<Imgs>> task;

    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public ImgssssViewModel(ImgsRoomDB database, String taskId) {
        task = database.wordDao().getImgsForSight(taskId);
    }

    // COMPLETED (7) Create a getter for the task variable
    public LiveData<List<Imgs>> getDish() {
        return task;
    }
}

*/
