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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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
        View rootView = inflater.inflate(R.layout.fragment_item_category, container, false);
        ListView lvItemCatg = (ListView) rootView.findViewById(R.id.lv_itemCategory);
        final ArrayList<String> itCatList = new ArrayList<>();
        itCatList.add("Books");
        itCatList.add("Notes");
        itCatList.add("Online Resources");
        itCatList.add("Stationery");
        itCatList.add("Car Pooling");
        itCatList.add("Others");

        ItemCategoryFragment.ItCatAdapter itCatAdapter = new ItemCategoryFragment.ItCatAdapter(itCatList);
        lvItemCatg.setAdapter(itCatAdapter);
        lvItemCatg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = ItemByCategoryFragment.newInstance(itCatList.get(position));
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.addToBackStack("item-cat");
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

    class ItCatAdapter extends BaseAdapter {
        class Holder {
            TextView name;
        }

        ArrayList<String> mList;

        public ItCatAdapter(ArrayList<String> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            Holder holder = new Holder();
            if (convertView == null) {
                convertView = li.inflate(R.layout.list_item_category, null);

                holder.name = (TextView) convertView.findViewById(R.id.tv_it_cat);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.name.setText(mList.get(position).toString());

            return convertView;
        }

    }

}
