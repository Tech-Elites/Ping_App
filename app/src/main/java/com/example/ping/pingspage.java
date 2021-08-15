package com.example.ping;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pingspage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pingspage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pingspage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pingspage.
     */
    // TODO: Rename and change types and number of parameters
    public static pingspage newInstance(String param1, String param2) {
        pingspage fragment = new pingspage();
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

    Button companions, pingbacks;
    CustomAdaptorHomeList customAdaptor;
    static ArrayList<PingRequest> arrayList;
    ListView listView;
    FirebaseUser user;
    ProgressBar progressBar;
    boolean inCompanion=true;
    void onCompanionClick(){
        progressBar.setVisibility(View.VISIBLE);
        companions.setBackgroundColor(companions.getContext().getResources().getColor(R.color.myblue));
        pingbacks.setBackgroundColor(pingbacks.getContext().getResources().getColor(R.color.white));
        arrayList=new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("ping_from_");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    PingRequest temp;
                    temp=snapshot1.getValue(PingRequest.class);
                    arrayList.add(temp);
                }
                customAdaptor=new CustomAdaptorHomeList(getContext(),arrayList);
                listView.setAdapter(customAdaptor);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    void onPingbacksClick(){
        progressBar.setVisibility(View.VISIBLE);
        pingbacks.setBackgroundColor(Color.parseColor("#3C7BFB"));
        companions.setBackgroundColor(Color.parseColor("#FFFFFF"));
        arrayList=new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("ping_back_from_");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    PingRequest temp;
                    temp=snapshot1.getValue(PingRequest.class);

                    arrayList.add(temp);
                }
                customAdaptor=new CustomAdaptorHomeList(getContext(),arrayList);
                listView.setAdapter(customAdaptor);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        companions=getView().findViewById(R.id.homeCompanions);
        pingbacks=getView().findViewById(R.id.homePingBacks);
        listView=getView().findViewById(R.id.homePageListView);
        inCompanion=true;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListClick(position);
            }
        });
        user= FirebaseAuth.getInstance().getCurrentUser();
        progressBar=getView().findViewById(R.id.homePageProgress);
        companions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onCompanionClick();
                inCompanion=true;
            }
        });
        pingbacks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onPingbacksClick();
                inCompanion=false;
            }
        });
        onCompanionClick();
    }
    void onListClick(int position)
    {
        //call the next activity
        Intent i=new Intent(getActivity(),ViewPing.class);
        i.putExtra("index",position);
        i.putExtra("inCompanion",inCompanion);
        startActivity(i);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pingspage, container, false);
    }
}