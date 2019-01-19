package com.example.applligent.nagoriengineering.service;

import android.util.Log;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.dao.ReminderDao;
import com.example.applligent.nagoriengineering.model.Chat;
import com.example.applligent.nagoriengineering.model.ReminderModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class ReminderService extends FirebaseMessagingService {

    private static final String TAG = "message triggered";
    Chat chatData;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ReminderDao reminderDao;
        String message = null;
        String user = null;
        String time = null;
        String date = null;
        String userID = null;
        String sendingTime = null;
        String title = null;
        reminderDao = MyNagoriApplication.getDatabase().reminderDao();

        // Check if message contains a notification payload.


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
            date = remoteMessage.getData().get("date");
            message = remoteMessage.getData().get("message");
            sendingTime = remoteMessage.getData().get("sendingTime");
            time = remoteMessage.getData().get("time");
            title = remoteMessage.getData().get("title");
            user = remoteMessage.getData().get("user");
            userID = remoteMessage.getData().get("userID");

            ReminderModel reminderModel = new ReminderModel();
            reminderModel.message = message;
            reminderModel.userID = userID;
            reminderModel.user = user;
            reminderModel.date = date;
            reminderModel.time = time;
            reminderModel.title = title;
            reminderModel.sendingTime = sendingTime;
            List<ReminderModel> reminderModelList = new ArrayList();
            reminderModelList.add(reminderModel);
            reminderDao.insert(reminderModel);

        }
    }
}
