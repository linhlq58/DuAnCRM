package com.totnghiepluon.duancrm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.totnghiepluon.duancrm.Models.Customer;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CRMDatabase";

    private static final String TABLE_CUSTOMER = "customer";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_LABEL = "label";
    private static final String KEY_IS_CUSTOMER = "is_customer";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMER + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_PHONE_NUMBER + " TEXT, "
                + KEY_COMPANY + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_ADDRESS + " TEXT, "
                + KEY_BIRTHDAY + " TEXT, "
                + KEY_LABEL + " INTEGER, "
                + KEY_IS_CUSTOMER + " INTEGER)";

        db.execSQL(CREATE_CUSTOMER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);

        this.onCreate(db);
    }

    public void addLead(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getName());
        values.put(KEY_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(KEY_COMPANY, customer.getCompany());
        values.put(KEY_EMAIL, customer.getEmail());
        values.put(KEY_ADDRESS, customer.getAddress());
        values.put(KEY_BIRTHDAY, customer.getBirthDay());
        values.put(KEY_LABEL, customer.getLabel());
        values.put(KEY_IS_CUSTOMER, 0);

        db.insert(TABLE_CUSTOMER, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getName());
        values.put(KEY_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(KEY_COMPANY, customer.getCompany());
        values.put(KEY_EMAIL, customer.getEmail());
        values.put(KEY_ADDRESS, customer.getAddress());
        values.put(KEY_BIRTHDAY, customer.getBirthDay());
        values.put(KEY_LABEL, customer.getLabel());
        values.put(KEY_IS_CUSTOMER, 1);

        db.insert(TABLE_CUSTOMER, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<Customer> getAllLeads() {
        ArrayList<Customer> listMessage = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE " + KEY_IS_CUSTOMER + " = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setPhoneNumber(cursor.getString(2));
                customer.setCompany(cursor.getString(3));
                customer.setEmail(cursor.getString(4));
                customer.setAddress(cursor.getString(5));
                customer.setBirthDay(cursor.getString(6));
                customer.setLabel(cursor.getInt(7));
                if (cursor.getInt(8) == 0) {
                    customer.setCustomer(false);
                } else {
                    customer.setCustomer(true);
                }

                listMessage.add(customer);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listMessage;
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> listMessage = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE " + KEY_IS_CUSTOMER + " = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setPhoneNumber(cursor.getString(2));
                customer.setCompany(cursor.getString(3));
                customer.setEmail(cursor.getString(4));
                customer.setAddress(cursor.getString(5));
                customer.setBirthDay(cursor.getString(6));
                customer.setLabel(cursor.getInt(7));
                if (cursor.getInt(8) == 0) {
                    customer.setCustomer(false);
                } else {
                    customer.setCustomer(true);
                }

                listMessage.add(customer);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listMessage;
    }

    public Customer getCustomerById(int id) {
        Customer customer = new Customer();

        String query = "SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setPhoneNumber(cursor.getString(2));
                customer.setCompany(cursor.getString(3));
                customer.setEmail(cursor.getString(4));
                customer.setAddress(cursor.getString(5));
                customer.setBirthDay(cursor.getString(6));
                customer.setLabel(cursor.getInt(7));
                if (cursor.getInt(8) == 0) {
                    customer.setCustomer(false);
                } else {
                    customer.setCustomer(true);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return customer;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CUSTOMER, null, null);
    }

    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CUSTOMER, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int updateCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getName());
        values.put(KEY_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(KEY_COMPANY, customer.getCompany());
        values.put(KEY_EMAIL, customer.getEmail());
        values.put(KEY_ADDRESS, customer.getAddress());
        values.put(KEY_BIRTHDAY, customer.getBirthDay());
        values.put(KEY_LABEL, customer.getLabel());
        values.put(KEY_IS_CUSTOMER, customer.isCustomer() ? 1 : 0);

        int i = db.update(TABLE_CUSTOMER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(customer.getId())});

        return i;
    }
}
