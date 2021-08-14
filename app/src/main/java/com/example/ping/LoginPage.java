package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    String email,password;
    EditText e,p;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        firebaseAuth = FirebaseAuth.getInstance();
        e=findViewById(R.id.loginEmail);
        p=findViewById(R.id.loginPassword);
    }

    void login(String email, String password){
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Error!")
                    .setMessage("Fill all the details and try again.")
                    .setPositiveButton("Okay",null)
                    .show();
        }
        else
        {
            //p.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        finish();
                        Intent i = new Intent(LoginPage.this,LandingPage.class);
//                        finish();
//                        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(LoginPage.this).toBundle());
                        startActivity(i);
                    }
                    else
                    {
                        new AlertDialog.Builder(LoginPage.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Error!")
                                .setMessage("Invalid login credentials. Please check your credentials and try again.")
                                .setPositiveButton("Okay",null)
                                .show();
                        //p.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }

    }

    public void StartLogin(View view) {
        email=e.getText().toString();
        password=p.getText().toString();
        login(email,password);

    }

    public void GoToRegister(View view) {
        startActivity(new Intent(this,RegisterPage.class));
    }
}