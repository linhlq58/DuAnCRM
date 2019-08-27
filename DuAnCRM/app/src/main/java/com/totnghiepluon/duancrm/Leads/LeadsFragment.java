package com.totnghiepluon.duancrm.Leads;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import com.totnghiepluon.duancrm.data.MyDatabase;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class LeadsFragment extends BaseFragment implements View.OnClickListener {
    private Button mAddLead;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private MyDatabase database;
    private List<CustomerInfo> customerInfoList;

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
        database = new MyDatabase(getActivity(), Constants.DATABASES, null, 1);
        recyclerView = rootView.findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAddLead.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        database.query("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.NAME + " VARCHAR(50), " + Constants.PHONE_NUMBER + " VARCHAR(20), " + Constants.COMPANY + " VARCHAR(100), " +
                Constants.EMAIL + " VARCHAR(50), " + Constants.ADDRESS + " VARCHAR(200), " + Constants.BIRTHDAY + " VARCHAR(20))");
//        customerInfoList = getDataFromDatabase();
        customerInfoList = new ArrayList<>();
        myAdapter = new MyAdapter(customerInfoList,getActivity());
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
//        database.query("INSERT INTO " + Constants.TABLE_NAME + " VALUES(NULL, '" + strings[2] + "')");
        customerInfoList = getDataFromDatabase();
        myAdapter.updateList(customerInfoList);
        myAdapter.notifyDataSetChanged();
    }

    private List<CustomerInfo> getDataFromDatabase() {
        Cursor data = database.getData("SELECT * FROM " + Constants.TABLE_NAME);
        List<CustomerInfo> list = new ArrayList<>();
        CustomerInfo customer;
        while (data.moveToNext()) {
            customer = new CustomerInfo(data.getString(1), data.getString(2), data.getString(3)
                    , data.getString(4), data.getString(5), data.getString(6), 0, data.getInt(0));
            list.add(customer);
        }
        return list;
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
        intent.putExtra(Constants.EXTRAS, false);
        startActivity(intent);
    }
}
