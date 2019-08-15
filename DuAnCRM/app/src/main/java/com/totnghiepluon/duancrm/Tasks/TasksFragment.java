package com.totnghiepluon.duancrm.Tasks;

import android.os.Bundle;
import android.view.View;

import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.R;

public class TasksFragment extends BaseFragment {

    public static TasksFragment createInstance() {

        Bundle args = new Bundle();

        TasksFragment fragment = new TasksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tasks;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
