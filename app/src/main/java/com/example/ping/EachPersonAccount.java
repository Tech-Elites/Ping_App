package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EachPersonAccount extends AppCompatActivity {

    TextView nameTV;
    static String uid,name;

    static ArrayList<Ping> arrayList=new ArrayList<>();
    CustomAdaptorAccountPagePings customAdaptor;
    ListView listView;
    ProgressBar progressBar;

    void setupDetails(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(uid).child("pings");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Ping temp=new Ping();
                    temp=snapshot1.getValue(Ping.class);
                    if(temp.getVisible().compareTo("true")==0)
                        arrayList.add(temp);
                }

                customAdaptor=new CustomAdaptorAccountPagePings(EachPersonAccount.this,arrayList);
                listView.setAdapter(customAdaptor);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_person_account);

        nameTV=findViewById(R.id.EachaccountPageName);
        listView=findViewById(R.id.EachaccountPageListView);
        progressBar=findViewById(R.id.EachAccountPageProgress);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OnClickList(position);
            }
        });
        uid=getIntent().getStringExtra("uid");
        name=getIntent().getStringExtra("name");
        nameTV.setText(name);
        setupDetails();
    }
    void OnClickList(int position)
    {
        Intent i=new Intent(EachPersonAccount.this,ViewPing.class);
        i.putExtra("index",position);
        i.putExtra("inCompanion",true);
        i.putExtra("fromAccount",true);
        startActivity(i);
    }

}