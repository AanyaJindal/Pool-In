package com.aanyajindal.pool_in;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.aanyajindal.pool_in.models.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SettingsFragment extends Fragment {


    FirebaseUser user;
    User mUser;
    String flag;
    DatabaseReference userRef;
    private static final String TAG = "SettingsFragment";
    Switch swContactPublic;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {

        Bundle args = new Bundle();

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Button btnEditProfile = (Button) rootView.findViewById(R.id.btn_editProfile);
        Button btnChngPass = (Button) rootView.findViewById(R.id.btn_chPass);
        Button btnLogout = (Button) rootView.findViewById(R.id.btn_logout);
        swContactPublic = (Switch) rootView.findViewById(R.id.sw_contact_public);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              mUser = dataSnapshot.getValue(User.class);
                flag = mUser.getContactPublic();
                Log.d(TAG, "onDataChange: " + dataSnapshot);
                Log.d(TAG, "onDataChange: " + flag);
                if(flag.equals("false"))
                    swContactPublic.setChecked(false);
                else
                    swContactPublic.setChecked(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        swContactPublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    userRef.child("contactPublic").setValue("true");
                else
                    userRef.child("contactPublic").setValue("false");
            }
        });


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), AccountSettingsCategory.class);
//                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(getActivity().getApplicationContext(), StartActivity.class));
                            }
                        });
            }
        });
        btnChngPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = user.getEmail();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Recovery E-mail sent on registered E-mail ID", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        return rootView;
    }

}
