package com.totnghiepluon.duancrm.Leads;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.totnghiepluon.duancrm.AddCustomer;
import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.data.MyDatabase;
import com.totnghiepluon.duancrm.utils.Constants;

public class LeadsFragment extends BaseFragment implements View.OnClickListener {
    private Button mAddLead;
    private MyDatabase database;
    private String[] strings = {"Mot", "Hai", "Ba", "Bon", "Nam"};

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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAddLead.setOnClickListener(this);
        database.query("CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.NAME + " VARCHAR(50), " + Constants.PHONE_NUMBER + " VARCHAR(20), " + Constants.COMPANY + " VARCHAR(100), " +
                Constants.EMAIL + " VARCHAR(50), " + Constants.ADDRESS + " VARCHAR(200), " + Constants.BIRTHDAY + " VARCHAR(20))");

    }

    @Override
    public void onResume() {
        super.onResume();
//        database.query("INSERT INTO " + Constants.TABLE_NAME + " VALUES(NULL, '" + strings[2] + "')");
        Log.d("Huybv", " On Resume");
        Cursor data = database.getData("SELECT * FROM " + Constants.TABLE_NAME);
        while (data.moveToNext()) {
            Log.d("Huybv", data.getString(1) + " Name");
            Log.d("Huybv", data.getString(2) + " Phone");
            Log.d("Huybv", data.getString(3) + " Company");
            Log.d("Huybv", data.getString(4) + " Email");
            Log.d("Huybv", data.getString(5) + " Address");
            Log.d("Huybv", data.getString(6) + " Birthday");
        }
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
