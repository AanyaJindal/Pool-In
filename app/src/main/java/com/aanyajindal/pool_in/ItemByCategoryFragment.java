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
public class ItemByCategoryFragment extends Fragment {
    ArrayList<Item> list;
    ItemAdapter itemAdapter;
    ListView listView;

    private static final String TAG = "ItemByCategoryFragment";

    public ItemByCategoryFragment() {
        // Required empty public constructor
    }

    public static ItemByCategoryFragment newInstance(String cat) {

        Bundle args = new Bundle();
        args.putString("catKey", cat);
        ItemByCategoryFragment fragment = new ItemByCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_item_by_category, container, false);
        String category = getArguments().getString("catKey");

        list = new ArrayList<>();

        listView = (ListView) rootView.findViewById(R.id.lv_itemByCategory);

        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference().child("items");
        Query queryRef = itemsRef.orderByChild("cat").equalTo(category);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                Item item = dataSnapshot.getValue(Item.class);
                Log.d(TAG, "onChildAdded: " + item.getName());
                list.add(new Item(item.getName(), item.getUser(), item.getDesc(), item.getMode(), item.getCat(), item.getTags(), item.getDate()));
                Log.d(TAG, "onCreateView: " + list.size());
                itemAdapter = new ItemAdapter(list);
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

    private class ItemAdapter extends BaseAdapter {
        class Holder {
            TextView name;
            TextView user;
            TextView date;
            TextView tags;
        }

        private ArrayList<Item> mList;

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
            Holder holder = new Holder();
            if (convertView == null) {
                convertView = li.inflate(R.layout.item_list, null);

                holder.name = (TextView) convertView.findViewById(R.id.item_name);
                holder.user = (TextView) convertView.findViewById(R.id.item_owner);
                holder.date = (TextView) convertView.findViewById(R.id.item_date);
                holder.tags = (TextView) convertView.findViewById(R.id.item_tags);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
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
            }
            catch(ParseException pe) {
                pe.printStackTrace();
            }
            holder.date.setText(frDate);
            holder.user.setText(item.getUser());
            return convertView;
        }

    }
}