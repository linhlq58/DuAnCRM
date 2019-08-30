package com.totnghiepluon.duancrm.Labels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.totnghiepluon.duancrm.AddCustomer;
import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.adapter.MyAdapter;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class LabelActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "Huybv";
    private RecyclerView recyclerView;
    private TextView labelText;
    private Button closeButton;
    private MyAdapter myAdapter;
    private DatabaseHelper db;
    private List<CustomerInfo> customerInfoList;
    private String mLabel;
    private int currentType;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_label;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        labelText = findViewById(R.id.txt_label);
        closeButton = findViewById(R.id.label_close);
        recyclerView = findViewById(R.id.recyclerview);
        db = new DatabaseHelper(this);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mLabel = getIntent().getStringExtra(Constants.LABEL);
        labelText.setText(mLabel);
        closeButton.setOnClickListener(this);
        customerInfoList = new ArrayList<>();
        if (mLabel.equals(Constants.CUSTOMER_LABEL[0])) {
            customerInfoList = db.getAllLeads();
            currentType = 0;
        } else {
            for (int i = 1; i < 5; i++) {
                if (mLabel.equals(Constants.CUSTOMER_LABEL[i])) {
                    currentType = i;
                    List<CustomerInfo> list = db.getAllCustomers();
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).getmPriority() == i) {
                            customerInfoList.add(list.get(j));
                            Log.d(TAG, "initData: "+list.get(j).getmID());
                        }
                    }
                }
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(customerInfoList, this);
        myAdapter.setOnItemSelectedListener(new MyAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                editCustomer(position);
            }
        });
        myAdapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
            }
        });
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void editCustomer(int position) {
        Intent intent = new Intent(this, AddCustomer.class);
//        Log.d("Huybv", customerInfoList.get(position).getmID()+ "" + customerInfoList.get(position).getmName());
        intent.putExtra(Constants.EDIT, customerInfoList.get(position).getmID());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.label_close) {
            finish();
        }
    }
}
