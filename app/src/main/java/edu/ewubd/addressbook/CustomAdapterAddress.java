package edu.ewubd.addressbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapterAddress extends BaseAdapter {
    Context context;
    ArrayList<AddressList> arrayList;
    public CustomAdapterAddress(Context context, ArrayList<AddressList> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_layout_address, parent, false);

        TextView name = rowView.findViewById(R.id.tv_name);
        TextView phone = rowView.findViewById(R.id.tv_phone);

        AddressList allAddress = arrayList.get(position);

        name.setText(allAddress.getUsername());
        phone.setText(allAddress.getPhone());


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = allAddress.getId();
                Intent intent = new Intent(context, UpdateAddress.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("addressId", allAddress.getId());
                intent.putExtra("username", allAddress.getUsername());
                intent.putExtra("email", allAddress.getEmail());
                intent.putExtra("firstAddress", allAddress.getFirstAddress());
                intent.putExtra("secondAddress", allAddress.getSecondAddress());
                intent.putExtra("phone", allAddress.getPhone());
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
