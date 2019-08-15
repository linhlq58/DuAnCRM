package com.totnghiepluon.duancrm.Graph;

import android.os.Bundle;
import android.view.View;

import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.R;

public class GraphFragment extends BaseFragment {

    public static GraphFragment createInstance() {

        Bundle args = new Bundle();

        GraphFragment fragment = new GraphFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_graph;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
