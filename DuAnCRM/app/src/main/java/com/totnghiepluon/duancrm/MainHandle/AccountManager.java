package com.totnghiepluon.duancrm.MainHandle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.data.RWManagerFile;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AccountManager extends BaseActivity implements View.OnClickListener {
    private int currentMode = Constants.ADD_NEW_ACCOUNT;
    private EditText user;
    private EditText pass1;
    private EditText pass2;
    private Button btnNewAccount;
    private Button btnEditAccount;
    private Button btnDeleteAccount;
    private Button btnCancel;
    private Button btnFinish;
    private CheckBox checkBox;
    private TextView txtPass1;
    private TextView txtPass2;
    private TextView txtPassNotice;
    private TextView txtUserNotice;
    private RWManagerFile rwManagerFile;
    private TextView checkboxManager;

    @Override
    protected int getLayoutResource() {
        return R.layout.manage_account;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        rwManagerFile = new RWManagerFile(this);
        user = findViewById(R.id.edt_user);
        pass1 = findViewById(R.id.edt_pass1);
        pass2 = findViewById(R.id.edt_pass2);
        btnNewAccount = findViewById(R.id.add_account);
        btnEditAccount = findViewById(R.id.change_pass);
        btnDeleteAccount = findViewById(R.id.delete_account);
        btnCancel = findViewById(R.id.btn_cancel);
        btnFinish = findViewById(R.id.btn_finish);
        checkboxManager = findViewById(R.id.text_manage);
        checkBox = findViewById(R.id.check_box);
        txtPass1 = findViewById(R.id.txt_pass1);
        txtPass2 = findViewById(R.id.txt_pass2);
        txtPassNotice = findViewById(R.id.txt_duplicate_pass);
        txtUserNotice = findViewById(R.id.txt_duplicate_user);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        btnNewAccount.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDeleteAccount.setOnClickListener(this);
        btnEditAccount.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_account:
                addAccountMode();
                break;
            case R.id.change_pass:
                changePassMode();
                break;
            case R.id.delete_account:
                deleteAccountMode();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_finish:
                checkFinish();
                break;
            default:
                break;
        }
    }

    private void checkFinish() {

        boolean isSuitable = true;
        if (pass1.getText().toString().equals("")) {
            pass1.setHint(getResources().getString(R.string.red_notice));
            pass1.setHintTextColor(getResources().getColor(R.color.red, null));
            isSuitable = false;
        }
        if (pass2.getText().toString().equals("")) {
            pass2.setHint(getResources().getString(R.string.red_notice));
            pass2.setHintTextColor(getResources().getColor(R.color.red, null));
            isSuitable = false;
        }
        if (user.getText().toString().equals("")) {
            user.setHint(getResources().getString(R.string.red_notice));
            user.setHintTextColor(getResources().getColor(R.color.red, null));
            isSuitable = false;
        }
        if (!isSuitable) {
            return;
        }
        if (!pass2.getText().toString().equals(pass1.getText().toString()) && currentMode != Constants.DELETE_ACCOUNT) {
            txtPassNotice.setText(Constants.DUPLICATE_PASSWORD);
            txtPassNotice.setVisibility(View.VISIBLE);
            return;
        }
        String managerList = rwManagerFile.readFromFile(true);
        String staffList = rwManagerFile.readFromFile(false);
        String username = user.getText().toString();
        if (currentMode == Constants.ADD_NEW_ACCOUNT) {
            createNewAccount(managerList, staffList);
        } else if (currentMode == Constants.EDIT_ACCOUNT) {
            editAccountPass(managerList, staffList, username,true);
        }else if(currentMode == Constants.DELETE_ACCOUNT){
            editAccountPass(managerList,staffList,username,false);
        }
    }


    private void editAccountPass(String managerList, String staffList, String username,boolean isAppend) {
        List<String> userlist;
        boolean isManager = true;
        if (managerList.contains("|" + username + ",")) {
            userlist = getAccountList(true);
        } else if (staffList.contains("|" + username + ",")) {
            userlist = getAccountList(false);
            isManager = false;
        } else {
            txtUserNotice.setText(Constants.UNEXIST_STRING);
            txtUserNotice.setVisibility(View.VISIBLE);
            return;
        }
        String temp;
        for (int i = 0; i < userlist.size(); i++) {
            temp = "|" + userlist.get(i);
            if (temp.contains("|" + username + ",")) {
                if(isAppend) {
                    userlist.set(i, username + "," + pass1.getText().toString());
                }
                else {
                    userlist.remove(i);
                }
                writeToFile(userlist, isManager);
                break;
            }
        }

    }

    private void writeToFile(List<String> userlist, boolean isManager) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userlist.size(); i++) {
            stringBuilder.append("|");
            stringBuilder.append(userlist.get(i));
        }
        Log.d("Huybv", rwManagerFile.readFromFile(isManager));
        rwManagerFile.editFile(stringBuilder.toString(), isManager);
        Log.d("Huybv", rwManagerFile.readFromFile(isManager));
    }

    private void createNewAccount(String managerList, String staffList) {
        if (managerList.contains("|" + user.getText().toString() + ",")
                || staffList.contains("|" + user.getText().toString() + ",")) {
            txtUserNotice.setText(Constants.RETYPE_EXISTED_STRING);
            txtUserNotice.setVisibility(View.VISIBLE);
            return;
        }
        txtUserNotice.setVisibility(View.INVISIBLE);
        String account = user.getText().toString() + "," + pass2.getText().toString();
        Log.d("Huybv", rwManagerFile.readFromFile(checkBox.isChecked()));
        rwManagerFile.writeToFile(account, checkBox.isChecked());
        Log.d("Huybv", rwManagerFile.readFromFile(checkBox.isChecked()));
        finish();
    }

    private void deleteAccountMode() {
        btnEditAccount.setTextColor(getResources().getColor(R.color.dark, null));
        btnNewAccount.setTextColor(getResources().getColor(R.color.dark, null));
        btnDeleteAccount.setTextColor(getResources().getColor(R.color.white, null));
        txtPass1.setVisibility(View.GONE);
        txtPass2.setVisibility(View.GONE);
        pass1.setVisibility(View.GONE);
        pass2.setVisibility(View.GONE);
        checkBox.setVisibility(View.GONE);
        checkboxManager.setVisibility(View.GONE);
        currentMode = Constants.DELETE_ACCOUNT;
    }

    private void addAccountMode() {
        btnEditAccount.setTextColor(getResources().getColor(R.color.dark, null));
        btnDeleteAccount.setTextColor(getResources().getColor(R.color.dark, null));
        btnNewAccount.setTextColor(getResources().getColor(R.color.white, null));
        txtPass1.setVisibility(View.VISIBLE);
        txtPass2.setVisibility(View.VISIBLE);
        pass1.setVisibility(View.VISIBLE);
        pass2.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.VISIBLE);
        checkboxManager.setVisibility(View.VISIBLE);
        currentMode = Constants.ADD_NEW_ACCOUNT;
    }

    private void changePassMode() {
        currentMode = Constants.EDIT_ACCOUNT;
        btnDeleteAccount.setTextColor(getResources().getColor(R.color.dark, null));
        btnEditAccount.setTextColor(getResources().getColor(R.color.white, null));
        btnNewAccount.setTextColor(getResources().getColor(R.color.dark, null));
        txtPass1.setVisibility(View.VISIBLE);
        txtPass2.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.GONE);
        checkboxManager.setVisibility(View.GONE);
        pass1.setVisibility(View.VISIBLE);
        pass2.setVisibility(View.VISIBLE);
    }

    private List<String> getAccountList(boolean isManager) {
        List<String> accountLists = new ArrayList<>();
        String savedString = rwManagerFile.readFromFile(isManager);
        StringTokenizer st = new StringTokenizer(savedString, "|");
        while (st.hasMoreTokens()) {
            accountLists.add(st.nextToken());
        }
        return accountLists;
    }
}
