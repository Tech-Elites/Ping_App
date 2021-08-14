package com.example.ping;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search.
     */
    // TODO: Rename and change types and number of parameters
    public static search newInstance(String param1, String param2) {
        search fragment = new search();
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

    Button searchButton;
    EditText searchText;
    String searchVal;
    ArrayList<UserNameIDClass> arrayList;
    CustomAdaptorSearchPage customAdaptor;
    ListView listView;

    public void SearchUserName(){
        listView=getView().findViewById(R.id.searchPageListView);
        searchVal=searchText.getText().toString();
        arrayList=new ArrayList<>();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    String temp;
                    temp=snapshot1.child("name").getValue().toString();

                    if(searchVal.toLowerCase().compareTo(temp.substring(0,searchVal.length()).toLowerCase())==0){
//                        Toast.makeText(getActivity(), temp, Toast.LENGTH_LONG).show();
                        UserNameIDClass tempClass=new UserNameIDClass(temp,snapshot1.getKey());
                        arrayList.add(tempClass);
                    }
                }
                customAdaptor=new CustomAdaptorSearchPage(getContext(),arrayList);
                listView.setAdapter(customAdaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchButton=getView().findViewById(R.id.searchPageIdButton);
        searchText=getView().findViewById(R.id.searchPagePersonName);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SearchUserName();
            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}