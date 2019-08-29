package com.totnghiepluon.duancrm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.Base.StartApplication;
import com.totnghiepluon.duancrm.MainHandle.MainActivity;
import com.totnghiepluon.duancrm.Models.Customer;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.data.MyDatabase;
import com.totnghiepluon.duancrm.utils.Constants;

public class AddCustomer extends BaseActivity implements View.OnClickListener {
    private Button mBtnClose;
    private Button mBtnFinish;
    private TextView label;
    private EditText mEdtName;
    private EditText mEdtCompany;
    private EditText mEdtPhone;
    private EditText mEdtLocation;
    private EditText mEdtBirthday;
    private EditText mEdtEmail;
    private boolean isAddCustomer;
    private DatabaseHelper db;

    @Override
    protected int getLayoutResource() {
        return R.layout.add_customer;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        label = findViewById(R.id.txt_label);
        mBtnClose = findViewById(R.id.btn_close);
        mBtnFinish = findViewById(R.id.btn_check);
        mEdtName = findViewById(R.id.edt_name);
        mEdtBirthday = findViewById(R.id.edt_birthday);
        mEdtCompany = findViewById(R.id.edt_company);
        mEdtLocation = findViewById(R.id.edt_address);
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtPhone = findViewById(R.id.edt_phone);
        isAddCustomer = getIntent().getBooleanExtra(Constants.EXTRAS, false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        db = StartApplication.getDb();

        if (!isAddCustomer) {
            label.setText(getResources().getString(R.string.add_leader));
            label.setTextSize(19);
        } else {
            label.setText(getResources().getString(R.string.add_customer));
        }
        mBtnClose.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
    }

    private void addNewCustomer() {
        Customer customer = new Customer();
        customer.setName(mEdtName.getText().toString());
        customer.setPhoneNumber(mEdtPhone.getText().toString());
        customer.setCompany(mEdtCompany.getText().toString());
        customer.setEmail(mEdtEmail.getText().toString());
        customer.setAddress(mEdtLocation.getText().toString());
        customer.setBirthDay(mEdtBirthday.getText().toString());
        customer.setLabel(0);

        if (isAddCustomer) {
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
                addNewCustomer();
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
        }
    }
}
