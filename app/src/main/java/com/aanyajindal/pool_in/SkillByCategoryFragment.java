package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class SkillByCategoryFragment extends Fragment {


    public SkillByCategoryFragment() {
        // Required empty public constructor
    }

    ListView listView;
    ArrayList<User> list;


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

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        Query queryRef = usersRef.child("skills").orderByChild(category).equalTo(true);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());
                User user = dataSnapshot.getValue(User.class);
                list.add(user);
                UserAdapter itemAdapter = new UserAdapter(list);
                listView.setAdapter(itemAdapter);
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

    private class UserAdapter extends BaseAdapter{
        class Holder{
            TextView name;
            TextView college;
        }
        ArrayList<User> mList;

        public UserAdapter(ArrayList<User> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public User getItem(int position) {
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

            User user =  getItem(position);
            holder.name.setText(user.getName());
            holder.college.setText(user.getCollege());

            return convertView;
        }
    }

}
