package com.totnghiepluon.duancrm.Customers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.totnghiepluon.duancrm.AddCustomer;
import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.Base.StartApplication;
import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomersFragment extends BaseFragment implements View.OnClickListener {
    private DatabaseHelper db;

    private RecyclerView recyclerView;
    private ListCustomerAdapter listCustomerAdapter;
    private ArrayList<CustomerInfo> listCustomer;
    private Button mAddLead;

    public static CustomersFragment createInstance() {

        Bundle args = new Bundle();

        CustomersFragment fragment = new CustomersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_customers;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        recyclerView = rootView.findViewById(R.id.rcv_customer);
        mAddLead = rootView.findViewById(R.id.customer_add);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        db = StartApplication.getDb();
        listCustomer = db.getAllCustomers();

        listCustomerAdapter = new ListCustomerAdapter(getActivity(), listCustomer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(listCustomerAdapter);

        mAddLead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.customer_add) {
            changeActivity();
        }
    }

    private void changeActivity() {
        Intent intent = new Intent(getActivity(), AddCustomer.class);
        intent.putExtra(Constants.EXTRAS, true);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            listCustomerAdapter .refreshData(db.getAllCustomers());
        }
    }
}
