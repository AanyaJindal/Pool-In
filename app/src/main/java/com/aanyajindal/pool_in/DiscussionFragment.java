package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Comment;
import com.aanyajindal.pool_in.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DiscussionFragment extends Fragment {


    DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts");
    ArrayList<Comment> list;

    public DiscussionFragment() {
        // Required empty public constructor
    }

    public static DiscussionFragment newInstance(Post post) {

        Bundle args = new Bundle();

        args.putString("title", post.getTitle());
        args.putString("date", post.getDate());
        args.putString("author", post.getAuthorId());
        args.putString("body", post.getBody());
        args.putString("tags", post.getTags());
        args.putString("category", post.getCategory());
        args.putString("pid", post.getpID());

        DiscussionFragment fragment = new DiscussionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_discussion, container, false);

        Bundle bundle = getArguments();
        Post post = new Post(bundle.getString("title"), bundle.getString("date"), bundle.getString("body"), bundle.getString("author"), bundle.getString("tags"), bundle.getString("category"), bundle.getString("pid"));

        DatabaseReference commentRef = postsRef.child(post.getpID()).child("comments");

        final ValueEventListener valueEventListener = commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot) {
                    Comment comment = new Comment();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView discussionTitleView = (TextView) rootView.findViewById(R.id.discussion_title_value);
        TextView discussionAuthorView = (TextView) rootView.findViewById(R.id.discussion_author_value);
        TextView discussionDateView = (TextView) rootView.findViewById(R.id.discussion_date_value);
        TextView discussionBodyView = (TextView) rootView.findViewById(R.id.discussion_body_value);

        Button addCommentButton = (Button) rootView.findViewById(R.id.btn_addComment);

        ListView commentsList = (ListView) rootView.findViewById(R.id.discussion_comment_listView);

        discussionTitleView.setText(post.getTitle());
        discussionAuthorView.setText(post.getAuthorId());
        discussionDateView.setText(post.getDate());
        discussionBodyView.setText(post.getBody());

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return rootView;
    }

}
