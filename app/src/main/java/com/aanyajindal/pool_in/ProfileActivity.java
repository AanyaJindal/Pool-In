package com.aanyajindal.pool_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.bumptech.glide.Glide.with;

public class ProfileActivity extends AppCompatActivity {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView tvEmailItem;
    RelativeLayout rlContact;
    TextView tvLocation;
    TextView tvBranch;
    TextView tvYear;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvContact;
    String id;
    ImageView ivProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id =  getIntent().getStringExtra("userid");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        ivProfilePic = (ImageView) findViewById(R.id.iv_profile_pic);
        rlContact = (RelativeLayout) findViewById(R.id.rl_contact);
        tvEmailItem = (TextView) findViewById(R.id.tv_email_item);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvYear = (TextView) findViewById(R.id.tv_year);
        tvBranch = (TextView) findViewById(R.id.tv_branch);
        tvContact = (TextView) findViewById(R.id.tv_contact_item);

        myRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User mUser = dataSnapshot.getValue(User.class);
                tvEmailItem.setText(mUser.getEmail());
                tvLocation.setText(mUser.getLocation());
                tvYear.setText(mUser.getYear());
                collapsingToolbarLayout.setTitle(mUser.getName());
                tvBranch.setText(mUser.getBranch());

                Glide
                        .with(ProfileActivity.this)
                        .load(mUser.getDplink())
                        .placeholder(R.mipmap.ic_launcher)
                        .crossFade()
                        .centerCrop()
                        .into(ivProfilePic);

                if(id.equals(user.getUid()))
                {
                    rlContact.setVisibility(View.VISIBLE);
                    tvContact.setText(mUser.getContact());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
            }
        });
    }

}
