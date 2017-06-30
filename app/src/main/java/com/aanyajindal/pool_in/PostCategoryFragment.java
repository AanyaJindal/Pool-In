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
        ListView lvPostCatg = (ListView) rootView.findViewById(R.id.lv_postCategory);
        final ArrayList<String> postCatList = new ArrayList<>(Arrays.asList("Projects",
                "Placement",
                "Lost and Found",
                "Mentorship",
                "Blood Donation",
                "Complaints",
                "Fest Participation",
                "Campus Life",
                "Others"));
        ItPostCatAdapter itPostCatAdapter = new ItPostCatAdapter(postCatList);
        lvPostCatg.setAdapter(itPostCatAdapter);

        lvPostCatg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = PostByCategoryFragment.newInstance(postCatList.get(position));
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.addToBackStack("post-cat");
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }

    class ItPostCatAdapter extends BaseAdapter {
        class Holder {
            TextView name;
        }

        ArrayList<String> mList;

        public ItPostCatAdapter(ArrayList<String> mList) {
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
                convertView = li.inflate(R.layout.list_post_category, null);

                holder.name = (TextView) convertView.findViewById(R.id.tv_it_post_cat);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.name.setText(mList.get(position).toString());

            return convertView;
        }

    }

}
