package com.totnghiepluon.duancrm.Customers;

import android.os.Bundle;
import android.view.View;

import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.R;

public class CustomersFragment extends BaseFragment {

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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
