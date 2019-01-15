package com.example.applligent.nagoriengineering.service;

import android.util.Log;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.model.Chat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageService extends FirebaseMessagingService {
    private static final String TAG = "message triggered";
    Chat chatData;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ChatDao chatDao;
        String dataMessage = null;
        String dataUser = null;
        String dataTime = null;
        String dataId = null;
        chatDao = MyNagoriApplication.getDatabase().chatDao();

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null && remoteMessage.getData().get("id") != GeneralPreference.getUserId(getApplicationContext())) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
            dataMessage = remoteMessage.getData().get("message");
            dataUser = remoteMessage.getData().get("displayName");
            dataTime = remoteMessage.getData().get("timeStamp");
            dataId = remoteMessage.getData().get("id");
            chatData = new Chat();
            chatData.message = dataMessage;
            chatData.displayName = dataUser;
            chatData.hourMinute = dataTime;

            chatData.userId = dataId;
            List<Chat> chatList = new ArrayList();
            chatList.add(chatData);
            chatDao.insertAll(chatList);
        }




    }
}

