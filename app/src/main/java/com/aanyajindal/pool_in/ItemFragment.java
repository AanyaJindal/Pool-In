package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Item;


public class ItemFragment extends Fragment {


    public ItemFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_item, container, false);
        Item obj = new Item();

        TextView itemName = (TextView)rootView.findViewById(R.id.item_name_value);
        TextView itemUser = (TextView)rootView.findViewById(R.id.item_user_value);
        TextView itemDesc = (TextView)rootView.findViewById(R.id.item_desc_value);
        TextView itemMode = (TextView)rootView.findViewById(R.id.item_mode_value);
        TextView itemCategory = (TextView)rootView.findViewById(R.id.item_category_value);

        itemName.setText(obj.getName());
        itemUser.setText(obj.getUser());
        itemDesc.setText(obj.getDesc());
        itemMode.setText(obj.getMode());
        itemCategory.setText(obj.getCat());
        return rootView;
    }

}
