package com.totnghiepluon.duancrm.Base;

import android.app.Application;

import com.totnghiepluon.duancrm.data.DatabaseHelper;

public class StartApplication extends Application {
    private static DatabaseHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setDb(DatabaseHelper databaseHelper) {
        db = databaseHelper;
    }

    public static DatabaseHelper getDb() {
        return db;
    }
}
