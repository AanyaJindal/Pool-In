package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Item;
import com.aanyajindal.pool_in.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class MyItemsFragment extends Fragment {

    ArrayList<Item> list;
    ArrayList<String> ids;

    FirebaseUser user;
    ListView listView;

    public static final String TAG = "MyItemsFragment";


    public MyItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_items, container, false);

        list = new ArrayList<>();
        ids = new ArrayList<>();
        listView = (ListView) rootView.findViewById(R.id.lv_myItems);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onCreateView: "+user.getDisplayName());

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("items");

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                Log.d(TAG, "onChildAdded: "+ dataSnapshot.getKey());
                String itemID = dataSnapshot.getKey();
                ids.add(itemID);

                DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("items").child(itemID);
                itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Item item = dataSnapshot.getValue(Item.class);
                        list.add(item);
                        MyItemsFragment.ItemAdapter itemAdapter = new MyItemsFragment.ItemAdapter(list);
                        listView.setAdapter(itemAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = ItemFragment.newInstance(ids.get(position), list.get(position));
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

    class ItemAdapter extends BaseAdapter {
        class Holder {
            TextView name;
            TextView user;
            TextView date;
            TextView tags;
        }

        ArrayList<Item> mList;

        public ItemAdapter(ArrayList<Item> mList) {
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
            MyItemsFragment.ItemAdapter.Holder holder = new MyItemsFragment.ItemAdapter.Holder();
            if (convertView == null) {
                convertView = li.inflate(R.layout.item_list, null);

                holder.name = (TextView) convertView.findViewById(R.id.item_name);
                holder.user = (TextView) convertView.findViewById(R.id.item_owner);
                holder.date = (TextView) convertView.findViewById(R.id.item_date);
                holder.tags = (TextView) convertView.findViewById(R.id.item_tags);
                convertView.setTag(holder);
            } else {
                holder = (MyItemsFragment.ItemAdapter.Holder) convertView.getTag();
            }

            Item item = (Item) getItem(position);
            holder.name.setText(item.getName());
            holder.tags.setText(item.getTags());

            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat fmt2 = new SimpleDateFormat("EEE, MMM d, ''yy");
            String frDate = "";
            try {
                Date date = fmt.parse(item.getDate().toString());
                frDate = fmt2.format(date);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            holder.date.setText(frDate);
            holder.user.setText((item.getUsername()));

//            DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("users").child(item.getUser());
//            final Holder finalHolder = holder;
//            temp.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    user = dataSnapshot.getValue(User.class);
//                    finalHolder.user.setText(user.getName());
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

            return convertView;
        }

    }

}
