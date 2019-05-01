package com.example.applligent.nagoriengineering.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED)) {
            Log.e("", "ACTION_DATE_CHANGED received");
        }
    }
}