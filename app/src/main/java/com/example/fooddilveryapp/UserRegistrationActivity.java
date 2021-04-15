package com.example.fooddilveryapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserRegistrationActivity extends AppCompatActivity {

    private final AppCompatActivity activity = UserRegistrationActivity.this;
    private DatabaseHelper databaseHelper;
    private User user;
    private Button buttonSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextNumber;
    private EditText editTextConfirmPassword;
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

        editTextEmail = findViewById(R.id.et_email);
        editTextName = findViewById(R.id.et_fullname);
        editTextPassword = findViewById(R.id.et_password);
        editTextConfirmPassword = findViewById(R.id.et_confirm_password);
        editTextNumber = findViewById(R.id.et_phnumber);


        buttonSignUp = findViewById(R.id.btn_signup);
        buttonSignUp.setOnClickListener(v -> {
            postDataToSQLite();
        });


    }


    private void postDataToSQLite() {

        String password = editTextPassword.getText().toString();
        String confirm_password = editTextConfirmPassword.getText().toString();
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String phnumber = editTextNumber.getText().toString();





        if(!password.equals(confirm_password)){
            Constants.showToast(getApplicationContext(),"Password not Matched");
            return;
        }

        if (!databaseHelper.checkUser(editTextEmail.getText().toString().trim())) {
            user.setUserFullName(editTextName.getText().toString().trim());
            user.setUserEmail(editTextEmail.getText().toString().trim());
            user.setUserPassword(editTextPassword.getText().toString().trim());
            user.setUserPhoneNumber(Integer.parseInt(editTextNumber.getText().toString().trim()));
            databaseHelper.addUser(user);
            // Snack Bar to show success message that record saved successfully
            Toast.makeText(getApplicationContext(), "SignUp Successfull", Toast.LENGTH_SHORT).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(getApplicationContext(), "Email Already Exsits", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextNumber.setText(null);
        editTextPassword.setText(null);
        editTextEmail.setText(null);
        editTextName.setText(null);
    }
}