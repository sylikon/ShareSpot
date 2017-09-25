package com.maciek.facebooktest;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Geezy on 23.09.2017.
 */

public class SpotList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> userList;

    public SpotList(Activity context, List<User> userList){
        super(context, R.layout.list_layout, userList);
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textView33);
        TextView textViewSpotNumber = (TextView) listViewItem.findViewById(R.id.textView22);
        TextView textViewContact = (TextView) listViewItem.findViewById(R.id.textView44);

        User user = userList.get(position);

        textViewName.setText(user.getName());
        textViewSpotNumber.setText(String.valueOf(user.getSpot().getNumber()) +" cena: " + String.valueOf(user.getSpot().getPrice()) +" PLN/msc");
        textViewContact.setText("tutaj jakiśtam kontakt");
        return listViewItem;
    }
}
