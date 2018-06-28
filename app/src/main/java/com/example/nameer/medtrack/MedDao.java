package com.example.nameer.medtrack;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MedDao {
    @Query("SELECT * FROM med_table")
    List<MedItem> getAllMedItems();

    @Insert
    void insertAll(MedItem...medItems);
}
