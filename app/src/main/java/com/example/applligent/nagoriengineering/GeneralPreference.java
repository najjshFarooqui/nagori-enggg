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

    public static void setUserId(Context context, String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("userId-shared_pref", userId).apply();
    }

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId-shared_pref", "");
    }

    public static void setUserEmail(Context context, String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("user_email", email).apply();
    }

    public static String getUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", "");
    }


    public static void setAdmin(Context context, String admin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("user_admin", admin).apply();
    }

    public static String getAdmin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_admin", "");
    }



}