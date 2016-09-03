package com.aanyajindal.pool_in;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddItem extends AppCompatActivity {

    EditText etItemName, etItemCategory, etItemDesc, etItemMode;
    Button btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

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
                alert.setPositiveButton("Set Mode of Payment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tMode = "";
                        if(rbSell.isChecked()){
                            tMode = "Sell";
                        }else if(rbRent.isChecked()){
                            tMode = "Rent";
                        }else if(rbDonate.isChecked()){
                            tMode = "Donate";
                        }else if(rbBarter.isChecked()){
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
            }
        });

    }
}
