package com.example.ping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class PingConfirmNewPing_PingBacks extends AppCompatActivity {

    ListView listView;
    ArrayList<PingBackImageSwitchClass> arrayList=new ArrayList<>();
    CustomAdaptorNameDescSwitch customAdaptor;
    PingBackImageSwitchClass u1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_confirm_new_ping__ping_backs);

        listView=findViewById(R.id.newPingPingbackListView);
        u1=new PingBackImageSwitchClass("Harsh","Very good",true);
        arrayList.add(u1);
        arrayList.add(u1);
        customAdaptor = new CustomAdaptorNameDescSwitch(this,arrayList);
        listView.setAdapter(customAdaptor);
    }

    public void newPingPingbackSkip(View view) {

    }

    public void newPingPingbackProceed(View view) {

    }
}