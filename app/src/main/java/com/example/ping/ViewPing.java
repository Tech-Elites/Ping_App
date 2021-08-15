package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewPing extends AppCompatActivity {

    Button Ping_back_Accept;
    TextView desc,address;
    boolean inCompanion;
    int index;

    String currentUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ping);
        Ping_back_Accept=findViewById(R.id.viewPingAcceptOrPingBack);
        desc=findViewById(R.id.viewPingDesc);
        address=findViewById(R.id.viewPingAddress);
        Intent i=getIntent();
        inCompanion=i.getBooleanExtra("inCompanion",false);
        index=i.getIntExtra("index",0);
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
    public void OnClickPingBackAccept(View view)
    {
        if(inCompanion)
        {
            PingBackFunc();
        }
        else
        {
            PingRequest otherUser=pingspage.arrayList.get(index);
            String currentAcceptingUID=FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(currentAcceptingUID).child("name");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentUserName=snapshot.getValue().toString();
                    PingRequest toBeAddedPingRequest = new PingRequest(otherUser.getDesc(), otherUser.getAddress(), otherUser.getLat(), otherUser.getLng(),currentUserName,currentAcceptingUID);
                    addNotificationToOther(toBeAddedPingRequest,currentAcceptingUID);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



//            Toast.makeText(this, , Toast.LENGTH_SHORT).show();
            //pingspage.arrayList.get(index).getUserid();

        }
    }

    void addNotificationToOther(PingRequest toBeAddedPingRequest,String currentAcceptingUID){
        PingRequest otherUser=pingspage.arrayList.get(index);
        String otherPersonsUid=otherUser.getUserid();
        FirebaseDatabase.getInstance().getReference().child(otherPersonsUid).child("notifications").push().setValue(toBeAddedPingRequest.returnPingRequest());
//        Toast.makeText(this, pingspage.pingIDForDelete.get(index), Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference().child(currentAcceptingUID).child("ping_back_from_").child(pingspage.pingIDForDelete.get(index)).getRef().removeValue();
        finish();
    }

    void PingBackFunc()
    {
        Intent i=new Intent(this,PingBackForm.class);
        i.putExtra("lat",pingspage.arrayList.get(index).getLat());
        i.putExtra("lng",pingspage.arrayList.get(index).getLng());
        i.putExtra("index",index);
        finish();
        startActivity(i);

        /*FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("pings");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int count=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    double lat=0,lng=0;
                    String finalVisible="",desc="",address="";
                    for(DataSnapshot snapshot1:dataSnapshot.getChildren())
                    {
                        if(snapshot1.getKey().toString().compareTo("lat")==0)
                        {
                            lat=Double.parseDouble(snapshot1.getValue().toString());
                        }
                        if(snapshot1.getKey().toString().compareTo("lng")==0)
                        {
                            lng=Double.parseDouble(snapshot1.getValue().toString());
                        }
                        if(snapshot1.getKey().toString().compareTo("visible")==0)
                        {
                            finalVisible=(snapshot1.getValue().toString());
                        }
                        if(snapshot1.getKey().toString().compareTo("desc")==0)
                        {
                            desc=(snapshot1.getValue().toString());
                        }
                        if(snapshot1.getKey().toString().compareTo("address")==0)
                        {
                            address=(snapshot1.getValue().toString());
                        }

                    }
                    float result[]=new float[1];
                    Location.distanceBetween(lat,lng,Double.parseDouble(pingspage.arrayList.get(index).getLat()),Double.parseDouble(pingspage.arrayList.get(index).getLng()),result);

                    if(result[0]<150)
                    {
                        final String finalVisible_=finalVisible,address_=address,desc_=desc;
                        final  double lat_=lat,lng_=lng;
                        new AlertDialog.Builder(ViewPing.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Alert!")
                                .setMessage("You already have a ping in this location proceed to ping back that ping??")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i=new Intent(ViewPing.this,PingConfirmNewPing.class);
                                        //make sure that one person is not able to make consecutive pings from one location
                                        i.putExtra("visible",finalVisible_);
                                        i.putExtra("lat",""+lat_);
                                        i.putExtra("long",""+lng_);
                                        i.putExtra("desc",desc_);
                                        i.putExtra("address",address_);
                                        i.putExtra("ping_back_id",pingspage.arrayList.get(index).getUserid());
                                        startActivity(i);
                                    }
                                })
                                .show();
                        count++;
                    }

                }
                if(count==0)
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }


}