package com.example.ping;

import android.Manifest;
import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    Button setLocation,createPing;
    LocationManager locationManager;
    LocationListener locationListener;
    TextView addressView;
    EditText description;
    LatLng myLocation;
    SwitchCompat switchCompat;
    String defaultAddress="Select your address-";
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLocation=getView().findViewById(R.id.buttonsetLocationHome);
        createPing=getView().findViewById(R.id.createPingHome);
        addressView=getView().findViewById(R.id.addressHomePage);
        switchCompat=getView().findViewById(R.id.privateSwitchHome);
        description=getView().findViewById(R.id.homeDescription);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), ""+isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocation();
            }
        });
        createPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPing();
            }
        });
        LocationServices();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
    void setLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Toast.makeText(getActivity(), "Here in location", Toast.LENGTH_SHORT).show();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }
    void createPing()
    {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            if(addressView.getText().toString().compareTo(defaultAddress)==0|| TextUtils.isEmpty(description.getText().toString()))
            {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Alert!")
                        .setMessage("Fill all the details before sending a ping!!")
                        .setPositiveButton("Okay",null)
                        .show();
            }
            else
            {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("pings");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        int count=0;
                        for(DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            double lat=0,lng=0;
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
                            }
                            float result[]=new float[1];
                            Location.distanceBetween(lat,lng,myLocation.latitude,myLocation.longitude,result);
                            Toast.makeText(getActivity(), "distance"+result[0], Toast.LENGTH_SHORT).show();
                            if(result[0]<150)
                            {
                                count++;
                            }

                        }
                        if(count==0)
                        {
                            new AlertDialog.Builder(getActivity())
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Alert!")
                                    .setMessage("Are you sure you want to proceed with this ping??")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            String lat=""+myLocation.latitude;
                                            String lon=""+myLocation.longitude;
                                            String visible="true";
                                            if(switchCompat.isChecked())
                                            {
                                                Toast.makeText(getActivity(), "Activated", Toast.LENGTH_SHORT).show();
                                                visible="false";
                                            }
                                            final String finalVisible=visible;
                                            Intent i=new Intent(getActivity(),PingConfirmNewPing.class);
                                            //make sure that one person is not able to make consecutive pings from one location
                                            i.putExtra("visible",finalVisible);
                                            i.putExtra("lat",lat);
                                            i.putExtra("long",lon);
                                            i.putExtra("desc",description.getText().toString());
                                            i.putExtra("address",addressView.getText().toString());
                                            startActivity(i);


                                        }
                                    })
                                    .setNegativeButton("Nope",null)
                                    .show();
                        }
                        else
                        {
                            new AlertDialog.Builder(getActivity())
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Alert!")
                                    .setMessage("You already have a ping in this location you cannot create one !")
                                    .setPositiveButton("Okay", null)
                                    .show();

                        }
                        
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }
    }
    void LocationServices()
    {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                Geocoder geocoder;
                Toast.makeText(getActivity(), "Here in on location changed", Toast.LENGTH_SHORT).show();
                List<Address> addresses = null;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    addressView.setText(addresses.get(0).getAddressLine(0));
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


}