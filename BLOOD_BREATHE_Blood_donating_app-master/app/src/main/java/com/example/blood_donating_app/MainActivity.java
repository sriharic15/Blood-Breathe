package com.example.blood_donating_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import  androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.blood_donating_app.Adapter.UserAdapter;
import com.example.blood_donating_app.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
private DrawerLayout drawerLayout;
private Toolbar toolbar;
private NavigationView nav_view;
private CircleImageView nav_profile_image;
private TextView nav_fullname,nav_email,nav_bloodgroup,nav_city,nav_number;
RecyclerView recyclerView;
private List<User>userList;
private UserAdapter userAdapter;
private ProgressBar progressBar;



    private DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Blood Donation App");
        drawerLayout = findViewById(R.id.draweLayout);
        nav_view = findViewById(R.id.nav_view);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        userList = new ArrayList<>();
    userAdapter = new UserAdapter(MainActivity.this,userList);

    recyclerView.setAdapter(userAdapter);

   DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                           .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
   ref.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
           DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
           Query query = reference.orderByChild("type").equalTo("donor");
           query.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                   userList.clear();
                   for(DataSnapshot dataSnapshot : snapshot.getChildren())
                   {
                       User user = dataSnapshot.getValue(User.class);
                       userList.add(user);
                   }
                   userAdapter.notifyDataSetChanged();
                   progressBar.setVisibility(View.GONE);
               }

               @Override
               public void onCancelled(@NonNull @NotNull DatabaseError error) {

               }
           });
       }

       @Override
       public void onCancelled(@NonNull @NotNull DatabaseError error) {

       }
   });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(this);

        progressBar = findViewById(R.id.progressbar);


        nav_fullname = nav_view.getHeaderView(0).findViewById(R.id.nav_user_fullname);
        nav_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_bloodgroup = nav_view.getHeaderView(0).findViewById(R.id.nav_user_bloodgroup);
        nav_number = nav_view.getHeaderView(0).findViewById(R.id.nav_user_number);
        nav_city = nav_view.getHeaderView(0).findViewById(R.id.nav_user_city);

        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String name = snapshot.child("name").getValue().toString();
                    nav_fullname.setText(name);

                    String mail = snapshot.child("mail").getValue().toString();
                    nav_email.setText(mail);

                    String bloodgroup = snapshot.child("bloodgroup").getValue().toString();
                    nav_bloodgroup.setText(bloodgroup);

                    String number = snapshot.child("number").getValue().toString();
                    nav_number.setText(number);

                    String city = snapshot.child("city").getValue().toString();
                    nav_city.setText(city);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }




    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.profile:
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case  R.id.home:
                  finish();
                  break;

            case R.id.nearbybloodbanks:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=blood+banks+near+me&rlz=1C1CHBF_enIN960IN960&oq=blood+banks+near+me&aqs=chrome..69i57j0i10i457j0i402l2j0i10l6.8864j0j7&sourceid=chrome&ie=UTF-8&dlnr=1&sei=msj_YMGfAoqe4-EPgdGlgAE"));
                startActivity(intent1);
                finish();
                break;

            case R.id.aplus:
                Intent intent2 = new Intent(MainActivity.this,CategorySelectedActivity.class);
                intent2.putExtra("bloodgroup","A+");
                startActivity(intent2);
                break;

            case R.id.aminus:
                Intent intent3 = new Intent(MainActivity.this,CategorySelectedActivity.class);
                intent3.putExtra("bloodgroup","A-");
                startActivity(intent3);
                break;

            case R.id.bplus:
                Intent intent4 = new Intent(MainActivity.this,CategorySelectedActivity.class);
                intent4.putExtra("bloodgroup","B+");
                startActivity(intent4);
                break;

            case R.id.oplus:
                Intent intent5 = new Intent(MainActivity.this,CategorySelectedActivity.class);
                intent5.putExtra("bloodgroup","O+");
                startActivity(intent5);
                break;

            case R.id.ominus:
                Intent intent6 = new Intent(MainActivity.this,CategorySelectedActivity.class);
                intent6.putExtra("bloodgroup","O-");
                startActivity(intent6);
                break;

            case R.id.abplus:
                Intent intent7 = new Intent(MainActivity.this,CategorySelectedActivity.class);
                intent7.putExtra("bloodgroup","AB+");
                startActivity(intent7);
                break;

            case R.id.abminus:
                Intent intent8 = new Intent(MainActivity.this,CategorySelectedActivity.class);
                intent8.putExtra("bloodgroup","AB-");
                startActivity(intent8);
                break;

            case R.id.bminus:
                Intent intent9 = new Intent(MainActivity.this,CategorySelectedActivity.class);
                intent9.putExtra("bloodgroup","B-");
                startActivity(intent9);
                break;

            case R.id.seachhospitals:
                Intent intent10 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=hospitals+near+me&rlz=1C1CHBF_enIN960IN960&oq=hospita&aqs=chrome.2.69i57j0i433j0i433i457j0i402l2j46i433j0i433l3j0.6717j0j4&sourceid=chrome&ie=UTF-8"));
                startActivity(intent10);
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}