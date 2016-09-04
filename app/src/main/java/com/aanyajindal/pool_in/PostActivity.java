package com.aanyajindal.pool_in;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aanyajindal.pool_in.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    Button btn;
    EditText et_postTitle;
    EditText et_postBody;
    EditText et_postCategory;
    EditText et_postTags;

    DatabaseReference postsDatabase;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        et_postTitle = (EditText)findViewById(R.id.et_postTitle);
        et_postBody = (EditText)findViewById(R.id.et_postBody);
        et_postTags = (EditText)findViewById(R.id.et_postTags);
        et_postCategory = (EditText) findViewById(R.id.et_postCategory);
        btn = (Button) findViewById(R.id.btn_addPost);

        Date newDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String date = sdf.format(newDate);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postTitle = et_postTitle.getText().toString();
                String postBody = et_postBody.getText().toString();
                String postCategory =  et_postCategory.getText().toString();
                String postTags = et_postTags.getText().toString();
                user = FirebaseAuth.getInstance().getCurrentUser();
                postsDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
                String key;
                Post post = new Post(postTitle,date,postBody,user.getUid(),postTags,postCategory);
                key = postsDatabase.push().getKey();
                postsDatabase.child(key).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(PostActivity.this,WelcomeActivity.class));
                    }
                });
            }
        });


    }
}
