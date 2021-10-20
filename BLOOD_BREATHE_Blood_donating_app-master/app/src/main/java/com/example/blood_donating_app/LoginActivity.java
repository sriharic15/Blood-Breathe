package com.example.blood_donating_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    private EditText loginmail,loginpassword;
    private Button signin;
private ProgressDialog loader;
private FirebaseAuth mAuth;

private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton bbtn = findViewById(R.id.iBack);
        bbtn.setOnClickListener(v -> {

            Intent i = new Intent(LoginActivity.this,Optionsignuplogin.class);
            startActivity(i);
        });
           authStateListener= firebaseAuth -> {
               FirebaseUser user = mAuth.getCurrentUser();
               if(user !=null)
               {

                   Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                   startActivity(intent);
                   finish();
               }
           };
          loginmail = findViewById(R.id.loginmail);
          loginpassword =findViewById(R.id.loginpassword);
          signin=findViewById(R.id.signinbtn);
          loader=new ProgressDialog(this);
          mAuth = FirebaseAuth.getInstance();

          signin.setOnClickListener(v -> {
              final String email = loginmail.getText().toString().trim();
              final  String password = loginpassword.getText().toString().trim();

              if (TextUtils.isEmpty(email)) {
                  loginmail.setError("EMAIL IS REQUIRED");
              }
              if (TextUtils.isEmpty(password)) {
                  loginpassword.setError("EMAIL IS REQUIRED");
              }
              else {
                  loader.setMessage("LOGGING IN");
                  loader.setCanceledOnTouchOutside(false);
                  loader.show();

                  mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(LoginActivity.this,"LOG IN SUCCESSFULL",Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                             startActivity(intent);
                             finish();
                         }
                         else {
                             Toast.makeText(LoginActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();

                         }
                         loader.dismiss();
                      }
                  });
              }
          });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(authStateListener);
    }
}