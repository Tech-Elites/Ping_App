package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandingPage extends AppCompatActivity {

    Toolbar tb;
    Button notBut;
    int count=0;
    String username;

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        if(count>0){
            notBut.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notification_new_icon, 0, 0, 0);
            //notBut.setText(count);
        }
        //Refresh your stuff here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        tb=findViewById(R.id.myToolbar);
        setSupportActionBar(tb);

        notBut=findViewById(R.id.notificationIconButton);
//        notBut.setBackground(ContextCompat.getDrawable(this, R.drawable.notification));
        notBut.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notification, 0, 0, 0);
        username=FirebaseAuth.getInstance().getCurrentUser().getUid();
        boolean newNot=false;
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(username).child("notifications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    count++;
                }
                if(count>0){
                    notBut.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notification_new_icon, 0, 0, 0);
//                    notBut.setText(count);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void GoToNotifications(View view) {
        startActivity(new Intent(this,NotificationPage.class));
    }

    public void LogOut(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(LandingPage.this, LoginPage.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(LandingPage.this).toBundle());

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are your sure you want to log out?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}