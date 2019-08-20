package com.totnghiepluon.duancrm;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.totnghiepluon.duancrm.Base.BaseActivity;

public class AddCustomer extends BaseActivity {
    private Button mBtnClose;
    private Button mBtnFinish;
    private EditText mEdtName;
    private EditText mEdtCompany;
    private EditText mEdtPhone;
    private EditText mEdtLocation;
    private EditText mEdtBirthday;

    @Override
    protected int getLayoutResource() {
        return R.layout.add_customer;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
//        mBtnClose = findViewById(R.id.btn_close);
//        mBtnFinish = findViewById(R.id.btn_check);
//        mEdtName = findViewById(R.id.edt_name);
//        mEdtBirthday = findViewById(R.id.edt_birthday);
//        mEdtCompany = findViewById(R.id.edt_company);
//        mEdtLocation = findViewById(R.id.edt_address);
//        mEdtPhone = findViewById(R.id.edt_phone);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
