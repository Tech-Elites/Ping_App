package com.example.ping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class CustomAdaptorNameDescSwitch extends ArrayAdapter<PingBackImageSwitchClass> {

    public CustomAdaptorNameDescSwitch(@NonNull Context context, ArrayList<PingBackImageSwitchClass> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;


        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_adaptor_template_namedescimage_switch, parent, false);
        }

        try{
            PingBackImageSwitchClass currentUser = getItem(position);

            TextView tname = currentItemView.findViewById(R.id.customLay2Name);
            TextView descname = currentItemView.findViewById(R.id.customLay2Desc);
            SwitchCompat sname = currentItemView.findViewById(R.id.customLay2Switch);

            boolean status=currentUser.isStatus();
            sname.setChecked(status);
            tname.setText(currentUser.getName());
            descname.setText(currentUser.getDescription());

            tname.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                }
            });

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}
