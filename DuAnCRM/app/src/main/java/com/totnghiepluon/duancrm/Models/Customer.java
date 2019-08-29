package com.totnghiepluon.duancrm.Models;

public class Customer {
    private int id;
    private String name;
    private String phoneNumber;
    private String company;
    private String email;
    private String address;
    private String birthDay;
    private int label;
    private boolean isCustomer;

    public Customer() {
    }

    public Customer(String name, String phoneNumber, String company, String email, String address, String birthDay, int label) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.email = email;
        this.address = address;
        this.birthDay = birthDay;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }
}
