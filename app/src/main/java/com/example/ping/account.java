package com.example.ping;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class account extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public account() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment account.
     */
    // TODO: Rename and change types and number of parameters
    public static account newInstance(String param1, String param2) {
        account fragment = new account();
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

    String name;
    int connections=0;
    FirebaseUser user;
    TextView nameTV, connectionsTV, closeTV;

    ArrayList<Ping> arrayList=new ArrayList<>();
    CustomAdaptorAccountPagePings customAdaptor;
    ListView listView;
    ProgressBar progressBar;
    ImageView imageNoResult;
    TextView textNoResult;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar=getActivity().findViewById((R.id.AccountPageProgress));
        progressBar.setVisibility(View.VISIBLE);
        nameTV=getActivity().findViewById(R.id.accountPageName);
        connectionsTV=getActivity().findViewById(R.id.accountPageConnections);
        user=FirebaseAuth.getInstance().getCurrentUser();
        closeTV=getActivity().findViewById(R.id.accountPageCloseFriends);
        listView=getActivity().findViewById(R.id.accountPageListView);
        imageNoResult=getActivity().findViewById(R.id.accountNoresultImage);
        textNoResult=getActivity().findViewById(R.id.accountNoresultText);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("name");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name=snapshot.getValue().toString();
                findConnections();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void findConnections(){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("companions");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    connections+=1;
                }
                searchPings();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void searchPings(){
        nameTV.setText(name);
        connectionsTV.setText("Close Friends: 0");
        connectionsTV.setText("Connections: "+connections);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("pings");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Ping temp=new Ping();
                    temp=snapshot1.getValue(Ping.class);
                    arrayList.add(temp);
                }
                fillPingList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void fillPingList(){
        if(arrayList.isEmpty()){
            imageNoResult.setVisibility(View.VISIBLE);
            textNoResult.setVisibility(View.VISIBLE);
        }
        customAdaptor = new CustomAdaptorAccountPagePings(getContext(),arrayList);
        listView.setAdapter(customAdaptor);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}