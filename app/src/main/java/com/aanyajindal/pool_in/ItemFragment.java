package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ItemFragment extends Fragment {


    public ItemFragment() {

    }

    public static ItemFragment newInstance(Item item) {
        
        Bundle args = new Bundle();
        args.putString("name",item.getName());
        args.putString("date",item.getDate());
        args.putString("desc",item.getDesc());
        args.putString("tags",item.getTags());
        args.putString("user",item.getUser());
        args.putString("mode",item.getMode());
        args.putString("cat",item.getCat());
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_item, container, false);
        Bundle bundle = getArguments();
        final Item obj = new Item(bundle.getString("name"),bundle.getString("user"),bundle.getString("desc")
        ,bundle.getString("mode"),bundle.getString("cat"),bundle.getString("tags"),bundle.getString("date"));

        TextView itemName = (TextView)rootView.findViewById(R.id.item_name_value);
        final TextView itemUser = (TextView)rootView.findViewById(R.id.item_user_value);
        final TextView itemDesc = (TextView)rootView.findViewById(R.id.item_desc_value);
        TextView itemMode = (TextView)rootView.findViewById(R.id.item_mode_value);
        TextView itemCategory = (TextView)rootView.findViewById(R.id.item_category_value);
        TextView itemTags = (TextView) rootView.findViewById(R.id.item_tags_value);
        TextView itemDate = (TextView) rootView.findViewById(R.id.item_date_value);


        itemName.setText(obj.getName());
        itemUser.setText(obj.getUser());
        itemDesc.setText(obj.getDesc());
        itemMode.setText(obj.getMode());
        itemCategory.setText(obj.getCat());
        itemTags.setText(obj.getTags());

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("EEE, MMM d, ''yy");
        String frDate = "";
        try {
            Date date = fmt.parse(obj.getDate().toString());
            frDate = fmt2.format(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        itemDate.setText(frDate);

        itemUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = UserProfileFragment.newInstance(obj.getUser());
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

}
