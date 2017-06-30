package com.aanyajindal.pool_in;



import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aanyajindal.pool_in.models.Item;
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
public class MyItemsFragment extends Fragment {

    ArrayList<Item> list;
    ArrayList<String> ids;
    MyItemsFragment.ItemAdapter itemAdapter;


    FirebaseUser user;
    ListView listView;

    public static final String TAG = "MyItemsFragment";


    public MyItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_my_items, container, false);
        list = new ArrayList<>();
        ids = new ArrayList<>();
        listView = (ListView) rootView.findViewById(R.id.lv_myItems);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onCreateView: " + user.getDisplayName());

        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("items");

        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                Log.d(TAG, "onChildAdded: " + dataSnapshot.getKey());
                String itemID = dataSnapshot.getKey();
                ids.add(itemID);

                DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("items").child(itemID);
                itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Item item = dataSnapshot.getValue(Item.class);
                        list.add(item);
                        itemAdapter = new MyItemsFragment.ItemAdapter(list);
                        listView.setAdapter(itemAdapter);
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
                Log.d(TAG, "onChildRemoved: " + dataSnapshot);
                int i = ids.indexOf(dataSnapshot.getKey());
                list.remove(i);
                itemAdapter = new MyItemsFragment.ItemAdapter(list);
                listView.setAdapter(itemAdapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment frag = ItemFragment.newInstance(ids.get(position), list.get(position));
                fragmentTransaction.replace(R.id.frag_container, frag);
                fragmentTransaction.commit();
            }
        });

        // define Choice mode for multiple  delete
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO  Auto-generated method stub
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO  Auto-generated method stub
                CustomViewPager view = (CustomViewPager) container.findViewById(R.id.viewpager);
                view.disableScroll(false);

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // TODO  Auto-generated method stub
                mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
                CustomViewPager view = (CustomViewPager) container.findViewById(R.id.viewpager);
                view.disableScroll(true);
//                view.setOnTouchListener(otl);


                return true;

            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode,
                                               MenuItem item) {

                // TODO  Auto-generated method stub
                switch (item.getItemId()) {
                    case R.id.selectAll:
                        //
                        final int checkedCount = list.size();
                        // If item  is already selected or checked then remove or
                        // unchecked  and again select all
                        itemAdapter.removeSelection();
                        for (int i = 0; i < checkedCount; i++) {
                            listView.setItemChecked(i, true);
                            itemAdapter.toggleSelection(i);
                        }
                        mode.setTitle(checkedCount + "  Selected");
                        return true;
                    case R.id.delete:
                        // Add  dialog for confirmation to delete selected item
                        // record.
                        AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Do you surely want to delete the selected item(s)?");

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO  Auto-generated method stub
                                SparseBooleanArray selected = itemAdapter.getSelectedIds();
                                Log.d(TAG, "onClick: " + selected);
                                for (int i = (selected.size() - 1); i >= 0; i--) {
                                    if (selected.valueAt(i)) {
                                        Log.d(TAG, "onClick: " + i + " hehe " + selected.valueAt(i));
                                        String selecteditemID = ids.get(selected.keyAt(i));
                                        Log.d(TAG, "onClick: " + selecteditemID);
                                        FirebaseDatabase.getInstance().getReference().child("items")
                                                .child(selecteditemID).removeValue();
                                        FirebaseDatabase.getInstance().getReference().child("users")
                                                .child(user.getUid()).child("items")
                                                .child(selecteditemID).removeValue();
                                    }
                                }

                                // Close CAB
                                mode.finish();
                                selected.clear();

                            }
                        });
                        AlertDialog alert = builder.create();
                        //alert.setIcon(R.drawable);// dialog  Icon
                        alert.setTitle("Confirmation"); // dialog  Title
                        alert.show();
                        return true;
                    default:
                        return false;
                }

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // TODO  Auto-generated method stub
                final int checkedCount = listView.getCheckedItemCount();
                // Set the  CAB title according to total checked items
                mode.setTitle(checkedCount + "  Selected");
                // Calls  toggleSelection method from ListViewAdapter Class
                itemAdapter.toggleSelection(position);
            }
        });

        return rootView;
    }

    class ItemAdapter extends BaseAdapter {

        class Holder {
            TextView name;
            TextView user;
            TextView date;
            TextView tags;
        }

        ArrayList<Item> mList;
        private SparseBooleanArray mSelectedItemsIds;

        public ItemAdapter(ArrayList<Item> mList) {

            this.mList = mList;
            mSelectedItemsIds = new SparseBooleanArray();
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
            MyItemsFragment.ItemAdapter.Holder holder = new MyItemsFragment.ItemAdapter.Holder();
            if (convertView == null) {
                convertView = li.inflate(R.layout.item_list, null);

                holder.name = (TextView) convertView.findViewById(R.id.item_name);
                holder.user = (TextView) convertView.findViewById(R.id.item_owner);
                holder.date = (TextView) convertView.findViewById(R.id.item_date);
                holder.tags = (TextView) convertView.findViewById(R.id.item_tags);
                convertView.setTag(holder);
            } else {
                holder = (MyItemsFragment.ItemAdapter.Holder) convertView.getTag();
            }

            Item item = (Item) getItem(position);
            holder.name.setText(item.getName());
            holder.tags.setText(item.getTags());

            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat fmt2 = new SimpleDateFormat("EEE, MMM d, ''yy");
            String frDate = "";
            try {
                Date date = fmt.parse(item.getDate().toString());
                frDate = fmt2.format(date);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
            holder.date.setText(frDate);
            holder.user.setText((item.getUsername()));

//            DatabaseReference temp = FirebaseDatabase.getInstance().getReference().child("users").child(item.getUser());
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

            return convertView;
        }

        public ArrayList<Item> getMyList() {
            return mList;
        }

        public void toggleSelection(int position) {
            selectView(position, !mSelectedItemsIds.get(position));
        }

        // Remove selection after unchecked
        public void removeSelection() {
            mSelectedItemsIds = new SparseBooleanArray();
            notifyDataSetChanged();
        }

        // Item checked on selection
        public void selectView(int position, boolean value) {
            if (value)
                mSelectedItemsIds.put(position, value);
            else
                mSelectedItemsIds.delete(position);
            notifyDataSetChanged();
        }

        // Get number of selected item
        public int getSelectedCount() {
            return mSelectedItemsIds.size();
        }

        public SparseBooleanArray getSelectedIds() {
            return mSelectedItemsIds;
        }


    }

}
