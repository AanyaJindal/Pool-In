package com.aanyajindal.pool_in;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.aanyajindal.pool_in.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseInstanceIdSer";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference userTokenRef = FirebaseDatabase.getInstance().getReference().child("user-tokens");

    public MyFirebaseInstanceIdService() {
    }


    @Override
    public void onTokenRefresh() {


        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "FCM Registration Token: " + token);
        sendRegistrationToServer(token);
        //Intent intent = new Intent(this, MyFirebaseMessagingService.class);
        //startService(intent);
    }


    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        //add to firebase


        try {
            String userId = user.getUid();
            userTokenRef.child(userId).setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }catch (NullPointerException e){

        }


    }




}
