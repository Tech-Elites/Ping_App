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

public class CustomAdaptorHomeList extends ArrayAdapter<PingRequest> {

    public CustomAdaptorHomeList(@NonNull Context context, ArrayList<PingRequest> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;



        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_for_home_page, parent, false);
        }

        try{
            PingRequest currentUser = getItem(position);

            TextView tname = currentItemView.findViewById(R.id.HomeViewName);
            TextView tdesc = currentItemView.findViewById(R.id.HomeViewDesc);
            TextView taddr = currentItemView.findViewById(R.id.HomeViewAddress);

            tname.setText(currentUser.getName());
            tdesc.setText(currentUser.getDesc());
            taddr.setText("@ "+currentUser.getAddress());

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}
