package com.aanyajindal.pool_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.User;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView ivUserProfilePic;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    User mUser;
    TextView tvUserDisplayName;
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    startActivity(new Intent(WelcomeActivity.this, EditProfileActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);


        ivUserProfilePic = (ImageView) header.findViewById(R.id.iv_user_profile_pic);
        tvUserDisplayName = (TextView) header.findViewById(R.id.tv_user_display_name);
        Log.d(TAG, "onCreate: " + user.getDisplayName());

        Glide.with(this).load(user.getPhotoUrl().toString()).into(ivUserProfilePic);
        tvUserDisplayName.setText("Hello, " + user.getDisplayName());


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        fragment = HomeFragment.newInstance();
        fragmentTransaction.replace(R.id.frag_container, fragment);
        fragmentTransaction.commit();

//        Intent intent = new Intent(this, MyFirebaseInstanceIdService.class);
//        startService(intent);

        user.getToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            Log.d(TAG, "onComplete: " + idToken);
                            DatabaseReference userTokenRef = FirebaseDatabase.getInstance().getReference().child("user-tokens");
                            String userId = user.getUid();
                            userTokenRef.child(userId).setValue(idToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });
                            // Send token to your backend via HTTPS
                            // ...
                        } else {
                            // Handle error -> task.getException();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        FragmentManager.BackStackEntry backStackEntry = getSupportFragmentManager()
//                .getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1);
        Log.d(TAG, "onBackPressed: "+getSupportFragmentManager());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Fragment frag1 = HomeFragment.newInstance();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frag_container,frag1).commit();


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment frag = null;

        if (id == R.id.nav_settings) {
            frag = SettingsFragment.newInstance();
            fragmentTransaction.addToBackStack("home");
            fragmentTransaction.replace(R.id.frag_container, frag);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_home) {
            frag = HomeFragment.newInstance();
            fragmentTransaction.replace(R.id.frag_container, frag);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_browse) {
            frag = ItemCategoryFragment.newInstance();
            fragmentTransaction.replace(R.id.frag_container, frag);
            fragmentTransaction.addToBackStack("home");
            fragmentTransaction.commit();
        } else if (id == R.id.my) {
            frag = MyFragment.newInstance(0);
            fragmentTransaction.replace(R.id.frag_container, frag);
            fragmentTransaction.addToBackStack("home");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("userid", user.getUid());
            startActivity(intent);

        } else if (id == R.id.nav_discussions) {
            frag = PostCategoryFragment.newInstance();
            fragmentTransaction.replace(R.id.frag_container, frag);
            fragmentTransaction.addToBackStack("home");
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
