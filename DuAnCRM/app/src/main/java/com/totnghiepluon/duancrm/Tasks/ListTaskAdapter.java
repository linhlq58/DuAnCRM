package com.totnghiepluon.duancrm.Tasks;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Models.Customer;
import com.totnghiepluon.duancrm.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.TaskViewHolder> {
    private Activity context;
    private ArrayList<Customer> listCustomer;

    public ListTaskAdapter(Activity context, ArrayList<Customer> listCustomer) {
        this.context = context;
        this.listCustomer = listCustomer;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = context.getLayoutInflater().inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String nameCompany = listCustomer.get(position).getName() + " (" + listCustomer.get(position).getCompany() + ")";
        holder.tvName.setText(nameCompany);
    }

    @Override
    public int getItemCount() {
        return listCustomer.size();
    }

    public void refreshData(ArrayList<Customer> listCustomer) {
        this.listCustomer = listCustomer;
        notifyDataSetChanged();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
