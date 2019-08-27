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
