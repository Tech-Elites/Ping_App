package com.example.ping;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.widget.SwitchCompat;

        import java.util.ArrayList;

public class NotifcationCustomAdaptor extends ArrayAdapter<PingRequest> {

    public NotifcationCustomAdaptor(@NonNull Context context, ArrayList<PingRequest> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;



        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout_notification, parent, false);
        }

        try{
            PingRequest currentUser = getItem(position);

            TextView tname = currentItemView.findViewById(R.id.notifcationCustomName);
            TextView taddr = currentItemView.findViewById(R.id.notifcationCustomAddress);

            tname.setText(currentUser.getName()+" accepted your request.");
            taddr.setText("@ "+currentUser.getAddress());

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}
