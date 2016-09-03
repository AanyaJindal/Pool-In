package com.aanyajindal.pool_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN  = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, WelcomeActivity.class));
        } else {
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setLogo(R.mipmap.ic_launcher)
                            .setIsSmartLockEnabled(false)
                            .setProviders(AuthUI.EMAIL_PROVIDER,AuthUI.GOOGLE_PROVIDER)
                            .build(this),
                    RC_SIGN_IN);
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//this activity now not on backstack
                startActivity(intent);
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
            }
        }
    }
}
