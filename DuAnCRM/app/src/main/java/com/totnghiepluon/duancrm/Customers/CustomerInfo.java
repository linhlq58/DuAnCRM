package com.totnghiepluon.duancrm.Customers;

public class CustomerInfo {
    private String mName;
    private String mCompany;
    private String mPhoneNumber;
    private String mLocation;
    private String mEmail;
    private String mBirthday;
    private int mPriority;
    private int mID;
    private boolean isCustomer;

    public CustomerInfo() {
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public CustomerInfo(String mName, String mPhoneNumber, String mCompany, String mLocation, String mEmail, String mBirthday, int mPriority, int ID) {
        this.mName = mName;
        this.mCompany = mCompany;
        this.mPhoneNumber = mPhoneNumber;
        this.mLocation = mLocation;
        this.mEmail = mEmail;
        this.mBirthday = mBirthday;
        this.mID = ID;
        this.mPriority = mPriority;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setmBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }

    public void setmPriority(int mPriority) {
        this.mPriority = mPriority;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public String getmCompany() {
        return mCompany;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmBirthday() {
        return mBirthday;
    }

    public int getmPriority() {
        return mPriority;
    }

    public int getmID() {
        return mID;
    }
}
