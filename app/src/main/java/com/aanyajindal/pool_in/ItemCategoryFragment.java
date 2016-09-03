package com.aanyajindal.pool_in;


import android.content.Intent;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemCategoryFragment extends Fragment {


    public ItemCategoryFragment() {
        // Required empty public constructor
    }

    public static ItemCategoryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ItemCategoryFragment fragment = new ItemCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_item_category, container, false);
        ListView lvItemCatg = (ListView) rootView.findViewById(R.id.lv_itemCategory);
        final ArrayList<String> itCatList = new ArrayList<>();
        itCatList.add("Books");
        itCatList.add("Notes");
        itCatList.add("Online Resources");
        itCatList.add("Car Pooling");
        itCatList.add("Others");
        ArrayAdapter<String> itCatAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_category, itCatList);
        lvItemCatg.setAdapter(itCatAdapter);
        lvItemCatg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = PeopleByCategoryFragment.newInstance(itCatList.get(position));
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

}
