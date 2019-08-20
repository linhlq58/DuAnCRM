package com.totnghiepluon.duancrm.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GetDataFromPreference {
    private Context context;
    private int length;
    public GetDataFromPreference(Context context) {
        this.context = context;
    }

    public List<Integer> loadGameSetting(String s)  {
        SharedPreferences sharedPreferences= context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
        List<Integer> list = new ArrayList<>();
        if(sharedPreferences!= null) {
            String savedString = sharedPreferences.getString(s, "");
            StringTokenizer st = new StringTokenizer(savedString, ",");
            for(int i = 0; i < length; i++){
                list.add(Integer.parseInt(st.nextToken()));
            }
        } else {
            Log.d("Huybv", "Empty");
        }
        return list;
    }
    public void doSave(List<Integer> list, String s)  {
        length = list.size();
        // File chia sẻ sử dụng trong nội bộ ứng dụng, hoặc các ứng dụng được chia sẻ cùng User.
        SharedPreferences sharedPreferences= context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
        StringBuilder str = new StringBuilder();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int i= 0; i < length; i ++){
            str.append(list.get(i)).append(",");
        }
        editor.putString(s, str.toString());
        // Save.
        editor.apply();
    }
}
