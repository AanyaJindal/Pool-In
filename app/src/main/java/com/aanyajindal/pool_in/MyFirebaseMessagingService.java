package com.aanyajindal.pool_in;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class MyFirebaseMessagingService extends IntentService {

    private static final String TAG = "MyFirebaseMessaging";
    public MyFirebaseMessagingService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
