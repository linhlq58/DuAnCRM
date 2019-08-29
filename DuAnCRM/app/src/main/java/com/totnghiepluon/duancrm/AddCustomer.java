package com.totnghiepluon.duancrm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.Base.StartApplication;
import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.utils.Constants;

public class AddCustomer extends BaseActivity implements View.OnClickListener {
    private Button mBtnClose;
    private Button mBtnFinish;
    private TextView label;
    private EditText mEdtName;
    private EditText mEdtCompany;
    private LinearLayout editLayout;
    private EditText mEdtPhone;
    private EditText mEdtLocation;
    private EditText mEdtBirthday;
    private EditText mEdtEmail;
    private boolean isAddCustomer;
    private DatabaseHelper db;
    private int editCustomer;
    private Button delete;
    private Button makeCustomer;
    private String username;
    private Spinner dropDown;
    private RelativeLayout rlDropDown;

    @Override
    protected int getLayoutResource() {
        return R.layout.add_customer;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        db = StartApplication.getDb();
        label = findViewById(R.id.txt_label);
        editLayout = findViewById(R.id.edit_layout);
        mBtnClose = findViewById(R.id.btn_close);
        mBtnFinish = findViewById(R.id.btn_check);
        mEdtName = findViewById(R.id.edt_name);
        mEdtBirthday = findViewById(R.id.edt_birthday);
        mEdtCompany = findViewById(R.id.edt_company);
        mEdtLocation = findViewById(R.id.edt_address);
        delete = findViewById(R.id.delete_user);
        makeCustomer = findViewById(R.id.make_customer);
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtPhone = findViewById(R.id.edt_phone);
        isAddCustomer = getIntent().getBooleanExtra(Constants.EXTRAS, false);
        username = getIntent().getStringExtra(Constants.USERNAME);
        editCustomer = getIntent().getIntExtra(Constants.EDIT, -1);
        dropDown = findViewById(R.id.spinner1);
        rlDropDown = findViewById(R.id.layout_dropdown);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String[] items = new String[]{"Chưa giao dịch", "Có hợp đồng", "Đã trả tiền", "Khách quen"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        dropDown.setAdapter(adapter);
        if (!isAddCustomer) {
            label.setText(getResources().getString(R.string.add_leader));
            label.setTextSize(19);
        } else {
            label.setText(getResources().getString(R.string.add_customer));
            rlDropDown.setVisibility(View.VISIBLE);
        }
        if (editCustomer > -1) {
            editLayout.setVisibility(View.VISIBLE);
            CustomerInfo customerInfo = db.getCustomerById(editCustomer);
            mEdtName.setText(customerInfo.getmName());
            mEdtBirthday.setText(customerInfo.getmBirthday());
            mEdtCompany.setText(customerInfo.getmCompany());
            mEdtLocation.setText(customerInfo.getmLocation());
            mEdtEmail.setText(customerInfo.getmEmail());
            mEdtPhone.setText(customerInfo.getmPhoneNumber());
            makeCustomer.setOnClickListener(this);
            delete.setOnClickListener(this);
            dropDown.setVisibility(View.GONE);
            if (isAddCustomer) {
                makeCustomer.setVisibility(View.GONE);
                rlDropDown.setVisibility(View.VISIBLE);
            }
        } else {
            editLayout.setVisibility(View.INVISIBLE);
        }
        mBtnClose.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
    }

    private void addNewCustomer(boolean isAddCustomer) {
        int priority = 0;
        if (isAddCustomer && dropDown != null) {
            priority = dropDown.getSelectedItemPosition() + 1;
        }
        CustomerInfo customer = new CustomerInfo(mEdtName.getText().toString(), mEdtPhone.getText().toString()
                , mEdtCompany.getText().toString(), mEdtEmail.getText().toString(),
                mEdtLocation.getText().toString(), mEdtBirthday.getText().toString(),
                priority, 0, username);
        if (this.isAddCustomer) {
            db.addCustomer(customer);
        } else {
            db.addLead(customer);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_check:
                if (editCustomer == -1) {
                    addNewCustomer(isAddCustomer);
                } else {
                    editCustomerinfo();
                }
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
        }
    }

    private void editCustomerinfo() {

    }
}
