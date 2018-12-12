package com.example.android.myappimgs;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.myappimgs.dataRoom.Imgs;
import com.example.android.myappimgs.dataRoom.ImgsRepository;

import java.util.List;

public class ImgViewModel extends AndroidViewModel {

    private static ImgsRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private static LiveData<List<Imgs>> mAllQuestions;

    public ImgViewModel (Application application) {
        super(application);
        mRepository = new ImgsRepository(application);
        mAllQuestions = mRepository.getmAllQuestions();
    }
    public static void delAll(){mRepository.DeleteAll();}
    static LiveData<List<Imgs>> getAllQuestions() { return mAllQuestions; }

    public static void insert(Imgs word) { mRepository.insert(word); }
}