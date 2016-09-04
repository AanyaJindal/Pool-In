package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostByCategoryFragment extends Fragment {


    public PostByCategoryFragment() {
        // Required empty public constructor
    }

    public static PostByCategoryFragment newInstance(String str) {

        Bundle args = new Bundle();
        args.putString("postKey", str);
        PostByCategoryFragment fragment = new PostByCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_by_category, container, false);
    }

}
