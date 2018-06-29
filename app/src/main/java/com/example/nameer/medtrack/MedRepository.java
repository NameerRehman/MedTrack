package com.example.nameer.medtrack;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

//this class manages one or more databases - in this case, AppDatabase. Sends data to MedViewModel
//so that LiveData can be sent to MainActivity
public class MedRepository {

    private MedDao mMedDao;
    private LiveData<List<MedItem>> mAllMeds;

    //get access to data from AppDatabase
    MedRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mMedDao = db.medDao();
        mAllMeds = mMedDao.getMedListByDate(); //calls database query (with the help of MedDao class) to get Meds orderd by StartDate
    }

    //getter to be accessed by MedViewModel and then MainActivity to observe LiveData
    LiveData<List<MedItem>> getAllMeds(){
        return mAllMeds;
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
