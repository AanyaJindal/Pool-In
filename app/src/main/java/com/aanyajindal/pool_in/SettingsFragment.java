package com.aanyajindal.pool_in;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends Fragment {


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
        View rootView  = inflater.inflate(R.layout.fragment_settings, container, false);
        Button btnEditProfile = (Button) rootView.findViewById(R.id.btn_editProfile);
        Button btnChngPass = (Button) rootView.findViewById(R.id.btn_editProfile);
        Button btnLogout = (Button) rootView.findViewById(R.id.btn_logout);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AccountSettingsCategory.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

}
