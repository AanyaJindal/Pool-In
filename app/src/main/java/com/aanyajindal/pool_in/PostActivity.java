package com.aanyajindal.pool_in;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aanyajindal.pool_in.models.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    Button btn;
    EditText etPostTitle;
    EditText etPostBody;
    EditText etPostCategory;
    EditText etPostTags;
    RadioGroup rgPostCategory;
    DatabaseReference postsDatabase;
    private static final String TAG = "PostActivity";
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        user = FirebaseAuth.getInstance().getCurrentUser();
        etPostTitle = (EditText) findViewById(R.id.et_postTitle);
        etPostBody = (EditText) findViewById(R.id.et_postBody);
        etPostTags = (EditText) findViewById(R.id.et_postTags);
        etPostCategory = (EditText) findViewById(R.id.et_postCategory);
        btn = (Button) findViewById(R.id.btn_addPost);

        Date newDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String date = sdf.format(newDate);

        etPostCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(PostActivity.this);
                final View postDialogView = li.inflate(R.layout.post_dialog, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(PostActivity.this);
                rgPostCategory = (RadioGroup) postDialogView.findViewById(R.id.rg_post_category);
                final RadioButton rbPlacement = (RadioButton) postDialogView.findViewById(R.id.rb_placement);
                final RadioButton rbLost = (RadioButton) postDialogView.findViewById(R.id.rb_lost);
                final RadioButton rbMentor = (RadioButton) postDialogView.findViewById(R.id.rb_mentor);
                final RadioButton rbBlood = (RadioButton) postDialogView.findViewById(R.id.rb_blood);
                final RadioButton rbComplaint = (RadioButton) postDialogView.findViewById(R.id.rb_complaints);
                final RadioButton rbFest = (RadioButton) postDialogView.findViewById(R.id.rb_fest);
                final RadioButton rbCampus = (RadioButton) postDialogView.findViewById(R.id.rb_campus);
                final RadioButton rbOthers1 = (RadioButton) postDialogView.findViewById(R.id.rb_others1);
                final RadioButton rbProjects = (RadioButton) postDialogView.findViewById(R.id.rb_project);
                final EditText etRbothers1 = (EditText) postDialogView.findViewById(R.id.et_rbothers1);

                rgPostCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if (checkedId == R.id.rb_others1) {
                            etRbothers1.setVisibility(View.VISIBLE);
                        } else {
                            etRbothers1.setVisibility(View.GONE);
                        }
                    }
                });

                alert.setView(postDialogView);
                alert.setTitle("Post Category");
                alert.setIcon(R.mipmap.ic_skill);
                alert.setPositiveButton("Set Post Category", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tMode = "";
                        if (rbPlacement.isChecked()) {
                            tMode = "Placement";
                        } else if (rbProjects.isChecked()) {
                            tMode = "Projects";
                        } else if (rbLost.isChecked()) {
                            tMode = "Lost and Found";
                        } else if (rbMentor.isChecked()) {
                            tMode = "Mentorship";
                        } else if (rbBlood.isChecked()) {
                            tMode = "Blood Donation";
                        } else if (rbComplaint.isChecked()) {
                            tMode = "Complaints";
                        } else if (rbFest.isChecked()) {
                            tMode = "Fest Participation";
                        } else if (rbCampus.isChecked()) {
                            tMode = "Campus Life";
                        } else if (rbOthers1.isChecked()) {
                            tMode = "Others";
                            etPostTitle.setText(etRbothers1.getText().toString() + ": " +etPostTitle.getText());
                        }
                        etPostCategory.setText(tMode);
                    }
                });
                alert.setNegativeButton("CANCEL", null);
                alert.create();
                alert.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: gello");
                String postTitle = etPostTitle.getText().toString();
                String postBody = etPostBody.getText().toString();
                String postCategory = etPostCategory.getText().toString();
                String postTags = etPostTags.getText().toString();
                if (postTitle.equals("")) {
                    Toast.makeText(PostActivity.this, "Title cannot be left empty!", Toast.LENGTH_SHORT).show();
                } else if (postBody.equals("")) {
                    Toast.makeText(PostActivity.this, "Body cannot be left empty!", Toast.LENGTH_SHORT).show();
                } else if (postCategory.equals("")) {
                    Toast.makeText(PostActivity.this, "Category must be selected", Toast.LENGTH_SHORT).show();
                } else {

                    postsDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
                    String key;
                    key = postsDatabase.push().getKey();
                    Post post = new Post(postTitle, date, postBody, user.getUid(), user.getDisplayName(), postTags, postCategory, key);
                    FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("posts").child(key).setValue(true);
                    postsDatabase.child(key).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(PostActivity.this, WelcomeActivity.class));
                            Toast.makeText(PostActivity.this, "Post added successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PostActivity.this, "Sorry! Post could not be added at this time", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}
