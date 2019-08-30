package com.totnghiepluon.duancrm.Tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.totnghiepluon.duancrm.AddCustomer;
import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.Base.StartApplication;
import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.Models.Task;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TasksFragment extends BaseFragment implements View.OnClickListener {
    private DatabaseHelper db;

    private RelativeLayout rtlEmpty;
    private RecyclerView recyclerView;
    private ListTaskAdapter listTaskAdapter;
    private Button btnAdd;

    private ArrayList<Task> listTask;

    public static TasksFragment createInstance() {

        Bundle args = new Bundle();

        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tasks;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        rtlEmpty = rootView.findViewById(R.id.rtl_empty);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        btnAdd = rootView.findViewById(R.id.btn_add);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        db = StartApplication.getDb();

        listTask = db.getAllTasks();
        listTaskAdapter = new ListTaskAdapter(getActivity() , listTask);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(listTaskAdapter);
        btnAdd.setOnClickListener(this);
        checkEmpty();
    }

    private void checkEmpty() {
        rtlEmpty.setVisibility(listTask.size() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                changeActivity();
                break;
        }
    }

    private void changeActivity() {
        Intent intent = new Intent(getActivity(), AddTaskActivity.class);
        startActivityForResult(intent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            listTask = db.getAllTasks();
            listTaskAdapter.refreshData(listTask);

            checkEmpty();
        }
    }
}
