package com.totnghiepluon.duancrm.Leads;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.totnghiepluon.duancrm.AddCustomer;
import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.adapter.MyAdapter;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.List;

public class LeadsFragment extends BaseFragment implements View.OnClickListener {
    private Button mAddLead;
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private MyAdapter myAdapter;
    private List<CustomerInfo> customerInfoList;
    private String username;

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
        if (getArguments() != null) {
            username = getArguments().getString(Constants.USERNAME, Constants.MANAGER);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAddLead.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        getListLeadByUser();
        myAdapter = new MyAdapter(customerInfoList, getActivity());
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

    private void getListLeadByUser() {
        if (username.equals(Constants.MANAGER)) {
            customerInfoList = db.getAllLeads();
        } else
            customerInfoList = db.getLeadByUser(username);
    }

    private void editCustomer(int position) {
        Intent intent = new Intent(getActivity(), AddCustomer.class);
        Log.d("Huybv", "editCustomer: " + customerInfoList.get(position).getmName());
        intent.putExtra(Constants.USERNAME, username);
        intent.putExtra(Constants.EDIT, customerInfoList.get(position).getmID());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
//        database.query("INSERT INTO " + Constants.TABLE_NAME + " VALUES(NULL, '" + strings[2] + "')");
        getListLeadByUser();
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
        if (username == null) {
            Log.d("Huybv", "changeActivity: ");
        }
        else
        Log.d("Huybv", "changeActivity: "+ username);
        intent.putExtra(Constants.USERNAME, username);
        startActivity(intent);
    }
}
