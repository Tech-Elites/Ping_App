package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationPage extends AppCompatActivity {

    ArrayList<PingRequest> arrayList;
    ListView listView;
    FirebaseUser user;
    NotifcationCustomAdaptor customAdaptor;
    TextView checkerTV;
    int count=0;

    void deleteNotification(int pos){
        String uidToDelete=arrayList.get(pos).getUserid();
        Toast.makeText(this, uidToDelete, Toast.LENGTH_SHORT).show();
        arrayList.remove(pos);
        customAdaptor=new NotifcationCustomAdaptor(this,arrayList);
        listView.setAdapter(customAdaptor);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("notifications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    PingRequest temp=snapshot1.getValue(PingRequest.class);
                    if(temp.getUserid().compareTo(uidToDelete)==0){
                       snapshot1.getRef().removeValue();
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        user= FirebaseAuth.getInstance().getCurrentUser();
        listView=findViewById(R.id.notificationListView);
        arrayList=new ArrayList<>();
        checkerTV=findViewById(R.id.notificationsEmpty);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("notifications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    PingRequest temp;
                    temp=snapshot1.getValue(PingRequest.class);
                    arrayList.add(temp);
                    count++;
                }
                checker();
                customAdaptor=new NotifcationCustomAdaptor(NotificationPage.this,arrayList);
                listView.setAdapter(customAdaptor);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        deleteNotification(pos);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(NotificationPage.this);
                        builder.setMessage("Are your sure you want to delete this notification?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();


                        return true;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    void checker(){
        if(count==0){
            checkerTV.setText("No new notifications!");
        }
        else{
            checkerTV.setText(count+" new notifications!");
        }
    }
}