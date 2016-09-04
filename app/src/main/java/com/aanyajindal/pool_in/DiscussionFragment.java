package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DiscussionFragment extends Fragment {


    public DiscussionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_discussion, container, false);

        TextView discussionTitleView = (TextView)rootView.findViewById(R.id.discussion_title_value);
        TextView discussionAuthorView = (TextView)rootView.findViewById(R.id.discussion_author_value);
        TextView discussionDateView = (TextView)rootView.findViewById(R.id.discussion_date_value);
        TextView discussionBodyView = (TextView)rootView.findViewById(R.id.discussion_body_value);

        Button addCommentButton = (Button)rootView.findViewById(R.id.btn_addComment);

        ListView commentsList = (ListView)rootView.findViewById(R.id.discussion_comment_listView);

        return rootView;
    }

}
