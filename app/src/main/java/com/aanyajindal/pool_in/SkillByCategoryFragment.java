package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SkillByCategoryFragment extends Fragment {


    public SkillByCategoryFragment() {
        // Required empty public constructor
    }


    public static SkillByCategoryFragment newInstance(String skillCat) {

        Bundle args = new Bundle();

        SkillByCategoryFragment fragment = new SkillByCategoryFragment();
        args.putString("skillCatKey", skillCat);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skill_by_category, container, false);
    }

}
