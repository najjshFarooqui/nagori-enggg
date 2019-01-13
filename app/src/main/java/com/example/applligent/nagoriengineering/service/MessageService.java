package com.example.applligent.nagoriengineering.service;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.model.Chat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessageService extends FirebaseMessagingService {
    private static final String TAG = "message triggered";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        ChatDao chatDao;
        String dataMessage = null;
        String dataUser = null;
        String dataTime = null;
        String dataId = null;
        chatDao = MyNagoriApplication.getDatabase().chatDao();

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
            dataMessage = remoteMessage.getData().get("message");
            dataUser = remoteMessage.getData().get("displayName");
            dataTime = remoteMessage.getData().get("timeStamp");
            dataId = remoteMessage.getData().get("id");
            Chat chatData = new Chat();
            chatData.message = dataMessage;
            chatData.displayName = dataUser;
            chatData.timeStamp = dataTime;
            chatData.userId = dataId;
            List<Chat> chatList = new ArrayList();
            chatList.add(chatData);
            chatDao.insertAll(chatList);
        }


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt(60000);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, MyNagoriApplication.chatChannel)
                .setSmallIcon(R.drawable.turbine)  //a resource for your custom small icon
                .setContentTitle("Nagori App") //the "title" value you sent in your notification
                .setContentText(remoteMessage.getData().get("message")) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSound(defaultSoundUri);


        notificationManager.notify(notificationId, notificationBuilder.build());

    }
}
