package com.example.nameer.medtrack;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.Date;
import java.util.List;

//this class manages one or more databases - in this case, AppDatabase. Sends data to MedViewModel
//so that LiveData can be sent to MainActivity
public class MedRepository {

    private MedDao mMedDao;
    private CalDao mCalDao;
    private LiveData<List<MedItem>> mAllMeds;
    private LiveData<List<String>> mConditions;
    private LiveData<List<MedItem>> mMedsByCondition;

    //get access to data from AppDatabase
    MedRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mMedDao = db.medDao();
        mCalDao = db.calDao();
        mAllMeds = mMedDao.getMedsByStartDate(); //calls database query (with the help of MedDao class) to get Meds orderd by StartDate
        mConditions = mMedDao.getConditions();
    }

    public void insert (MedItem medItem){
        new insertAsyncTask(mMedDao).execute(medItem);
    }
    public void delete (int id){ new deleteAsyncTask(mMedDao).execute(id); }
    public void update (String medName, String startDate, String endDate, String condition, String notes, int id){
        new editAsyncTask(mMedDao, medName, startDate, endDate, condition, notes).execute(id); }

    //getter to be accessed by MedViewModel and then MainActivity to observe LiveData
    LiveData<List<MedItem>> getMedsByStartDate(){return mAllMeds;} //returns mMedDao.getMedsListbyStartDate
    LiveData<List<MedItem>> getMedsByConditionDateAdded(String condition) {return mMedDao.getMedsByConditionDateAdded(condition);}
    LiveData<List<String>> getConditions() { return mConditions; }

    LiveData<List<MedItem>> getMedsByDateAdded() {return mMedDao.getMedsByDateAdded();}
    LiveData<List<MedItem>> getMedsByAlphabetical() {return mMedDao.getMedsByAlphabetical();}
    LiveData<List<MedItem>> getMedsByOngoingDA(String endDate) {return mMedDao.getMedsByOngoingDA(endDate);}
    LiveData<List<MedItem>> getMedsByOngoingStart(String endDate) {return mMedDao.getMedsByOngoingStart(endDate);}
    LiveData<List<MedItem>> getMedsByOngoingAlphabetical(String endDate) {return mMedDao.getMedsByOngoingAlphabetical(endDate);}

    LiveData<List<MedItem>> getMedsByConditionAlphabetical(String condition) {return mMedDao.getMedsByConditionAlphabetical(condition);}
    LiveData<List<MedItem>> getMedsByConditionStartDate(String condition) {return mMedDao.getMedsByConditionStartDate(condition);}
    LiveData<List<MedItem>> getMedsByConditionOngoingDA(String condition, String endDate) {return mMedDao.getMedsByConditionOngoingDA(condition, endDate);}
    LiveData<List<MedItem>> getMedsByConditionOngoingStart(String condition, String endDate) {return mMedDao.getMedsByConditionOngoingStart(condition, endDate);}
    LiveData<List<MedItem>> getMedsByConditionOngoingAlphabetical(String condition, String endDate) {return mMedDao.getMedsByConditionOngoingAlphabetical(condition, endDate);}


    //Calendar queries
    public void insertCal (CalendarEvent calendarEvent){
        new insertAsyncTaskCal(mCalDao).execute(calendarEvent);
    }
    LiveData<CalendarEvent> getEvents(long date) {return mCalDao.getEvents(date);}
    LiveData<List<CalendarEvent>> getallEvents(){return mCalDao.getallEvents();}


    //Med AsyncTasks
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

    //Calendar AsyncTasks
    private static class insertAsyncTaskCal extends AsyncTask<CalendarEvent, Void, Void> {
        private CalDao mAsyncTaskDao;

        insertAsyncTaskCal(CalDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CalendarEvent... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
