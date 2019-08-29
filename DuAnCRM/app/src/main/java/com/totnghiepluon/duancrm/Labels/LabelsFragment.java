package com.totnghiepluon.duancrm.Labels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.utils.Constants;

public class LabelsFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout layoutCustomer[];
    private TextView numberCustomer[];

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
        layoutCustomer = new RelativeLayout[5];
        numberCustomer = new TextView[5];
        layoutCustomer[0] = rootView.findViewById(R.id.label_1);
        layoutCustomer[1] = rootView.findViewById(R.id.label_2);
        layoutCustomer[2] = rootView.findViewById(R.id.label_3);
        layoutCustomer[3] = rootView.findViewById(R.id.label_4);
        layoutCustomer[4] = rootView.findViewById(R.id.label_5);
        numberCustomer[0] = rootView.findViewById(R.id.txt_quantity_customer1);
        numberCustomer[1] = rootView.findViewById(R.id.txt_quantity_customer2);
        numberCustomer[2] = rootView.findViewById(R.id.txt_quantity_customer3);
        numberCustomer[3] = rootView.findViewById(R.id.txt_quantity_customer4);
        numberCustomer[4] = rootView.findViewById(R.id.txt_quantity_customer5);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        for (int i = 0; i < 5; i++) {
            layoutCustomer[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.label_1:
                changeActivity(0);
                break;
            case R.id.label_2:
                changeActivity(1);
                break;
            case R.id.label_3:
                changeActivity(2);
                break;
            case R.id.label_4:
                changeActivity(3);
                break;
            case R.id.label_5:
                changeActivity(4);
                break;
            default:
                break;
        }
    }

    private void changeActivity(int i) {
        Intent intent = new Intent(getActivity(), LabelActivity.class);
        intent.putExtra(Constants.LABEL, Constants.CUSTOMER_LABEL[i]);
        startActivity(intent);
    }
}
