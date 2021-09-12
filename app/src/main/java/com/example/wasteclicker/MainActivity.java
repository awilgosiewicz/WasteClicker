package com.example.wasteclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        PasswordEt = (EditText) findViewById(R.id.etPassword);

    }


    public void OnLogin(View view){
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);

        Intent openGame = new Intent(this, GameActivity.class);
        openGame.putExtra("name_key", username);
        startActivity(openGame);
    }



    public void OpenReg(View view){
        startActivity(new Intent(this, Register.class));
    }




}