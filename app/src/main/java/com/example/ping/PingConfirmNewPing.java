package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PingConfirmNewPing extends AppCompatActivity {

    ListView listView;
    ArrayList<CompanionSwitchClass> arrayList=new ArrayList<>();
    ArrayList<String> companionIds=new ArrayList<>();

    CustomAdaptorSingleValue customAdaptor;
    CompanionSwitchClass u1;
    Intent i;
    static ArrayList<String> selectedIds=new ArrayList<>();
    String address,desc,lat,lng,visible,ping_back_id="",ping_id_delete="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_confirm_new_ping);
        i=getIntent();
        address=i.getStringExtra("address");
        desc=i.getStringExtra("desc");
        lat=i.getStringExtra("lat");
        lng=i.getStringExtra("long");
        visible=i.getStringExtra("visible");
        if(i.hasExtra("ping_back_id"))
            ping_back_id=i.getStringExtra("ping_back_id");
        if(i.hasExtra("ping_id_delete"))
            ping_id_delete=i.getStringExtra("ping_id_delete");
        listView=findViewById(R.id.companionsListView);

        GetTheIDS();
    }

    private void fillTheList(String id,int n) {

        FirebaseDatabase.getInstance().getReference().child(id).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {

                    CompanionSwitchClass c=new CompanionSwitchClass(task.getResult().getValue().toString(),true);

                    arrayList.add(c);
                    if(n<(companionIds.size()-1))
                    {

                        fillTheList(companionIds.get(n+1),n+1);
                    }
                    else
                    {
                        setAdaptor();
                    }
                }
            }
        });
    }

    void GetTheIDS()
    {
        FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
        if(u!=null)
        {

            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(u.getUid()).child("companions");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    companionIds.clear();
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        if(snapshot1.getValue().toString().compareTo(ping_back_id)!=0)
                            companionIds.add(snapshot1.getValue().toString());
                        Toast.makeText(PingConfirmNewPing.this, snapshot1.getValue().toString(), Toast.LENGTH_SHORT).show();
                    }
                    fillTheList(companionIds.get(0),0);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    void setAdaptor()
    {
        try {
            customAdaptor = new CustomAdaptorSingleValue(this,arrayList);
            listView.setAdapter(customAdaptor);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void companionListNext(View view) {
        View v;
        SwitchCompat sw;
        selectedIds.clear();
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        for(int i=0;i<listView.getCount();i++)
        {
            v=listView.getChildAt(i);
            sw=v.findViewById(R.id.customPingListSwitch);
            if(sw.isChecked())
            {
                selectedIds.add(companionIds.get(i));

            }
        }
        Intent intent=new Intent(this,PingConfirmNewPing_PingBacks.class);
        intent.putExtra("visible",visible);
        intent.putExtra("lat",lat);
        intent.putExtra("long",lng);
        intent.putExtra("desc",desc);
        intent.putExtra("address",address);
        if(i.hasExtra("ping_back_id"))
            intent.putExtra("ping_back_id",ping_back_id);
        if(i.hasExtra("ping_id_delete"))
            intent.putExtra("ping_id_delete",ping_id_delete);
        startActivity(intent);
    }
}