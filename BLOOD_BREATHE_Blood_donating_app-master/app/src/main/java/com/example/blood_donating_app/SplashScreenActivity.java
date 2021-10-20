 package com.example.blood_donating_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

 public class SplashScreenActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView title;
    private  TextView slogan;

    Animation topanimation,bottomanimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        title = findViewById(R.id.welcome);
        slogan = findViewById(R.id.slogan);
        topanimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomanimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo.setAnimation(topanimation);
        title.setAnimation(bottomanimation);
        slogan.setAnimation(bottomanimation);

        int SPLASH_SCREEN = 5000;

        new Handler().postDelayed(new Runnable() {
                                              @Override
                                              public void run() {
                                                  Intent intent = new Intent(SplashScreenActivity.this,Optionsignuplogin.class);
                                                  startActivity(intent);
                                                  finish();
                                              }
                                          },SPLASH_SCREEN);

    }
}