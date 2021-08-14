package com.example.ping;

import android.content.Context;
import android.content.Intent;
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

public class CustomAdaptorSearchPage extends ArrayAdapter<UserNameIDClass> {

    public CustomAdaptorSearchPage(@NonNull Context context, ArrayList<UserNameIDClass> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;


        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_template_search_view, parent, false);
        }

        try{
            UserNameIDClass currentUser = getItem(position);

            TextView tname = currentItemView.findViewById(R.id.searchCustomListName);
            tname.setText(currentUser.getName());
            tname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent i = new Intent(getContext(),EachPersonAccount.class);
                    i.putExtra("uid",currentUser.getUid());
                    i.putExtra("name",currentUser.getName());
                    getContext().startActivity(i);
                }
            });

        }catch (Exception e){
            System.out.println(e);
        }

        return currentItemView;
    }
}
