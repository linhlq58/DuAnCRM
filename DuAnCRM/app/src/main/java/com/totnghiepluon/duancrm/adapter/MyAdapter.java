package com.totnghiepluon.duancrm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomerHolder> {
    private List<CustomerInfo> customerInfoList;
    private Context context;
    private LayoutInflater inflater;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemLongClickListener mOnItemLongClickListener;

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
    public void onBindViewHolder(@NonNull CustomerHolder holder, final int position) {
        holder.company.setText(customerInfoList.get(position).getmCompany());
        holder.name.setText(customerInfoList.get(position).getmName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mOnItemLongClickListener == null) return false;
                mOnItemLongClickListener.onItemLongClicked(position);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemSelectedListener == null) return;
                mOnItemSelectedListener.onItemSelected(position);
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

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        mOnItemLongClickListener = longClickListener;
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

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClicked(int position);
    }
}
