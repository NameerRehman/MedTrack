package com.nrinfinity.nameer.medtrack;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {MedItem.class, CalendarEvent.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedDao medDao();
    public abstract CalDao calDao();

    private static AppDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    //prevent having multiple instances of the database opened at the same time (as only one is usually required)
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    //create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "med_database")
                            //.addMigrations(MIGRATION_1_2)
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final MedDao medDao;
        private final CalDao calDao;

        PopulateDbAsync(AppDatabase db){
            medDao = db.medDao();
            calDao = db.calDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            return null;

        }
    }


}
