package com.totnghiepluon.duancrm.Customers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.totnghiepluon.duancrm.AddCustomer;
import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.Base.StartApplication;
import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.adapter.MyAdapter;
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
    private String username;

    private BroadcastReceiver receiver;

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
        if (getArguments() != null) {
            username = getArguments().getString(Constants.USERNAME, Constants.MANAGER);
            Log.d("Huybv", username);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        db = StartApplication.getDb();
        listCustomer = db.getAllCustomers();

        listCustomerAdapter = new ListCustomerAdapter(getActivity(), listCustomer, new ListCustomerAdapter.IListAction() {
            @Override
            public void onCall(String phoneNumber) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }

            @Override
            public void onSms(String phoneNumber) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + phoneNumber));
                startActivity(sendIntent);
            }

            @Override
            public void onEmail(String email) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }

            @Override
            public void onItemSelected(int position, int id) {
                editCustomer(id);
            }
        });
        listCustomerAdapter.setOnSelectedListener(new ListCustomerAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                editCustomer(position);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(listCustomerAdapter);

        mAddLead.setOnClickListener(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                listCustomerAdapter .refreshData(db.getAllCustomers());
            }
        };
        getActivity().registerReceiver(receiver, new IntentFilter("update"));
    }

    private void editCustomer(int id) {
        Intent intent = new Intent(getActivity(), AddCustomer.class);
        intent.putExtra(Constants.EDIT, id);
        startActivity(intent);
    }

    private void editCustomer(int position) {
        Intent intent = new Intent(getActivity(), AddCustomer.class);
//        Log.d("Huybv", customerInfoList.get(position).getmID()+ "" + customerInfoList.get(position).getmName());
        intent.putExtra(Constants.EDIT, listCustomer.get(position).getmID());
        intent.putExtra(Constants.USERNAME, username);
        startActivity(intent);
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
            listCustomerAdapter.refreshData(db.getAllCustomers());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }
}
