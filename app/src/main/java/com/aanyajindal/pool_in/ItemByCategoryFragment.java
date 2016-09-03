package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aanyajindal.pool_in.models.Item;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemByCategoryFragment extends Fragment {

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
        View rootView =  inflater.inflate(R.layout.fragment_item_by_category, container, false);
        String category = getArguments().getString("catKey");

        DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference().child("items");
        Query queryRef = itemsRef.orderByChild("cat").equalTo(category);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                System.out.println(dataSnapshot.getValue());
                Item item = dataSnapshot.getValue(Item.class);
                Log.d(TAG, "onChildAdded: "+item.getName()+" "+item.getDesc());
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

}
