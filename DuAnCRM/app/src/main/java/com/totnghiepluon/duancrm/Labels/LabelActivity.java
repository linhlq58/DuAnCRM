package com.totnghiepluon.duancrm.Labels;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.utils.Constants;

public class LabelActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private TextView labelText;
    private Button closeButton;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_label;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        labelText = findViewById(R.id.txt_label);
        closeButton = findViewById(R.id.label_close);
        recyclerView = findViewById(R.id.recyclerview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        labelText.setText(getIntent().getStringExtra(Constants.LABEL));
    }
}
