package com.totnghiepluon.duancrm.MainHandle;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.data.RWManagerFile;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnLogin;
    private TextView mForget;
    private TextView mHelp;
    private TextView mWrongAccount;
    private RWManagerFile rwManagerFile;
    private EditText mUserName;
    private Intent intent;
    private EditText mPassword;

    @Override
    protected int getLayoutResource() {
        return R.layout.login_activity;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        rwManagerFile = new RWManagerFile(this);
        mBtnLogin = findViewById(R.id.btn_login);
        mForget = findViewById(R.id.tv_forget);
        mHelp = findViewById(R.id.tv_help);
        mWrongAccount = findViewById(R.id.wrong_account);
        mUserName = findViewById(R.id.edt_username);
        mPassword = findViewById(R.id.edt_password);
        mForget.setPaintFlags(mForget.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mHelp.setPaintFlags(mHelp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        intent = new Intent(this, MainActivity.class);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mWrongAccount.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBtnLogin.setOnClickListener(this);
        mForget.setOnClickListener(this);
        mHelp.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        rwManagerFile.writeToFile(Constants.MANAGER_ACCOUNT, true);
        rwManagerFile.writeToFile(Constants.STAFF_ACCOUNT, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                changeActivity();
                break;
            case R.id.tv_help:
                break;
            case R.id.edt_username:
                changeHintColor();
                break;
            case R.id.edt_password:
                changeHintColor();
                break;
            default:
                break;
        }
    }

    private void changeHintColor() {
        mUserName.setHint(getResources().getString(R.string.account_hint));
        mUserName.setHintTextColor(getResources().getColor(R.color.hint_color, null));
        mPassword.setHint(getResources().getString(R.string.pass_hint));
        mPassword.setHintTextColor(getResources().getColor(R.color.hint_color, null));
    }

    private void changeActivity() {
        List managerAccount = getAccountList(true);
        List staffAccount = getAccountList(false);
        boolean isSuitable = true;
        String password = mPassword.getText().toString();
        String username = mUserName.getText().toString();
        if (username.equals("")) {
            mUserName.setHint(getResources().getString(R.string.red_notice));
            mUserName.setHintTextColor(getResources().getColor(R.color.red, null));
            isSuitable = false;
        }
        if (password.equals("")) {
            mPassword.setHint(getResources().getString(R.string.red_notice));
            mPassword.setHintTextColor(getResources().getColor(R.color.red, null));
            isSuitable = false;
        }
        if (isSuitable) {
            String account = username;
            username = username + "," + password;
            for (int i = 0; i < managerAccount.size(); i++) {
                if (username.equals(managerAccount.get(i))) {
                    intent.putExtra(Constants.LOGIN, true);
                    intent.putExtra(Constants.USERNAME, Constants.MANAGER);
                    startActivity(intent);
                    isSuitable = false;
                    break;
                }
            }
            for (int i = 0; i < staffAccount.size(); i++) {
                if (username.equals(staffAccount.get(i))) {
                    intent.putExtra(Constants.LOGIN, false);
                    intent.putExtra(Constants.USERNAME, account);
                    startActivity(intent);
                    isSuitable = false;
                    break;
                }
            }
            if (isSuitable) {
                mWrongAccount.setVisibility(View.VISIBLE);
            } else {
                mWrongAccount.setVisibility(View.INVISIBLE);
            }
        }
    }

    private List getAccountList(boolean isManager) {
        List<String> accountLists = new ArrayList<>();
        String savedString = rwManagerFile.readFromFile(isManager);
        StringTokenizer st = new StringTokenizer(savedString, "|");
        while (st.hasMoreTokens()) {
            accountLists.add(st.nextToken());
        }
        return accountLists;
    }
}
