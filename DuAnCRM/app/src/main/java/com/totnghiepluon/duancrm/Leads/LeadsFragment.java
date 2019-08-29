package com.totnghiepluon.duancrm.Leads;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.totnghiepluon.duancrm.AddCustomer;
import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.Customers.CustomerInfo;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.adapter.MyAdapter;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class LeadsFragment extends BaseFragment implements View.OnClickListener {
    private Button mAddLead;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private MyAdapter myAdapter;
    private List<CustomerInfo> customerInfoList;
    private int mSelectedPosition;

    public static LeadsFragment createInstance() {

        Bundle args = new Bundle();

        LeadsFragment fragment = new LeadsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_leads;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        mAddLead = rootView.findViewById(R.id.btn_add);
        db = new DatabaseHelper(getActivity());
        recyclerView = rootView.findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAddLead.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        customerInfoList = db.getAllLeads();
        myAdapter = new MyAdapter(customerInfoList, getActivity());
        myAdapter.setOnItemSelectedListener(new MyAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                mSelectedPosition = position;
                editCustomer(position);
            }
        });
        myAdapter.setOnItemLongClickListener(new MyAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                mSelectedPosition = position;
            }
        });
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    private void editCustomer(int position) {
        Intent intent = new Intent(getActivity(), AddCustomer.class);
        intent.putExtra(Constants.EDIT, position + 1);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
//        database.query("INSERT INTO " + Constants.TABLE_NAME + " VALUES(NULL, '" + strings[2] + "')");
        customerInfoList = db.getAllLeads();
        myAdapter.updateList(customerInfoList);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                changeActivity();
                break;
            default:
                break;
        }
    }

    private void changeActivity() {
        Intent intent = new Intent(getActivity(), AddCustomer.class);
        intent.putExtra(Constants.EXTRAS, false);
        startActivity(intent);
    }
}
