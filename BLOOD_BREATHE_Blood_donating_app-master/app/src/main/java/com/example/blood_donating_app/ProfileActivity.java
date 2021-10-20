package com.example.blood_donating_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import  androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView name,email,phonenumber,bloodgroup,city;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MY PROFILE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = findViewById(R.id.profilename);
        email = findViewById(R.id.profileemail);
        phonenumber = findViewById(R.id.profilePhonenumber);
        bloodgroup = findViewById(R.id.profilebloodgroup);
        city = findViewById(R.id.profilecity);
        backButton = findViewById(R.id.backbutton);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    name.setText(snapshot.child("name").getValue().toString());
                    bloodgroup.setText(snapshot.child("bloodgroup").getValue().toString());
                    email.setText(snapshot.child("mail").getValue().toString());
                    phonenumber.setText(snapshot.child("number").getValue().toString());
                    city.setText(snapshot.child("city").getValue().toString());


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}