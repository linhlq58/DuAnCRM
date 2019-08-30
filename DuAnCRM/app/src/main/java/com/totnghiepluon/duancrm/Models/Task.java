package com.totnghiepluon.duancrm.Models;

public class Task {
    private int taskId;
    private String taskDate;
    private String taskTime;
    private String taskCustomer;
    private String taskDesc;

    public Task() {
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskCustomer() {
        return taskCustomer;
    }

    public void setTaskCustomer(String taskCustomer) {
        this.taskCustomer = taskCustomer;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
}
