package com.example.nameer.medtrack;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

//this class manages one or more databases - in this case, AppDatabase. Sends data to MedViewModel
//so that LiveData can be sent to MainActivity
public class MedRepository {

    private MedDao mMedDao;
    private LiveData<List<MedItem>> mAllMeds;
    private LiveData<List<String>> mConditions;

    //get access to data from AppDatabase
    MedRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mMedDao = db.medDao();
        mAllMeds = mMedDao.getMedListByDate(); //calls database query (with the help of MedDao class) to get Meds orderd by StartDate
        mConditions = mMedDao.getConditions();
    }

    //getter to be accessed by MedViewModel and then MainActivity to observe LiveData
    LiveData<List<MedItem>> getAllMeds(){
        return mAllMeds;
    }
    LiveData<List<String>> getConditions() { return mConditions; }

    public void insert (MedItem medItem){
        new insertAsyncTask(mMedDao).execute(medItem);
    }
    public void delete (int id){ new deleteAsyncTask(mMedDao).execute(id); }
    public void update (String medName, String startDate, String endDate, String condition, String notes, int id){
        new editAsyncTask(mMedDao, medName, startDate, endDate, condition, notes).execute(id); }



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

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private MedDao mAsyncTaskDao;

        deleteAsyncTask(MedDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class editAsyncTask extends AsyncTask<Integer, Void, Void> {
        private MedDao mAsyncTaskDao;
        private String medName;
        private String startDate;
        private String endDate;
        private String condition;
        private String notes;

        editAsyncTask(MedDao dao, String medName, String startDate, String endDate, String condition, String notes) {
            mAsyncTaskDao = dao;
            this.medName = medName;
            this.startDate = startDate;
            this.endDate = endDate;
            this.condition = condition;
            this.notes  = notes;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.update(medName, startDate, endDate, condition, notes, params[0]);
            return null;
        }
    }

    /*private static class getConditionsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MedDao mAsyncTaskDao;

        getConditionsAsyncTask(MedDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void...params) {
            mAsyncTaskDao.getConditions();
            return null;
        }
    }*/
}
