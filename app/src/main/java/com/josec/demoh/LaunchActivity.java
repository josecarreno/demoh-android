package com.josec.demoh;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LaunchActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mAuth = FirebaseAuth.getInstance();

        final Class<? extends Activity> activityClass;
        if(userIsLoggedOn())
            activityClass = ClientesActivity.class;
        else
            activityClass = MainActivity.class;

        Intent newActivity = new Intent(this, activityClass);
        startActivity(newActivity);
        finish();
    }

    private Boolean userIsLoggedOn() {
        return mAuth.getCurrentUser() != null;
    }
}
