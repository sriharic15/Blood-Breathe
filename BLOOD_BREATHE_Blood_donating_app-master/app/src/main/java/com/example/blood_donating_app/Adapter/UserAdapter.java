package com.example.blood_donating_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blood_donating_app.Model.User;
import com.example.blood_donating_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<User> userList;
Intent chooser=null;
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_displayed_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserAdapter.ViewHolder holder, int position)
    {
        final User user = userList.get(position);
        holder.useremail.setText(user.getMail());
        holder.username.setText(user.getName());
        holder.usernumber.setText(user.getNumber());
        holder.bloodGroup.setText(user.getBloodgroup());
        holder.usercity.setText(user.getCity());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username,useremail,bloodGroup,usernumber,usercity;
        public Button emailnow,callnow,mapnow;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.Username);
            bloodGroup = itemView.findViewById(R.id.userbloodgroup);
            useremail = itemView.findViewById(R.id.useremail);
            usernumber = itemView.findViewById(R.id.userPhonenumber);
            usercity = itemView.findViewById(R.id.usercity);
            emailnow = itemView.findViewById(R.id.emailnow);
            mapnow = itemView.findViewById(R.id.Mapnow);
            callnow = itemView.findViewById(R.id.callnow);

emailnow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
                    {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        String mail = reference.child("mail").getKey().toLowerCase();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_EMAIL  , new String[]{mail});
                        intent.setData(Uri.parse("mailto:"));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Its an Emergency!!! ...Requesting for blood" );
                        intent.putExtra(Intent.EXTRA_TEXT, "DONATE BLOOD AND SAVE LIFE");
                        chooser = Intent.createChooser(intent,"Send mail");
                        intent.setType("message/rfc822");
                        Intent i = (Intent.createChooser(intent,"choose an client"));
                        context.startActivity(i);
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

    }
});
            mapnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            String url = "http://maps.google.com/maps?daddr="+ snapshot.child("city").getValue().toString();
                            Intent intent13 = new Intent(android.content.Intent.ACTION_VIEW);
                            intent13.setData(Uri.parse(url));
                           context.startActivity(intent13);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
            });

      callnow.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                      .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
              ref.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                      DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                      String number = snapshot.child("number").getValue().toString();
                      String numberOnly= number.replaceAll("[^0-9]", "");
                      Intent intent = new Intent(Intent.ACTION_DIAL);
                      intent.setData(Uri.parse("tel:"+numberOnly));
                      context.startActivity(intent);
                  }

                  @Override
                  public void onCancelled(@NonNull @NotNull DatabaseError error) {

                  }
              });

          }
      });



        }
    }
}
