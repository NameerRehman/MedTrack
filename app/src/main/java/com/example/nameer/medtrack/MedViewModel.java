package com.example.nameer.medtrack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

//This class links the repository to MainActivity -
// Is used so backend does not have to be typed in UI layer (MainActivity)
// Provides methods for accessing the data layer, & returns LiveData so MainActivity can set up the observer relationship

public class MedViewModel extends AndroidViewModel{
    private MedRepository mRepository; //variable to hold a reference to the repository
    private LiveData<List<MedItem>> mAllMeds; //LiveData variable to cache medList
    private LiveData<List<String>> mConditions;
    private LiveData<List<MedItem>> mMedsByCondition;


    //get access to data from repo (i.e getAllMeds) - (note: not the usual this. constructor)
    public MedViewModel(Application application) {
        super(application);
        mRepository = new MedRepository(application);
        mAllMeds = mRepository.getMedsByStartDate(); //returns mAllMeds from repo - db.medDao().getMedListByDate;
        mConditions = mRepository.getConditions();
    }

    //Med queries
    public void insert(MedItem medItem) {
        mRepository.insert(medItem);
    }
    public void delete(int id){ mRepository.delete(id); }
    public void update(String medName, String startDate, String endDate, String condition, String notes, int id){
        mRepository.update(medName, startDate, endDate, condition, notes, id); }

    //return LiveData mAllMeds (called in MainActivity to setup observer relationship)
    LiveData<List<MedItem>> getMedsByStartDate() {
        return mAllMeds;
    }
    LiveData<List<String>> getConditions() { return mConditions; }
    LiveData<List<MedItem>> getMedsByConditionDateAdded(String condition) {return mRepository.getMedsByConditionDateAdded(condition);}

    LiveData<List<MedItem>> getMedsByDateAdded() {return mRepository.getMedsByDateAdded();}
    LiveData<List<MedItem>> getMedsByAlphabetical() {return mRepository.getMedsByAlphabetical();}
    LiveData<List<MedItem>> getMedsByOngoingDA(String endDate) {return mRepository.getMedsByOngoingDA(endDate);}
    LiveData<List<MedItem>> getMedsByOngoingStart(String endDate) {return mRepository.getMedsByOngoingStart(endDate);}
    LiveData<List<MedItem>> getMedsByOngoingAlphabetical(String endDate) {return mRepository.getMedsByOngoingAlphabetical(endDate);}

    LiveData<List<MedItem>> getMedsByConditionAlphabetical(String condition) {return mRepository.getMedsByConditionAlphabetical(condition);}
    LiveData<List<MedItem>> getMedsByConditionStartDate(String condition) {return mRepository.getMedsByConditionStartDate(condition);}
    LiveData<List<MedItem>> getMedsByConditionOngoingDA(String condition, String endDate) {return mRepository.getMedsByConditionOngoingDA(condition, endDate);}
    LiveData<List<MedItem>> getMedsByConditionOngoingStart(String condition, String endDate) {return mRepository.getMedsByConditionOngoingStart(condition, endDate);}
    LiveData<List<MedItem>> getMedsByConditionOngoingAlphabetical(String condition, String endDate) {return mRepository.getMedsByConditionOngoingAlphabetical(condition, endDate);}



    //Calendar queries
    public void insertCal(CalendarEvent calendarEvent) { mRepository.insertCal(calendarEvent); }
    public void deleteCal (long date){ mRepository.deleteCal(date); }


    LiveData<CalendarEvent> getEvents(long date) {return mRepository.getEvents(date);}
    LiveData<List<CalendarEvent>> getallEvents(){return mRepository.getallEvents();}



}
