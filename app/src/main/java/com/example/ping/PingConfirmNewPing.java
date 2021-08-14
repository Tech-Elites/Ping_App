package com.example.ping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class PingConfirmNewPing extends AppCompatActivity {

    ListView listView;
    ArrayList<CompanionSwitchClass> arrayList=new ArrayList<>();
    CustomAdaptorSingleValue customAdaptor;
    CompanionSwitchClass u1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_confirm_new_ping);

        listView=findViewById(R.id.companionsListView);
        u1=new CompanionSwitchClass("Harsh",true);
        arrayList.add(u1);
        arrayList.add(u1);
        customAdaptor = new CustomAdaptorSingleValue(this,arrayList);
        listView.setAdapter(customAdaptor);
    }

    public void companionListNext(View view) {
    }
}