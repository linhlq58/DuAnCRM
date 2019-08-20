package com.totnghiepluon.duancrm.MainHandle;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.data.ReadWriteFile;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnLogin;
    private TextView mForget;
    private TextView mHelp;
    private ReadWriteFile readWriteFile;
    private EditText mUserName;
    private Intent intent;
    private EditText mPassword;

    @Override
    protected int getLayoutResource() {
        return R.layout.login_activity;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        readWriteFile = new ReadWriteFile(this);
        mBtnLogin = findViewById(R.id.btn_login);
        mForget = findViewById(R.id.tv_forget);
        mHelp = findViewById(R.id.tv_help);
        mUserName = findViewById(R.id.edt_username);
        mPassword = findViewById(R.id.edt_password);
        mForget.setPaintFlags(mForget.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mHelp.setPaintFlags(mHelp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        intent = new Intent(this, MainActivity.class);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBtnLogin.setOnClickListener(this);
        mForget.setOnClickListener(this);
        mHelp.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        readWriteFile.writeToFile("huybv,123|abc,1231|asd,asdasd|asdasca,kdioas");
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
        mUserName.setHintTextColor(getResources().getColor(R.color.hint_color));
        mPassword.setHint(getResources().getString(R.string.pass_hint));
        mPassword.setHintTextColor(getResources().getColor(R.color.hint_color));
    }

    private void changeActivity() {
        List accountList = getAccountList();
        Log.d("Huybv", "account list size = " + accountList.size());
        boolean isSuitable = true;
        String password = mPassword.getText().toString();
        String username = mUserName.getText().toString();
        if (username.equals("")) {
            mUserName.setHint(getResources().getString(R.string.red_notice));
            mUserName.setHintTextColor(getResources().getColor(R.color.red));
            isSuitable = false;
        }
        if (password.equals("")) {
            mPassword.setHint(getResources().getString(R.string.red_notice));
            mPassword.setHintTextColor(getResources().getColor(R.color.red));
            isSuitable = false;
        }
        if (isSuitable) {
            username = username + "," + password;
            boolean isExistUser = false;
            for (int i = 0; i < accountList.size(); i++) {
                if (username.equals(accountList.get(i))){
                    startActivity(intent);
                    isExistUser = true;
                    break;
                }
            }
        }
    }

    private List getAccountList() {
        List<String> accountLists = new ArrayList<>();
        String savedString = readWriteFile.readFromFile();
        StringTokenizer st = new StringTokenizer(savedString, "|");
        for (int i = 0; i < 4; i++) {
            accountLists.add(st.nextToken());
        }
        return accountLists;
    }
}
