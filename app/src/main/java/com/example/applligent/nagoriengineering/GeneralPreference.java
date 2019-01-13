package com.example.applligent.nagoriengineering;

import android.content.Context;
import android.content.SharedPreferences;

public class GeneralPreference {

    final static String FILE_NAME = "shared_pref";


    public static void setDataLoaded(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("data_loaded_pref", true).apply();
    }

    public static boolean getDataLoaded(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("data_loaded_pref", false);
    }


    public static void setNameLoaded(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("name_loaded_pref", name).apply();
    }

    public static String getNameLoaded(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("name_loaded_pref", "");
    }
}
