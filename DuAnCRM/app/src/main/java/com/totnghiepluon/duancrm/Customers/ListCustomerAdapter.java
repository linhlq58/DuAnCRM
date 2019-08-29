package com.totnghiepluon.duancrm.Customers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListCustomerAdapter extends RecyclerView.Adapter<ListCustomerAdapter.CustomerViewHolder> {
    private Activity context;
    private ArrayList<CustomerInfo> listCustomer;

    public ListCustomerAdapter(Activity context, ArrayList<CustomerInfo> listCustomer) {
        this.context = context;
        this.listCustomer = listCustomer;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = context.getLayoutInflater().inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        String nameCompany = listCustomer.get(position).getmName() + " (" + listCustomer.get(position).getmCompany() + ")";
        holder.tvName.setText(nameCompany);
    }

    @Override
    public int getItemCount() {
        return listCustomer.size();
    }

    public void refreshData(ArrayList<CustomerInfo> listCustomer) {
        this.listCustomer = listCustomer;
        notifyDataSetChanged();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
