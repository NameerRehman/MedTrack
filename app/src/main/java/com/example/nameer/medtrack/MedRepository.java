package com.example.nameer.medtrack;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MedRepository {

    private MedDao mMedDao;
    private LiveData<List<MedItem>> mMedList;

    MedRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mMedDao = db.medDao();
        mMedList = mMedDao.getMedList();
    }

    LiveData<List<MedItem>> getMedList(){
        return mMedList;
    }

    public void insert (MedItem medItem){
        new insertAsyncTask(mMedDao).execute(medItem);
    }

    private static class insertAsyncTask extends AsyncTask<MedItem, Void, Void> {

        private MedDao mAsyncTaskDao;

        insertAsyncTask(MedDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MedItem... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
