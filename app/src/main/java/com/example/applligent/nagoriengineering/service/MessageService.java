package com.example.applligent.nagoriengineering.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.model.Chat;
import com.example.applligent.nagoriengineering.view.chat.ChatActivityNew;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageService extends FirebaseMessagingService {
    private static final String TAG = "message triggered";
    private static final int REQUEST_CODE = 1;
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
        if (!dataId.equals(GeneralPreference.getUserId(getApplicationContext()))  && !GeneralPreference.getFlag(getApplicationContext()).equals("najish")){


            showNotification(dataUser, dataMessage);
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().get("id"));

        }
    }

    public void showNotification(String title, String message) {

        Intent i = new Intent(this, ChatActivityNew.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyNagoriApplication.Companion.getChatChannel())
                .setContentTitle(title)
                .setSmallIcon(R.drawable.icon_app)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText(message);


        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());

    }
}



