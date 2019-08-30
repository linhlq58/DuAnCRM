package com.totnghiepluon.duancrm.Tasks;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.Models.Task;
import com.totnghiepluon.duancrm.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.TaskViewHolder> {
    private Activity context;
    private ArrayList<Task> listTask;

    public ListTaskAdapter(Activity context, ArrayList<Task> listTask) {
        this.context = context;
        this.listTask = listTask;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = context.getLayoutInflater().inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String info = listTask.get(position).getTaskTime() + ", " + listTask.get(position).getTaskDate() + " - " + listTask.get(position).getTaskCustomer();

        holder.tvName.setText(listTask.get(position).getTaskDesc());
        holder.tvInfo.setText(info);
    }

    @Override
    public int getItemCount() {
        return listTask.size();
    }

    public void refreshData(ArrayList<Task> listTask) {
        this.listTask = listTask;
        notifyDataSetChanged();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvInfo;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }
    }
}
