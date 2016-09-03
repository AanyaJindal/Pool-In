package com.aanyajindal.pool_in;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        EditText et_postTitle = (EditText)findViewById(R.id.et_postTitle);
        EditText et_postBody = (EditText)findViewById(R.id.et_postBody);
        EditText et_postCategory = (EditText)findViewById(R.id.et_postCategory);
        EditText et_postTags = (EditText)findViewById(R.id.et_postTags);

        Date newDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(newDate);

        String postTitle = et_postTitle.getText().toString();
        String postBody = et_postBody.getText().toString();
        String postCategory =  et_postCategory.getText().toString();
        String postTags = et_postTags.getText().toString();

    }
}
