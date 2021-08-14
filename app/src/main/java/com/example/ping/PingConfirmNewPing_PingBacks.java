package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PingConfirmNewPing_PingBacks extends AppCompatActivity {

    ListView listView;
    ArrayList<PingBackImageSwitchClass> arrayList=new ArrayList<>();
    CustomAdaptorNameDescSwitch customAdaptor;
    PingBackImageSwitchClass u1;
    String address,desc,lat,lng,visible;
    ArrayList<String> selectedIdsStrangers=new ArrayList<>();
    ArrayList<String> alltheIds=new ArrayList<>();
    double lat_d,lng_d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_confirm_new_ping__ping_backs);
        Intent i=getIntent();
        address=i.getStringExtra("address");
        desc=i.getStringExtra("desc");
        lat=i.getStringExtra("lat");
        lat_d=Double.parseDouble(lat);
        lng=i.getStringExtra("long");
        lng_d=Double.parseDouble(lng);
        visible=i.getStringExtra("visible");
        listView=findViewById(R.id.newPingPingbackListView);
        /*
        /////////////////////////////////
        //////////////////////////////
        //////////////////////////////
        imlment the idea that in case of no near by pings display an image instead of a blank page
         */
        findThePings();
    }
    void findThePings()
    {
        FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    //all the ids
                    if(dataSnapshot.getKey().toString().compareTo(u.getUid())!=0)
                    {
                        //all the id info -name,pings
                        String name="";
                        Ping ping=null;
                        String id=dataSnapshot.getKey();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {

                            if(dataSnapshot1.getKey().toString().compareTo("name")==0)
                            {
                                name=dataSnapshot1.getValue().toString();
                            }
                            if(dataSnapshot1.getKey().toString().compareTo("pings")==0)
                            {

                                //all the pings random key children
                                for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                                {

                                    double lat=0,lng=0;
                                    String address="",desc="",visible="";
                                    for(DataSnapshot dataSnapshot3:dataSnapshot2.getChildren())
                                    {
                                        //all the info about each ping
                                        if(dataSnapshot3.getKey().toString().compareTo("lat")==0)
                                        {
                                            lat=Double.parseDouble(dataSnapshot3.getValue().toString());
                                        }
                                        if(dataSnapshot3.getKey().toString().compareTo("lng")==0)
                                        {
                                            lng=Double.parseDouble(dataSnapshot3.getValue().toString());
                                        }
                                        if(dataSnapshot3.getKey().toString().compareTo("desc")==0)
                                        {
                                            desc=dataSnapshot3.getValue().toString();
                                        }
                                        if(dataSnapshot3.getKey().toString().compareTo("address")==0)
                                        {
                                            address=dataSnapshot3.getValue().toString();
                                        }
                                        if(dataSnapshot3.getKey().toString().compareTo("visible")==0)
                                        {
                                            visible=dataSnapshot3.getValue().toString();
                                        }

                                    }
                                    if(visible.compareTo("true")==0)
                                    {
                                        float distance[]=new float[1];
                                        Location.distanceBetween(lng,lat,lng_d,lat_d,distance);
                                        //check condition here
                                        /*if(distance[0]<30)
                                        {

                                        }*/
                                        ping=new Ping(visible,desc,address,lat+"",lng+"");
                                        break;
                                    }
                                }
                            }

                        }
                        if(ping!=null)
                        {
                            alltheIds.add(id);
                            arrayList.add(new PingBackImageSwitchClass(name,ping.getDesc(),false));
                            Toast.makeText(PingConfirmNewPing_PingBacks.this, ""+name+" here"+id, Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void createAdaptor()
    {
        customAdaptor = new CustomAdaptorNameDescSwitch(this,arrayList);
        listView.setAdapter(customAdaptor);

    }

    public void newPingPingbackSkip(View view) {

    }

    public void newPingPingbackProceed(View view) {

    }
}