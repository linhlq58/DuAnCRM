package com.totnghiepluon.duancrm.Tasks;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.Base.StartApplication;
import com.totnghiepluon.duancrm.Models.Task;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.data.DatabaseHelper;

public class AddTaskActivity extends BaseActivity implements View.OnClickListener {
    private DatabaseHelper db;

    private Button btnClose;
    private Button btnCheck;
    private LinearLayout lnSetDate;
    private LinearLayout lnSetTime;
    private LinearLayout lnAttachCustomer;
    private LinearLayout lnDesc;
    private TextView tvDate;
    private TextView tvTime;
    private EditText editCustomer;
    private EditText editDesc;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_add_task;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        btnClose = findViewById(R.id.btn_close);
        btnCheck = findViewById(R.id.btn_check);
        lnSetDate = findViewById(R.id.lnSetDate);
        lnSetTime = findViewById(R.id.lnSetTime);
        lnAttachCustomer = findViewById(R.id.lnAttachCustomer);
        lnDesc = findViewById(R.id.lnDesc);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        editCustomer = findViewById(R.id.editCustomer);
        editDesc = findViewById(R.id.editDesc);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        db = StartApplication.getDb();

        btnClose.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        lnSetDate.setOnClickListener(this);
        lnSetTime.setOnClickListener(this);
        lnAttachCustomer.setOnClickListener(this);
    }

    private void addNewTask() {
        Task task = new Task();
        task.setTaskDate(tvDate.getText().toString());
        task.setTaskTime(tvTime.getText().toString());
        task.setTaskCustomer(editCustomer.getText().toString());
        task.setTaskDesc(editDesc.getText().toString());

        db.addTask(task);
    }

    private String getStringFromNumber(int number) {
        if (number > 9) {
            return number + "";
        } else {
            return "0" + number;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close:
                finish();
                break;
            case R.id.btn_check:
                addNewTask();

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
            case R.id.lnSetDate:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tvDate.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                }, 2019, 7, 30);
                datePickerDialog.show();
                break;
            case R.id.lnSetTime:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tvTime.setText(getStringFromNumber(i) + ":" + getStringFromNumber(i1));
                    }
                }, 0, 0, true);
                timePickerDialog.show();
                break;
            case R.id.lnAttachCustomer:
                break;

        }
    }
}
