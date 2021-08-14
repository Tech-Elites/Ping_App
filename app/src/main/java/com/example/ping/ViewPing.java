package com.example.ping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ViewPing extends AppCompatActivity {

    Button Ping_back_Accept;
    TextView desc,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ping);
        Ping_back_Accept=findViewById(R.id.viewPingAcceptOrPingBack);
        desc=findViewById(R.id.viewPingDesc);
        address=findViewById(R.id.viewPingAddress);
        Intent i=getIntent();
        boolean inCompanion=i.getBooleanExtra("inCompanion",false);
        int index=i.getIntExtra("index",0);
        if(inCompanion)
        {
            Ping_back_Accept.setText("Ping Back");
        }
        else
        {
            Ping_back_Accept.setText("Accept");
        }
        desc.setText(pingspage.arrayList.get(index).getDesc());
        address.setText(pingspage.arrayList.get(index).getAddress());

    }
}