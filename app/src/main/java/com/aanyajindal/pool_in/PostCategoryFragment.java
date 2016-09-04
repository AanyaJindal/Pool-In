package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostCategoryFragment extends Fragment {


    public PostCategoryFragment() {
        // Required empty public constructor
    }

    public static PostCategoryFragment newInstance() {

        Bundle args = new Bundle();

        PostCategoryFragment fragment = new PostCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_post_category, container, false);
        ListView lvItemCatg = (ListView) rootView.findViewById(R.id.lv_itemCategory);
        final ArrayList<String> postCatList = new ArrayList<>(Arrays.asList("Projects",
                "Placement",
                "Lost and Found",
                "Mentorship",
                "Blood Donation",
                "Complaints",
                "Fest Participation",
                "Campus Life"));
        ArrayAdapter<String> postCatAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_category, postCatList);
        lvItemCatg.setAdapter(postCatAdapter);
        lvItemCatg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = PostByCategoryFragment.newInstance(postCatList.get(position));
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

}
