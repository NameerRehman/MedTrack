package com.example.nameer.medtrack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MedViewModel extends AndroidViewModel{
    private MedRepository mRepository; //variable to hold a reference to repository
    private LiveData<List<MedItem>> mMedList; //LiveData variable to cache medList

    //constructor to get a reference to repo and medList from repo
    public MedViewModel(Application application) {
        super(application);
        mRepository = new MedRepository(application);
        mMedList = mRepository.getMedList();
    }

    LiveData<List<MedItem>> getMedList() {return mMedList;}

    public void insert(MedItem medItem) {mRepository.insert(medItem);}
}
