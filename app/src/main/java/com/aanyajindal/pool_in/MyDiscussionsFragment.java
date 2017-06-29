package com.aanyajindal.pool_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Item;
import com.aanyajindal.pool_in.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyDiscussionsFragment extends Fragment {

    ArrayList<Post> list;
    ArrayList<String> ids;

    private static final String TAG = "MyDiscussionsFragment";

    FirebaseUser user;
    ListView listView;

    public MyDiscussionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_discussions, container, false);

        list = new ArrayList<>();
        ids = new ArrayList<>();
        listView = (ListView) rootView.findViewById(R.id.lv_myDiscussions);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onCreateView: "+user.getDisplayName());

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("posts");

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                Log.d(TAG, "onChildAdded: "+ dataSnapshot.getKey());
                String postID = dataSnapshot.getKey();
                ids.add(postID);

                DatabaseReference postRef = FirebaseDatabase.getInstance().getReference().child("posts").child(postID);
                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Post post = dataSnapshot.getValue(Post.class);
                        list.add(post);
                        MyDiscussionsFragment.PostAdapter postAdapter = new MyDiscussionsFragment.PostAdapter(list);
                        listView.setAdapter(postAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }



    class PostAdapter extends BaseAdapter {
        class Holder {
            TextView title;
            TextView user;
            TextView date;
            TextView tags;
        }

        ArrayList<Post> mList;

        public PostAdapter(ArrayList<Post> mList) {
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            MyDiscussionsFragment.PostAdapter.Holder holder = new MyDiscussionsFragment.PostAdapter.Holder();
            if (convertView == null) {
                convertView = li.inflate(R.layout.post_list, null);

                holder.title = (TextView) convertView.findViewById(R.id.post_title);
                holder.user = (TextView) convertView.findViewById(R.id.post_author);
                holder.date = (TextView) convertView.findViewById(R.id.post_date);
                holder.tags = (TextView) convertView.findViewById(R.id.post_tags);
                convertView.setTag(holder);
            } else {
                holder = (MyDiscussionsFragment.PostAdapter.Holder) convertView.getTag();
            }

            Post post = (Post) getItem(position);
            holder.title.setText(post.getTitle());
            holder.tags.setText(post.getTags());

            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat fmt2 = new SimpleDateFormat("EEE, MMM d, ''yy");
            String frDate = "";
            try {
                Date date = fmt.parse(post.getDate().toString());
                frDate = fmt2.format(date);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            holder.date.setText(frDate);
            holder.user.setText(post.getAuthor());

//            DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("users").child(post.getAuthorId());
//            final Holder finalHolder = holder;
//            temp.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    user = dataSnapshot.getValue(User.class);
//                    finalHolder.user.setText(user.getName());
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment frag = DiscussionFragment.newInstance(list.get(position));
                    fragmentTransaction.replace(R.id.frag_container, frag);
                    fragmentTransaction.commit();
                }
            });
            return convertView;
        }

    }

}
