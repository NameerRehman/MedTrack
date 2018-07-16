package com.example.nameer.medtrack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

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
        mAllMeds = mRepository.getAllMeds(); //returns mAllMeds from repo - db.medDao().getMedListByDate;
        mConditions = mRepository.getConditions();
    }

    //return LiveData mAllMeds (called in MainActivity to setup observer relationship)
    LiveData<List<MedItem>> getAllMeds() {
        return mAllMeds;
    }
    LiveData<List<String>> getConditions() { return mConditions; }
    LiveData<List<MedItem>> getMedsByCondition(String condition) {return mRepository.getMedsByCondition(condition);}

    //Insert new data into repo - used in onClick of "add" buton in MainActivity
    public void insert(MedItem medItem) {
        mRepository.insert(medItem);
    }

    public void delete(int id){
        mRepository.delete(id);
    }

    public void update(String medName, String startDate, String endDate, String condition, String notes, int id){
        mRepository.update(medName, startDate, endDate, condition, notes, id);
    }
}
