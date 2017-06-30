package com.aanyajindal.pool_in;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jatin on 30/6/17.
 */

public class StartPersistanceApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
