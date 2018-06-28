package com.example.nameer.medtrack;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MedDao {
    @Query("SELECT * FROM med_table ORDER BY start_date")
    List<MedItem> getAllMedItems();

    @Insert
    void insertAll(MedItem...medItems);
}
