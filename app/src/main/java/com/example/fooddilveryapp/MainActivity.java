package com.example.fooddilveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    Button buttonUser, buttonVendor, buttonRider;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonRider = findViewById(R.id.btn_rider);
        buttonUser = findViewById(R.id.btn_user);
        buttonVendor = findViewById(R.id.btn_vendor);


        buttonVendor.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
        });

        buttonUser.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
        });

        buttonRider.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
        });


    }
}