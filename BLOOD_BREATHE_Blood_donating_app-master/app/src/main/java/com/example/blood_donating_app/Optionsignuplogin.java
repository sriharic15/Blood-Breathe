package com.example.blood_donating_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Optionsignuplogin extends AppCompatActivity {
 Button loginbtn;
 Button signupbtn;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optionsignuplogin);
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.signupbtn);
        loginbtn.setOnClickListener(v -> {
            Intent i = new Intent(Optionsignuplogin.this,LoginActivity.class);
            startActivity(i);
        });
        signupbtn.setOnClickListener(v -> {
            Intent i =new Intent(Optionsignuplogin.this,RegestrationPage.class);
            startActivity(i);
        });
    }
}