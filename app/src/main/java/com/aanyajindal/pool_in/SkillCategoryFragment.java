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
public class SkillCategoryFragment extends Fragment {


    public SkillCategoryFragment() {
        // Required empty public constructor
    }

    public static SkillCategoryFragment newInstance() {

        Bundle args = new Bundle();

        SkillCategoryFragment fragment = new SkillCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_skill_category, container, false);
        ListView lvSkillCatg = (ListView) rootView.findViewById(R.id.lv_skillCategory);
        final ArrayList<String> skillCatList = new ArrayList<String>(
                Arrays.asList
                ("Android Development",
                "Competitive Coding",
                "HTML",
                "CSS",
                "Python",
                "Ruby",
                "Networking",
                "Data Structures",
                "Scala",
                "Perl",
                "Java",
                "C/C++",
                "PHP",
                "SQL Database",
                "NoSQL Database",
                "Artificial Intelligence",
                "Machine Learning",
                "Javascript"));


        ArrayAdapter<String> skillCatAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_category, skillCatList);
        lvSkillCatg.setAdapter(skillCatAdapter);

        lvSkillCatg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = SkillByCategoryFragment.newInstance(skillCatList.get(position));
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

}
