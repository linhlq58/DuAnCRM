package com.totnghiepluon.duancrm.Labels;

import android.os.Bundle;
import android.view.View;

import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.R;

public class LabelsFragment extends BaseFragment {

    public static LabelsFragment createInstance() {

        Bundle args = new Bundle();

        LabelsFragment fragment = new LabelsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_labels;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
