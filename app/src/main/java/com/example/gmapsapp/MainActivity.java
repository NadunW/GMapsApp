package com.example.gmapsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ERR_DIALOG_REQ = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isServicesOK() == true) {
            init();
        }
    }

    public void init() {
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //Everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google services working");
            return true;

        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //An error occured but we can resolve it (Version mismatches)
            Log.d(TAG, "isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERR_DIALOG_REQ);
            dialog.show();
        }
        else {
            Toast.makeText(this, "You cannot make map requests", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
