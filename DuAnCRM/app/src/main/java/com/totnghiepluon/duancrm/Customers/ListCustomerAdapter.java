package com.totnghiepluon.duancrm.Customers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListCustomerAdapter extends RecyclerView.Adapter<ListCustomerAdapter.CustomerViewHolder> {
    private Activity context;
    private ArrayList<CustomerInfo> listCustomer;
    private IListAction iListAction;

    public ListCustomerAdapter(Activity context, ArrayList<CustomerInfo> listCustomer, IListAction iListAction) {
        this.context = context;
        this.listCustomer = listCustomer;
        this.iListAction = iListAction;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = context.getLayoutInflater().inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, final int position) {
        String nameCompany = listCustomer.get(position).getmName() + " (" + listCustomer.get(position).getmCompany() + ")";
        int payState = listCustomer.get(position).getmPriority();
        holder.tvState.setText(Constants.CUSTOMER_LABEL[payState]);
        holder.tvName.setText(nameCompany);

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListAction.onCall(listCustomer.get(position).getmPhoneNumber());
            }
        });

        holder.btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListAction.onSms(listCustomer.get(position).getmPhoneNumber());
            }
        });

        holder.btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iListAction.onEmail(listCustomer.get(position).getmEmail());
            }
        });
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
        private TextView tvState;
        private ImageView btnCall;
        private ImageView btnSms;
        private ImageView btnEmail;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvState = itemView.findViewById(R.id.pay_state);
            tvName = itemView.findViewById(R.id.tv_name);
            btnCall = itemView.findViewById(R.id.btn_call);
            btnSms = itemView.findViewById(R.id.btn_sms);
            btnEmail = itemView.findViewById(R.id.btn_email);
        }
    }

    public interface IListAction {
        void onCall(String phoneNumber);

        void onSms(String phoneNumber);

        void onEmail(String email);
    }
}
