package com.totnghiepluon.duancrm.data;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RWManagerFile {
    private Context context;
    private int quantityAccount = 4;

    public RWManagerFile(Context context) {
        this.context = context;
    }

    public void writeToFile(String data,boolean isManager) {
        String fileName = "account.txt";
        if(isManager){
            fileName = "maccount.txt";
        }
        try {
            data = readFromFile(isManager) + "|" + data;
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName,
                    Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFile(boolean isManager) {

        String ret = "";
        String fileName = "account.txt";
        if(isManager){
            fileName = "maccount.txt";
        }
        try {
            InputStream inputStream = context.openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}