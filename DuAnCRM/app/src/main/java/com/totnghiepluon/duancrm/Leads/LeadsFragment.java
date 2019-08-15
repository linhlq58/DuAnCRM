package com.totnghiepluon.duancrm.Leads;

import android.os.Bundle;
import android.view.View;

import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.R;

public class LeadsFragment extends BaseFragment {

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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
