package com.aanyajindal.pool_in;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings_category);

        EditText etCollege = (EditText) findViewById(R.id.et_college);
        EditText etlocation  = (EditText) findViewById(R.id.et_location);
        EditText etSkillSet = (EditText) findViewById(R.id.et_skills);

        Button button = (Button) findViewById(R.id.btn_save_profile);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference mainDatabase,usersList;
        mainDatabase = FirebaseDatabase.getInstance().getReference();
        usersList  = mainDatabase.child("users");
        usersList.child(user.getUid()).setValue(new User(user.getDisplayName(), user.getEmail(),etCollege.getText().toString(),etlocation.getText().toString()));
        usersList.child(user.getUid()).child("skills").setValue(etSkillSet.getText().toString());
    }
}
