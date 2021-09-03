package com.example.wasteclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button playBtn;
    Button saveBtn, btnViewData;
    EditText UsernameEt, etEmail, PasswordEt ,etAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        playBtn =(Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
        peopleDB = new DatabaseHelper(this);
*/

        UsernameEt = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.email);
        PasswordEt = (EditText) findViewById(R.id.etPassword);
        etAge = (EditText) findViewById(R.id.Age);
    }

    public void OnLogin(View view){
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }


    /*
    public void openGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
*/
/*
    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
*/
}