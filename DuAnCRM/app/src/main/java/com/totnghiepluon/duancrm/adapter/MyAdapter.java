package com.totnghiepluon.duancrm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.totnghiepluon.duancrm.Customers.CustomerInfo;
import com.totnghiepluon.duancrm.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomerHolder> {
    private List<CustomerInfo> customerInfoList;
    private Context context;
    private LayoutInflater inflater;

    public MyAdapter(List<CustomerInfo> customerInfoList, Context context) {
        this.customerInfoList = customerInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        return new CustomerHolder(inflater.inflate(R.layout.layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHolder holder, int position) {
        Log.d("huybv", customerInfoList.get(position).getmID() + "");
        holder.company.setText(customerInfoList.get(position).getmCompany());
        holder.name.setText(customerInfoList.get(position).getmName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("Huybv","long clicked");
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Huybv","Clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerInfoList.size();
    }

    public void updateList(List<CustomerInfo> customerInfoList) {
        this.customerInfoList = customerInfoList;
    }

    public class CustomerHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView company;

        public CustomerHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            company = itemView.findViewById(R.id.tv_company);
        }
    }
}
