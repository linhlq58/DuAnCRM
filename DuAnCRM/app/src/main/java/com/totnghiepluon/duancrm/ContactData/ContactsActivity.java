package com.totnghiepluon.duancrm.ContactData;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.Base.StartApplication;
import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.data.DatabaseHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;

public class ContactsActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, View.OnClickListener {
    private DatabaseHelper db;

    /*
     * Defines an array that contains column names to move from
     * the Cursor to the ListView.
     */
    @SuppressLint("InlinedApi")
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    /*
     * Defines an array that contains resource ids for the layout views
     * that get the Cursor column contents. The id is pre-defined in
     * the Android framework, so it is prefaced with "android.R.id"
     */
    private final static int[] TO_IDS = {
            android.R.id.text1
    };

    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                            ContactsContract.Contacts.DISPLAY_NAME

            };

    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for the CONTACT_KEY column
    private static final int CONTACT_KEY_INDEX = 1;

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?";
    // Defines a variable for the search string
    private String searchString = "";
    // Defines the array to hold values that replace the ?
    private String[] selectionArgs = {searchString};

    // Define global mutable variables
    private ListView contactsList;
    private long contactId;
    private String contactKey;
    private Uri contactUri;
    private SimpleCursorAdapter cursorAdapter;
    private RelativeLayout btnBack;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_contact_list;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        contactsList = findViewById(R.id.list_contact);
        btnBack = findViewById(R.id.btn_back);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        db = StartApplication.getDb();

        // Initializes the loader
        getLoaderManager().initLoader(0, null, this);

        cursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.contacts_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);

        contactsList.setAdapter(cursorAdapter);
        contactsList.setOnItemClickListener(this);

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Cursor cursor = ((SimpleCursorAdapter) adapterView.getAdapter()).getCursor();
        // Move to the selected contact
        cursor.moveToPosition(i);
        // Get the _ID value
        contactId = cursor.getLong(CONTACT_ID_INDEX);
        // Get the selected LOOKUP KEY
        contactKey = cursor.getString(CONTACT_KEY_INDEX);
        // Create the contact's content Uri
        contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey);
        /*
         * You can use contactUri as the content URI for retrieving
         * the details for a contact.
         */

        int ColumeIndex_DISPLAY_NAME = cursor.getColumnIndex(Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                ContactsContract.Contacts.DISPLAY_NAME);
        String name = cursor.getString(ColumeIndex_DISPLAY_NAME);

        ContentResolver cr = getContentResolver();

        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
        while (phones.moveToNext()) {
            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            /*int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
            switch (type) {
                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                    // do something with the Home number here...
                    Log.e("home phone", number);
                    break;
                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                    // do something with the Mobile number here...
                    Log.e("home phone", number);
                    break;
                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                    // do something with the Work number here...
                    Log.e("home phone", number);
                    break;
            }*/

            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setmName(name);
            customerInfo.setmPhoneNumber(number);

            db.addCustomer(customerInfo);

            Intent updateCustomer = new Intent("update");
            sendBroadcast(updateCustomer);
        }
        phones.close();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array
         */
        selectionArgs[0] = "%" + searchString + "%";
        // Starts the query
        return new CursorLoader(
                this,
                ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // Put the result Cursor in the adapter for the ListView
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Delete the reference to the existing Cursor
        cursorAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }
}
