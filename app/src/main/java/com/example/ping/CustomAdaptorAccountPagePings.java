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

public class CustomAdaptorAccountPagePings extends ArrayAdapter<Ping> {

    public CustomAdaptorAccountPagePings(@NonNull Context context, ArrayList<Ping> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;


        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_template_accountpage_pings, parent, false);
        }

        try{
            Ping currentUser = getItem(position);

            TextView tname = currentItemView.findViewById(R.id.accountPingCustomLayDesc);
            TextView tadd = currentItemView.findViewById(R.id.accountPingCustomLayAdd);

            tname.setText(currentUser.getDesc());
            tadd.setText("@ "+currentUser.getAddress());

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}
