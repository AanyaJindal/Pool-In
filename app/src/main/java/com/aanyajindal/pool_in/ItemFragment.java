package com.aanyajindal.pool_in;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Item;
import com.aanyajindal.pool_in.models.User;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ItemFragment extends Fragment {
    User user;

    File localFile = null;
    ImageView ivItem;

    public ItemFragment() {

    }

    public static ItemFragment newInstance(String id, Item item) {

        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("name", item.getName());
        args.putString("username", item.getUsername());
        args.putString("date", item.getDate());
        args.putString("desc", item.getDesc());
        args.putString("tags", item.getTags());
        args.putString("user", item.getUser());
        args.putString("mode", item.getMode());
        args.putString("cat", item.getCat());
        args.putString("image", item.getImage());
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item, container, false);
        Bundle bundle = getArguments();
        final Item obj = new Item(bundle.getString("name"), bundle.getString("user"), bundle.getString("username"), bundle.getString("desc")
                , bundle.getString("mode"), bundle.getString("cat"), bundle.getString("tags"), bundle.getString("date"), bundle.getString("image"));

        String itemID = bundle.getString("id");

        TextView itemName = (TextView) rootView.findViewById(R.id.item_name_value);
        TextView itemUser;
        itemUser = (TextView) rootView.findViewById(R.id.item_user_value);
        TextView itemDesc;
        itemDesc = (TextView) rootView.findViewById(R.id.item_desc_value);
        TextView itemMode = (TextView) rootView.findViewById(R.id.item_mode_value);
        TextView itemCategory = (TextView) rootView.findViewById(R.id.item_category_value);
        TextView itemTags = (TextView) rootView.findViewById(R.id.item_tags_value);
        TextView itemDate = (TextView) rootView.findViewById(R.id.item_date_value);
        ivItem = (ImageView) rootView.findViewById(R.id.iv_item);


//        DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("users").child(obj.getUser());
//        temp.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                user = dataSnapshot.getValue(User.class);
//                itemUser.setText(user.getName());
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        itemName.setText(obj.getName());
        itemUser.setText(obj.getUsername());
        itemDesc.setText(obj.getDesc());
        itemMode.setText(obj.getMode());
        itemCategory.setText(obj.getCat());
        itemTags.setText(obj.getTags());

        if (obj.getImage().equals("true")) {

            ivItem.setVisibility(View.VISIBLE);
            try {
                localFile = File.createTempFile("images", "jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            StorageReference ref = FirebaseStorage.getInstance().getReference().child("item" + itemID + ".jpg");
            ref.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Glide.with(getContext()).load(localFile).into(ivItem);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle failed download
                    // ...
                }
            });
        }

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
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("userid", obj.getUser());
                startActivity(intent);
            }
        });

        return rootView;
    }

}
