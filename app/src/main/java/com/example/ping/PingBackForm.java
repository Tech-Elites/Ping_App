package com.example.ping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;

public class PingBackForm extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng myLocation;
    double lat,lng;
    TextView addressView;
    EditText desc;
    SwitchCompat visibile;
    ProgressBar progressBar;
    Button submit;
    int index;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_back_form);
        intent=getIntent();
        progressBar=findViewById(R.id.progressbarPingBack);
        progressBar.setVisibility(View.VISIBLE);
        addressView=findViewById(R.id.addressPingBackFormPage);
        desc=findViewById(R.id.PingBackFormDescription);
        visibile=findViewById(R.id.privateSwitchPingBackForm);
        submit=findViewById(R.id.createPingPingBackForm);
        submit.setEnabled(false);
        lat=Double.parseDouble(intent.getStringExtra("lat"));
        lng=Double.parseDouble(intent.getStringExtra("lng"));
        index=intent.getIntExtra("index",-1);
        LocationServices();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                           }
        }
    }
    void LocationServices()
    {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(PingBackForm.this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    float distance[]=new float[1];
                    Location.distanceBetween(lat,lng,myLocation.latitude,myLocation.longitude,distance);
                    if(distance[0]>150)
                    {
                        new AlertDialog.Builder(PingBackForm.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Alert!")
                                .setMessage("You are not close to the region of this ping to ping back..")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        submit.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        addressView.setText(addresses.get(0).getAddressLine(0));
                    }
                    //addressView.setText(addresses.get(0).getAddressLine(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                locationManager.removeUpdates(locationListener);

                //this method is called when there is a slight change in the user’s         //location
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }
    public void SubmitPingBack(View view)
    {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            if (TextUtils.isEmpty(desc.getText().toString())) {
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Alert!")
                        .setMessage("Fill all the details before sending a ping!!")
                        .setPositiveButton("Okay", null)
                        .show();
            }
            else
            {
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Alert!")
                        .setMessage("Are you sure you want to proceed with this ping_back??")
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String lat=""+myLocation.latitude;
                                String lon=""+myLocation.longitude;
                                String visible="true";
                                if(visibile.isChecked())
                                {

                                    visible="false";
                                }
                                final String finalVisible=visible;
                                Intent i=new Intent(PingBackForm.this,PingConfirmNewPing.class);
                                //make sure that one person is not able to make consecutive pings from one location
                                i.putExtra("visible",finalVisible);
                                i.putExtra("lat",lat);
                                i.putExtra("long",lon);
                                i.putExtra("desc",desc.getText().toString());
                                i.putExtra("address",addressView.getText().toString());
                                if(!intent.hasExtra("fromAccount"))
                                {
                                    i.putExtra("ping_back_id",pingspage.arrayList.get(index).getUserid());
                                    i.putExtra("ping_id_delete",pingspage.pingIDForDelete.get(index));
                                }
                                else
                                {
                                    i.putExtra("ping_back_id",EachPersonAccount.uid);
                                }
                                startActivity(i);


                            }
                        })
                        .setNegativeButton("Nope",null)
                        .show();
            }
        }
    }

}