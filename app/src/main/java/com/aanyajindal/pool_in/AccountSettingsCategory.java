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
import android.widget.CheckBox;
import android.widget.EditText;

import com.aanyajindal.pool_in.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountSettingsCategory extends AppCompatActivity {

    EditText etlocation;
    EditText etCollege;
    EditText etSkillSet;
        DatabaseReference mainDatabase,usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings_category);

        etCollege = (EditText) findViewById(R.id.et_college);
        etlocation = (EditText) findViewById(R.id.et_location);

        etSkillSet = (EditText) findViewById(R.id.et_skills);

        Button button = (Button) findViewById(R.id.btn_save_profile);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        etSkillSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(AccountSettingsCategory.this);
                final View skillDialogView = li.inflate(R.layout.skills_dialog, null);
                final AlertDialog.Builder alert = new AlertDialog.Builder(AccountSettingsCategory.this);
                final CheckBox cbAndroid = (CheckBox) skillDialogView.findViewById(R.id.cb_android);
                final CheckBox cbArtIntel = (CheckBox) skillDialogView.findViewById(R.id.cb_artInel);
                final CheckBox cbC = (CheckBox) skillDialogView.findViewById(R.id.cb_c);
                final CheckBox cbCompCode = (CheckBox) skillDialogView.findViewById(R.id.cb_compCode);
                final CheckBox cbCss = (CheckBox) skillDialogView.findViewById(R.id.cb_css);
                final CheckBox cbSql = (CheckBox) skillDialogView.findViewById(R.id.cb_sql);
                final CheckBox cbDs = (CheckBox) skillDialogView.findViewById(R.id.cb_ds);
                final CheckBox cbHtml = (CheckBox) skillDialogView.findViewById(R.id.cb_html);
                final CheckBox cbJava = (CheckBox) skillDialogView.findViewById(R.id.cb_java);
                final CheckBox cbJavas = (CheckBox) skillDialogView.findViewById(R.id.cb_javas);
                final CheckBox cbMachlearn = (CheckBox) skillDialogView.findViewById(R.id.cb_machLearn);
                final CheckBox cbNetworking = (CheckBox) skillDialogView.findViewById(R.id.cb_networking);
                final CheckBox cbNosql = (CheckBox) skillDialogView.findViewById(R.id.cb_nosql);
                final CheckBox cbPerl = (CheckBox) skillDialogView.findViewById(R.id.cb_perl);
                final CheckBox cbPhp = (CheckBox) skillDialogView.findViewById(R.id.cb_php);
                final CheckBox cbRuby = (CheckBox) skillDialogView.findViewById(R.id.cb_ruby);
                final CheckBox cbScala = (CheckBox) skillDialogView.findViewById(R.id.cb_scala);
                final CheckBox cbPython = (CheckBox) skillDialogView.findViewById(R.id.cb_python);
                alert.setView(skillDialogView);
                alert.setTitle("Skill-Set");
                alert.setPositiveButton("Set Skillset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tMode = "";
                        if(cbAndroid.isChecked()){
                            tMode += "Android, ";
                        }
                        if(cbArtIntel.isChecked()){
                            tMode += "Artificial Intelligence, ";
                        }
                        if(cbC.isChecked()){
                            tMode += "C/C++, ";
                        }
                        if(cbCompCode.isChecked()){
                            tMode += "Competitive Coding, ";
                        }
                        if(cbCss.isChecked()){
                            tMode += "CSS, ";
                        }
                        if(cbSql.isChecked()){
                            tMode += "SQL Database, ";
                        }
                        if(cbDs.isChecked()){
                            tMode += "Data Structures, ";
                        }if(cbHtml.isChecked()){
                            tMode += "HTML, ";
                        }if(cbJava.isChecked()){
                            tMode += "Java, ";
                        }if(cbJavas.isChecked()){
                            tMode += "Javascript, ";
                        }if(cbMachlearn.isChecked()){
                            tMode += "Machine Learning, ";
                        }if(cbNetworking.isChecked()){
                            tMode += "Networking, ";
                        }if(cbNosql.isChecked()){
                            tMode += "NoSQL Database, ";
                        }if(cbPerl.isChecked()){
                            tMode += "Perl, ";
                        }if(cbPhp.isChecked()){
                            tMode += "PHP, ";
                        }if(cbRuby.isChecked()){
                            tMode += "Ruby, ";
                        }if(cbScala.isChecked()){
                            tMode += "Scala, ";
                        } if(cbPython.isChecked()){
                            tMode+="Python, ";
                        }
                        if(!tMode.equals("")){
                            tMode = tMode.substring(0, tMode.length()-2);
                        }
                        etSkillSet.setText(tMode);
                    }
                });
                alert.setNegativeButton("CANCEL", null);
                alert.create();
                alert.show();
            }

        });

        mainDatabase = FirebaseDatabase.getInstance().getReference();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usersList  = mainDatabase.child("users");
                String string = etSkillSet.getText().toString();
                usersList.child(user.getUid()).setValue(new User(user.getDisplayName(), user.getEmail(),etCollege.getText().toString(),etlocation.getText().toString()));
                String[] parts = string.split(", ");
                usersList.child(user.getUid()).child("skills").setValue("");
                for(int i= 0;i<parts.length;i++){
                    usersList.child(user.getUid()).child("skills").child(parts[i]).setValue("true");
                }

            }
        });

    }
}
