package com.example.wasteclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    EditText username, email, password, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        age = (EditText) findViewById(R.id.et_age);
    }

    public void OnReg(View view){
        String str_username = username.getText().toString();
        String str_email = email.getText().toString();
        String str_password = password.getText().toString();
        String str_age = age.getText().toString();

        String type = "register";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_username, str_email, str_password, str_age);
        finish();
    }

}