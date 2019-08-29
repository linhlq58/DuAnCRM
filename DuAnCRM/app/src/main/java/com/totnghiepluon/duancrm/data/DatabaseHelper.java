package com.totnghiepluon.duancrm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.totnghiepluon.duancrm.Customers.CustomerInfo;

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
    private static final String Key_OWNER = "owner";
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
                + KEY_IS_CUSTOMER + " INTEGER,"
                + Key_OWNER + " TEXT)";
        db.execSQL(CREATE_CUSTOMER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);

        this.onCreate(db);
    }

    public void addLead(CustomerInfo customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getmName());
        values.put(KEY_PHONE_NUMBER, customer.getmPhoneNumber());
        values.put(KEY_COMPANY, customer.getmCompany());
        values.put(KEY_EMAIL, customer.getmEmail());
        values.put(KEY_ADDRESS, customer.getmLocation());
        values.put(KEY_BIRTHDAY, customer.getmBirthday());
        values.put(KEY_LABEL, customer.getmPriority());
        values.put(KEY_IS_CUSTOMER, 0);
        values.put(Key_OWNER, customer.getmOwner());
        db.insert(TABLE_CUSTOMER, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void addCustomer(CustomerInfo customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getmName());
        values.put(KEY_PHONE_NUMBER, customer.getmPhoneNumber());
        values.put(KEY_COMPANY, customer.getmCompany());
        values.put(KEY_EMAIL, customer.getmEmail());
        values.put(KEY_ADDRESS, customer.getmLocation());
        values.put(KEY_BIRTHDAY, customer.getmBirthday());
        values.put(KEY_LABEL, customer.getmPriority());
        values.put(KEY_IS_CUSTOMER, 1);
        values.put(Key_OWNER, customer.getmOwner());
        db.insert(TABLE_CUSTOMER, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<CustomerInfo> getAllLeads() {
        ArrayList<CustomerInfo> listMessage = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE " + KEY_IS_CUSTOMER + " = 0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                CustomerInfo customer = new CustomerInfo(cursor.getString(1), cursor.getString(2)
                        , cursor.getString(3), cursor.getString(4), cursor.getString(5)
                        , cursor.getString(6), cursor.getInt(7), cursor.getInt(0), cursor.getString(9));
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

    public ArrayList<CustomerInfo> getLeadByUser(String username) {
        ArrayList<CustomerInfo> listMessage = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE " + KEY_IS_CUSTOMER + " = 0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(9).equals(username)) {
                    CustomerInfo customer = new CustomerInfo(cursor.getString(1), cursor.getString(2)
                            , cursor.getString(3), cursor.getString(4), cursor.getString(5)
                            , cursor.getString(6), cursor.getInt(7), cursor.getInt(0), cursor.getString(9));
                    if (cursor.getInt(8) == 0) {
                        customer.setCustomer(false);
                    } else {
                        customer.setCustomer(true);
                    }

                    listMessage.add(customer);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listMessage;
    }

    public ArrayList<CustomerInfo> getAllCustomers() {
        ArrayList<CustomerInfo> listMessage = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE " + KEY_IS_CUSTOMER + " = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                CustomerInfo customer = new CustomerInfo(cursor.getString(1), cursor.getString(2)
                        , cursor.getString(3), cursor.getString(4), cursor.getString(5)
                        , cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9));
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

    public ArrayList<CustomerInfo> getCustomerbyUser(String username) {
        ArrayList<CustomerInfo> listMessage = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE " + KEY_IS_CUSTOMER + " = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(9).equals(username)) {
                    CustomerInfo customer = new CustomerInfo(cursor.getString(1), cursor.getString(2)
                            , cursor.getString(3), cursor.getString(4), cursor.getString(5)
                            , cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9));
                    if (cursor.getInt(8) == 0) {
                        customer.setCustomer(false);
                    } else {
                        customer.setCustomer(true);
                    }

                    listMessage.add(customer);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listMessage;
    }

    public CustomerInfo getCustomerById(int id) {
        CustomerInfo customer = new CustomerInfo();


        String query = "SELECT * FROM " + TABLE_CUSTOMER
                + " WHERE " + KEY_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                customer.setmID(cursor.getInt(0));
                customer.setmName(cursor.getString(1));
                customer.setmPhoneNumber(cursor.getString(2));
                customer.setmCompany(cursor.getString(3));
                customer.setmEmail(cursor.getString(4));
                customer.setmLocation(cursor.getString(5));
                customer.setmBirthday(cursor.getString(6));
                customer.setmPriority(cursor.getInt(7));
                if (cursor.getInt(8) == 0) {
                    customer.setCustomer(false);
                } else {
                    customer.setCustomer(true);
                }
                customer.setmOwner(cursor.getString(9));
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

    public int updateCustomer(CustomerInfo customer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, customer.getmName());
        values.put(KEY_PHONE_NUMBER, customer.getmPhoneNumber());
        values.put(KEY_COMPANY, customer.getmCompany());
        values.put(KEY_EMAIL, customer.getmEmail());
        values.put(KEY_ADDRESS, customer.getmLocation());
        values.put(KEY_BIRTHDAY, customer.getmBirthday());
        values.put(KEY_LABEL, customer.getmPriority());
        values.put(KEY_IS_CUSTOMER, customer.isCustomer() ? 1 : 0);
        values.put(Key_OWNER, customer.getmOwner());

        int i = db.update(TABLE_CUSTOMER, values, KEY_ID + " = ?",
                new String[]{String.valueOf(customer.getmID())});

        return i;
    }
}
