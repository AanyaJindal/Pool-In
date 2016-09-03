package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Item;
import com.aanyajindal.pool_in.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SkillByCategoryFragment extends Fragment {

    User user;
    private static final String TAG = "SkillByCategoryFragment";

    public SkillByCategoryFragment() {
        // Required empty public constructor
    }

    ListView listView;
    ArrayList<String> list;


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
        View rootView = inflater.inflate(R.layout.fragment_skill_by_category, container, false);

        String category = getArguments().getString("skillCatKey");

        listView = (ListView) rootView.findViewById(R.id.lv_skillPeople);

        list = new ArrayList<>();

        DatabaseReference skillsRef = FirebaseDatabase.getInstance().getReference().child("skills");
        DatabaseReference queryRef = skillsRef.child(category);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                list.add(dataSnapshot.getKey());
                Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());
//                User user = dataSnapshot.getValue(User.class);
//                list.add(user);
//                UserAdapter itemAdapter = new UserAdapter(list);
//                listView.setAdapter(itemAdapter);
                UserAdapter userAdapter = new UserAdapter(list);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    private class UserAdapter extends BaseAdapter {
        class Holder {
            TextView name;
            TextView college;
        }

        ArrayList<String> mList;

        public UserAdapter(ArrayList<String> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
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
                convertView = li.inflate(R.layout.user_list, null);

                holder.name = (TextView) convertView.findViewById(R.id.skill_list_user);
                holder.college = (TextView) convertView.findViewById(R.id.skill_list_college);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            String userid = getItem(position);
            DatabaseReference userList = FirebaseDatabase.getInstance().getReference().child("users");
            userList.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            holder.name.setText(user.getName());
            holder.college.setText(user.getCollege());

            return convertView;
        }
    }

}
