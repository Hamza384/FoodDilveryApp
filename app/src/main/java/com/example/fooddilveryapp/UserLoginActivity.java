package com.example.fooddilveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class UserLoginActivity extends AppCompatActivity {

    private TextView textViewSignUp;
    private DatabaseHelper databaseHelper;
    private final AppCompatActivity activity = UserLoginActivity.this;
    private Button buttonLogin;
    private EditText editTextEmail, editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        databaseHelper = new DatabaseHelper(activity);

        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);

        textViewSignUp = findViewById(R.id.tv_Signup);
        textViewSignUp.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), UserRegistrationActivity.class));

        });

        buttonLogin = findViewById(R.id.btn_login);
        buttonLogin.setOnClickListener(v -> {
            verifyFromSQLite();
        });



    }

    private void verifyFromSQLite() {
        if (databaseHelper.checkUser(editTextEmail.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {
            Intent accountsIntent = new Intent(activity, MyMainActivity.class);
            emptyInputEditText();
            startActivity(accountsIntent);
        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        editTextPassword.setText(null);
        editTextEmail.setText(null);
    }

}