package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterPage extends AppCompatActivity {

    String name,email,password;
    EditText n,e,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        n=findViewById(R.id.registerUserName);
        e=findViewById(R.id.registerEmail);
        p=findViewById(R.id.registerPassword);
    }

    public void RegisterAccount(View view) {
        name=n.getText().toString();
        email=e.getText().toString();
        password=p.getText().toString();
        if(name.length()>0 && email.length()>0 && password.length()>=7){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        UserClass u1=new UserClass(name);
                        HashMap<String,String> pMap=u1.getHashMap();
                        FirebaseUser u=FirebaseAuth.getInstance().getCurrentUser();
                        if(u!=null)
                        {
//                            progressBar.setVisibility(View.INVISIBLE);
                            FirebaseDatabase.getInstance().getReference().child(u.getUid()).setValue(pMap);
                            finish();
                            startActivity(new Intent(RegisterPage.this,LandingPage.class));
                        }
                    }
                    else
                    {
                        new AlertDialog.Builder(getApplicationContext())
                                .setTitle("Error!")
                                .setMessage("Something went wrong. Please try again.")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Ok",null)
                                .show();

                    }
                }
            });
        }
        else{
            if(name.length()<0 || email.length()<0){
                new AlertDialog.Builder(this)
                        .setTitle("Error!")
                        .setMessage("Fill all the details and try again.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Okay",null)
                        .show();
            }
            else{
                new AlertDialog.Builder(this)
                        .setTitle("Invalid Password!")
                        .setMessage("Password must be atleast 7 characters long.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Okay",null)
                        .show();
            }

        }
    }


}