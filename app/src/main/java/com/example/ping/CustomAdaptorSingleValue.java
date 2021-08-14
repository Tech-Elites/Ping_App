package com.example.ping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class CustomAdaptorSingleValue extends ArrayAdapter<CompanionSwitchClass> {

    public CustomAdaptorSingleValue(@NonNull Context context, ArrayList<CompanionSwitchClass> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;


        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_adaptor_template_singlevalue, parent, false);
        }

        try{
            CompanionSwitchClass currentUser = getItem(position);

            TextView tname = currentItemView.findViewById(R.id.customPingListName);
            SwitchCompat sname = currentItemView.findViewById(R.id.customPingListSwitch);

            boolean status=currentUser.isStatus();
            sname.setChecked(status);
            tname.setText(currentUser.getName());

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}
