package com.aanyajindal.pool_in;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aanyajindal.pool_in.models.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        Button btnAddItem = (Button) rootView.findViewById(R.id.btn_addItem);
//        Button btnAddPost = (Button) rootView.findViewById(R.id.btn_addPost);
//        btnAddItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), AddItem.class);
//                startActivity(intent);
//            }
//        });
//        btnAddPost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity().getApplicationContext(), PostActivity.class);
//                startActivity(intent);
//            }
//        });


        CardView cvAddItem, cvAddPost, cvMyItems, cvMyPosts;
        cvAddItem = (CardView) rootView.findViewById(R.id.cv_addItem);
        cvAddPost = (CardView) rootView.findViewById(R.id.cv_addPost);
        cvMyItems = (CardView) rootView.findViewById(R.id.cv_myItems);
        cvMyPosts = (CardView) rootView.findViewById(R.id.cv_myPosts);

        cvAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddItem.class);
                startActivity(intent);
            }
        });

        cvAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PostActivity.class);
                startActivity(intent);
            }
        });

        cvMyItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = MyFragment.newInstance(0);
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.addToBackStack("my-items");
                fragmentTransaction.commit();

            }
        });

        cvMyPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = MyFragment.newInstance(1);
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.addToBackStack("my-posts");
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

}
