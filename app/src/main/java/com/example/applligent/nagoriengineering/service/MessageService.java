package com.example.applligent.nagoriengineering.service;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
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

        chatDao = MyNagoriApplication.Companion.getDatabase(getApplicationContext()).chatDao();

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
            dataMessage = remoteMessage.getData().get("message");
            dataUser = remoteMessage.getData().get("displayName");
            dataTime = remoteMessage.getData().get("timeStamp");
            dataId = remoteMessage.getData().get("userId");


            chatData = new Chat();
            chatData.setMessage(dataMessage);
            chatData.setDisplayName(dataUser);
            chatData.setHourMinute(dataTime);
            chatData.setUserId(dataId);

            List<Chat> chatList = new ArrayList();
            chatList.add(chatData);
            chatDao.insertAll(chatList);
        }
        if (!dataId.equals(GeneralPreference.getUserId(getApplicationContext()))) {


            showNotification(dataUser, dataMessage);
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().get("id"));

        }
    }

    public void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyNagoriApplication.Companion.getChatChannel())
                .setContentTitle(title)
                .setSmallIcon(R.drawable.icon_app)
                .setAutoCancel(true)
                .setContentText(message);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());

    }
}



