package com.totnghiepluon.duancrm.Leads;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.totnghiepluon.duancrm.AddCustomer;
import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.utils.Constants;

public class LeadsFragment extends BaseFragment implements View.OnClickListener {
    private Button mAddLead;

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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAddLead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                changeActivity();
                break;
                default:
                    break;
        }
    }

    private void changeActivity() {
        Intent intent = new Intent(getActivity(), AddCustomer.class);
        intent.putExtra(Constants.EXTRAS,false);
        startActivity(intent);
    }
}
