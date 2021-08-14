package com.example.ping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EachPersonAccount extends AppCompatActivity {

    TextView tv;
    String uid,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_person_account);

        tv=findViewById(R.id.EachPersonViewName);
        uid=getIntent().getStringExtra("uid");
        name=getIntent().getStringExtra("name");
        tv.setText(uid);
    }
}