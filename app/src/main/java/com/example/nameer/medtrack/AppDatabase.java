package com.example.nameer.medtrack;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MedItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract  MedDao medDao();
}
