package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aanyajindal.pool_in.R;
import com.aanyajindal.pool_in.models.User;

public class UserProfileFragment extends Fragment {


    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        User obj = new User();


        TextView userName = (TextView)rootView.findViewById(R.id.user_name_value);
        TextView userEmail = (TextView)rootView.findViewById(R.id.user_email_value);
        TextView userCollege = (TextView)rootView.findViewById(R.id.user_college_value);
        TextView userLocation = (TextView)rootView.findViewById(R.id.user_location_value);

        ListView userSkills = (ListView)rootView.findViewById(R.id.user_skills_listView);
        ListView userItems = (ListView)rootView.findViewById(R.id.user_items_listView);

        userName.setText(obj.getName());
        userEmail.setText(obj.getEmail());
        userCollege.setText(obj.getCollege());
        userLocation.setText(obj.getLocation());
        return rootView;
    }

}
