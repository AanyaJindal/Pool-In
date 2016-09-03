package com.aanyajindal.pool_in;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aanyajindal.pool_in.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountSettingsCategory extends AppCompatActivity {

    EditText etlocation;
    EditText etCollege;
    EditText etSkillSet;
        DatabaseReference mainDatabase,usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings_category);

        etCollege = (EditText) findViewById(R.id.et_college);
        etlocation = (EditText) findViewById(R.id.et_location);

        etSkillSet = (EditText) findViewById(R.id.et_skills);

        Button button = (Button) findViewById(R.id.btn_save_profile);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        mainDatabase = FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string = etSkillSet.getText().toString();
                String[] parts = string.split(",");
                for(int i= 0;i<parts.length;i++){
                    usersList.child(user.getUid()).child("skills").push().setValue(parts[i]);
                }
                usersList  = mainDatabase.child("users");
                usersList.child(user.getUid()).setValue(new User(user.getDisplayName(), user.getEmail(),etCollege.getText().toString(),etlocation.getText().toString()));

            }
        });

    }
}
