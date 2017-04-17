package com.aanyajindal.pool_in;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aanyajindal.pool_in.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddItem extends AppCompatActivity {

    EditText etItemName, etItemCategory, etItemDesc, etItemMode, etItemTags;
    Button btnAddItem;
    RadioGroup rgCategory;

    DatabaseReference mainDatabase, itemsList;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        user = FirebaseAuth.getInstance().getCurrentUser();
        etItemCategory = (EditText) findViewById(R.id.et_itemCategory);
        etItemName = (EditText) findViewById(R.id.et_itemName);
        etItemDesc = (EditText) findViewById(R.id.et_itemDesc);
        etItemMode = (EditText) findViewById(R.id.et_itemMode);
        etItemTags = (EditText) findViewById(R.id.et_itemTags);

        btnAddItem = (Button) findViewById(R.id.btn_addItem);


        etItemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(AddItem.this);
                final View modeDialogView = li.inflate(R.layout.category_dialog, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(AddItem.this);
                rgCategory = (RadioGroup) modeDialogView.findViewById(R.id.rg_category);
                final RadioButton rbBooks = (RadioButton) modeDialogView.findViewById(R.id.rb_books);
                final RadioButton rbCar = (RadioButton) modeDialogView.findViewById(R.id.rb_car);
                final RadioButton rbNotes = (RadioButton) modeDialogView.findViewById(R.id.rb_notes);
                final RadioButton rbRes = (RadioButton) modeDialogView.findViewById(R.id.rb_res);
                final RadioButton rbStationery = (RadioButton) modeDialogView.findViewById(R.id.rb_stationery);
                final RadioButton rbOthers = (RadioButton) modeDialogView.findViewById(R.id.rb_others);
                final EditText etRbothers = (EditText) modeDialogView.findViewById(R.id.et_rbothers);

                rgCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if(checkedId==R.id.rb_others){
                            etRbothers.setVisibility(View.VISIBLE);
                        }
                        else{
                            etRbothers.setVisibility(View.GONE);
                        }
                    }
                });
                alert.setView(modeDialogView);
                alert.setTitle("Category");
                alert.setIcon(R.mipmap.ic_skill);
                alert.setPositiveButton("Set Item Category", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tMode = "";
                        if (rbBooks.isChecked()) {
                            tMode = "Books";
                        } else if (rbCar.isChecked()) {
                            tMode = "Car Pooling";
                        } else if (rbNotes.isChecked()) {
                            tMode = "Notes";
                        } else if (rbRes.isChecked()) {
                            tMode = "Online Resources";
                        } else if (rbStationery.isChecked()) {
                            tMode = "Stationery";
                        } else if (rbOthers.isChecked()) {
                            tMode = "Others:" + etRbothers.getText().toString();
                        }
                        etItemCategory.setText(tMode);
                    }
                });
                alert.setNegativeButton("CANCEL", null);
                alert.create();
                alert.show();
            }
        });

        etItemMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(AddItem.this);
                final View modeDialogView = li.inflate(R.layout.mode_dialog, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(AddItem.this);
                final RadioButton rbSell = (RadioButton) modeDialogView.findViewById(R.id.rb_sell);
                final RadioButton rbRent = (RadioButton) modeDialogView.findViewById(R.id.rb_rent);
                final RadioButton rbDonate = (RadioButton) modeDialogView.findViewById(R.id.rb_donate);
                final RadioButton rbBarter = (RadioButton) modeDialogView.findViewById(R.id.rb_barter);


                alert.setView(modeDialogView);
                alert.setTitle("Mode of Availability");
                alert.setIcon(R.mipmap.ic_mode);
                alert.setPositiveButton("Set Mode of Availability", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tMode = "";
                        if (rbSell.isChecked()) {
                            tMode = "Sell";
                        } else if (rbRent.isChecked()) {
                            tMode = "Rent";
                        } else if (rbDonate.isChecked()) {
                            tMode = "Donate";
                        } else if (rbBarter.isChecked()) {
                            tMode = "Barter";
                        }
                        etItemMode.setText(tMode);
                    }
                });
                alert.setNegativeButton("CANCEL", null);
                alert.create();
                alert.show();
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itName = etItemName.getText().toString();
                String itCat = etItemCategory.getText().toString();
                String itMode = etItemMode.getText().toString();
                String itDesc = etItemDesc.getText().toString();
                String itTags = etItemTags.getText().toString();
                Date newDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = sdf.format(newDate);
                Item item = new Item(itName, user.getUid(), user.getDisplayName(), itDesc, itMode, itCat, itTags, date);
                mainDatabase = FirebaseDatabase.getInstance().getReference();
                itemsList = mainDatabase.child("items");
                String itemid = itemsList.push().getKey();
                itemsList.child(itemid).setValue(item);
                mainDatabase.child("users").child(user.getUid()).child("items").child(itemid).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(AddItem.this, WelcomeActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });

            }
        });

    }
}
