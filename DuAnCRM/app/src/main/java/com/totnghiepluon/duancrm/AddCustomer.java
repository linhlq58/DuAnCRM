package com.totnghiepluon.duancrm;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.MainHandle.MainActivity;
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
    private MyDatabase database;

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
        if (!isAddCustomer) {
            label.setText(getResources().getString(R.string.add_leader));
            label.setTextSize(19);
        } else {
            label.setText(getResources().getString(R.string.add_customer));
        }
        mBtnClose.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
    }

    private void queryDB() {
        //create DB
        database = new MyDatabase(this, Constants.DATABASES, null, 1);
        //create table
        database.query("INSERT INTO " + Constants.TABLE_NAME + " VALUES(NULL, '" + mEdtName.getText().toString() +
                "', '" + mEdtPhone.getText().toString() +
                "', '" + mEdtCompany.getText().toString() +
                "', '" + mEdtEmail.getText().toString() +
                "', '" + mEdtLocation.getText().toString() +
                "', '" + mEdtBirthday.getText().toString() + "') ");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_check:
                queryDB();
                finish();
                break;
        }
    }
}
