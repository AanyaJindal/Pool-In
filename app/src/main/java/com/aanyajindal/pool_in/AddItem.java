package com.aanyajindal.pool_in;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.aanyajindal.pool_in.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItem extends AppCompatActivity {

    EditText etItemName, etItemCategory, etItemDesc, etItemMode;
    Button btnAddItem;

    DatabaseReference mainDatabase,itemsList;
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
        btnAddItem = (Button) findViewById(R.id.btn_addItem);

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
                Item item = new Item(itName,user.getUid(),itDesc,itMode,itCat);
                mainDatabase = FirebaseDatabase.getInstance().getReference();
                itemsList = mainDatabase.child("items");
                String itemid = itemsList.push().getKey();
                itemsList.child(itemid).setValue(item);
                mainDatabase.child("users").child(user.getUid()).child("items").child(itemid).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(AddItem.this,WelcomeActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });

            }
        });

    }
}
