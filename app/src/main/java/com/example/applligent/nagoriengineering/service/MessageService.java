package com.example.applligent.nagoriengineering.service;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.dao.UserDao;
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
        UserDao userDao;

        String dataMessage = null;
        String dataUser = null;
        String dataTime = null;
        String dataId = null;
        String email = null;
        chatDao = MyNagoriApplication.getDatabase().chatDao();
        userDao = MyNagoriApplication.getDatabase().registerDao();

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
            List<String> name = userDao.getDisplayName();
            dataMessage = remoteMessage.getData().get("message");
            dataUser = remoteMessage.getData().get("displayName");
            dataTime = remoteMessage.getData().get("timeStamp");
            dataId = remoteMessage.getData().get("id");
            email = remoteMessage.getData().get("email");


            chatData = new Chat();
            chatData.message = dataMessage;
            chatData.displayName = dataUser;
            chatData.hourMinute = dataTime;

            chatData.userId = dataId;
            List<Chat> chatList = new ArrayList();
            chatList.add(chatData);
            chatDao.insertAll(chatList);
        }
        if (email == GeneralPreference.getUserEmail(getApplicationContext())) {


            showNotification(dataUser, dataMessage);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().get("id"));

        }
    }

    public void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyNagoriApplication.chatChannel)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.turbine)
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());

    }
}



