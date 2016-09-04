package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aanyajindal.pool_in.R;
import com.aanyajindal.pool_in.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class UserProfileFragment extends Fragment {

    User obj;
    TextView userLocation;
    TextView userEmail;
    TextView userCollege;
    TextView userName;
    Button contactMe;



    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance(String userid) {

        Bundle args = new Bundle();

        args.putString("id", userid);

        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private static final String TAG = "UserProfileFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        String userid = getArguments().getString("id");
        Log.d(TAG, "onCreateView: "+userid);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        Log.d(TAG, "onCreateView: here");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: here");
                Log.d(TAG, "onDataChange: "+dataSnapshot.getValue());
                obj = dataSnapshot.getValue(User.class);
                userName.setText(obj.getName());
                userEmail.setText(obj.getEmail());
                userCollege.setText(obj.getCollege());
                userLocation.setText(obj.getLocation());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        userName = (TextView) rootView.findViewById(R.id.user_name_value);
        userEmail = (TextView) rootView.findViewById(R.id.user_email_value);
        userCollege = (TextView) rootView.findViewById(R.id.user_college_value);
        userLocation = (TextView) rootView.findViewById(R.id.user_location_value);
        contactMe = (Button) rootView.findViewById(R.id.btn_contact_me);

        contactMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ListView userSkills = (ListView) rootView.findViewById(R.id.user_skills_listView);
        ListView userItems = (ListView) rootView.findViewById(R.id.user_items_listView);



        ArrayList<String> usSkillList = new ArrayList<>(Arrays.asList(" "));

        ArrayAdapter<String> usSkillAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_category, usSkillList);
        userSkills.setAdapter(usSkillAdapter);

        ArrayList<String> usItemlList = new ArrayList<>(Arrays.asList(" "));

        ArrayAdapter<String> usItemAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_category, usItemlList);
        userItems.setAdapter(usItemAdapter);

        return rootView;
    }

}
