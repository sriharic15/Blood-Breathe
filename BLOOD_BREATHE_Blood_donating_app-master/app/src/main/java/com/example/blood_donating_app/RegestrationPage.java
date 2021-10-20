package com.example.blood_donating_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegestrationPage extends AppCompatActivity {
    ImageButton back;
    private CircleImageView profile_image;
    private EditText name;
    private EditText number;
    private EditText mail;
    private EditText city;
    private EditText registerpassword;
    private Spinner bloodGroupsSpinner;
   private Button registerbutton;
   private  FirebaseAuth mAuth;
   private  DatabaseReference userDatareference;
    private ProgressDialog loader;
    private  Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration_page);
        back = (ImageButton) findViewById(R.id.ivBack);
        back.setOnClickListener(v -> {
            Intent i = new Intent(RegestrationPage.this, Optionsignuplogin.class);
            startActivity(i);
        });

        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        mail = findViewById(R.id.mail);
        bloodGroupsSpinner = findViewById(R.id.bloodgroupspinner);
        registerpassword = findViewById(R.id.registerpassword);
        registerbutton = findViewById(R.id.rbutton);
        city = findViewById(R.id.city);
        loader=new ProgressDialog(RegestrationPage.this);
        mAuth = FirebaseAuth.getInstance();


        registerbutton.setOnClickListener(v -> {
            final String remail = mail.getText().toString().trim();
            final String rpassword = registerpassword.getText().toString().trim();
            final String fullname = name.getText().toString().trim();
            final String phonenumber = number.getText().toString().trim();
            final String bloodgroup = bloodGroupsSpinner.getSelectedItem().toString();
            if (bloodgroup.equals("SELECT YOUR BLOOD GROUP")) {
                Toast.makeText(RegestrationPage.this, "SELECT BLOOD GROUP", Toast.LENGTH_LONG).show();
                return;
            }


            String cityname = city.getText().toString().trim();

            if (TextUtils.isEmpty(remail)) {
                mail.setError("EMAIL IS REQUIRED");
                return;
            }
            if (TextUtils.isEmpty(rpassword)) {
                registerpassword.setError("PASSWORD IS REQUIRED");
                return;
            }
            if (TextUtils.isEmpty(phonenumber)) {
                number.setError("NUMBER IS REQUIRED");
                return;
            }
            if (TextUtils.isEmpty(fullname)) {
                name.setError("NAME IS REQUIRED");
                return;
            }
            if (TextUtils.isEmpty(cityname)) {
                city.setError("CITY IS REQUIRED");
                return;
            }
            if (bloodgroup.equals("SELECT YOUR BLOOD GROUP")) {
                Toast.makeText(RegestrationPage.this, "SELECT BLOOD GROUP", Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                loader.setMessage("regestering you ....");
                loader.setCanceledOnTouchOutside(false);
                loader.show();
                mAuth.createUserWithEmailAndPassword(remail,rpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            String error = task.getException().toString();
                            Toast.makeText(RegestrationPage.this,"Error" +error,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String currentUserId=mAuth.getCurrentUser().getUid();
                            userDatareference = FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(currentUserId);
                            HashMap userinfo =new HashMap();
                            userinfo.put("id",currentUserId);
                            userinfo.put("name",fullname);
                            userinfo.put("number",phonenumber);
                            userinfo.put("mail",remail);
                            userinfo.put("city",cityname);
                            userinfo.put("bloodgroup",bloodgroup);
                            userinfo.put("type","donor");
                            userinfo.put("share","donors"+bloodgroup);

                            userDatareference.updateChildren(userinfo).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task task) {
                                    if(task.isSuccessful())
                                    {
                                        loader.dismiss();
                                        Toast.makeText(RegestrationPage.this,"success",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegestrationPage.this,MainActivity.class);
                                        startActivity(intent);

                                    }
                                    else {
                                        Toast.makeText(RegestrationPage.this,task.getException().toString(),Toast.LENGTH_SHORT).show();


                                    }


                                }
                            });


                        }
                    }
                });
            }



        });

    }
}





















