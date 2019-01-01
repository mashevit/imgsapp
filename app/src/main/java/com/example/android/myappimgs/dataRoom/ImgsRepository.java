package com.example.android.myappimgs.dataRoom;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.List;

public class ImgsRepository {

    private ImgsDao mQuestionsDao;
  //  private LiveData<List<Imgs>> mAllQuestions;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ImgsRepository(Application application) {
        ImgsRoomDB db = ImgsRoomDB.getDatabase(application);
        mQuestionsDao = db.wordDao();
     //   mAllQuestions = mQuestionsDao.getAllQuestions();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public  LiveData<List<Imgs>> getmAllQuestions() {
        return mQuestionsDao.getAllQuestions();
    }


    public  LiveData<List<Imgs>> getbysight(String sight) {
        return mQuestionsDao.getImgsForSight(sight);
    }
    public LiveData<List<Imgs>> getmAllbytrip(String name) {
        return mQuestionsDao.sightBytrip(name);
    }

    public LiveData<List<String>> getmAllSights() {
        return mQuestionsDao.allsights();
    }

    public void DeleteAll () {
        new delAsyncTask(mQuestionsDao).execute();
    }
    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Imgs word) {
        new insertAsyncTask(mQuestionsDao,word.getSight(),word.getTrip()).execute(word);
    }
 /*   public LiveData<List<Imgs>> getImgsForSight(String sight) {
        return mQuestionsDao.getImgsForSight(sight);
    }*/
    private static class insertAsyncTask extends AsyncTask<Imgs, Void, Void> {

        private ImgsDao mAsyncTaskDao;
        private String sightname;
     private String tripname;
        insertAsyncTask(ImgsDao dao,String name,String tn) {
            mAsyncTaskDao = dao;sightname=name;tripname=tn;
        }

        @Override
        protected Void doInBackground(final Imgs... params) {
            if (mAsyncTaskDao.sightByName(sightname).size()==0)
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class delAsyncTask extends AsyncTask<String, Void, Void> {

        private ImgsDao mAsyncTaskDao;

        delAsyncTask(ImgsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {

                mAsyncTaskDao.deleteAll();
            return null;
        }
    }

}
