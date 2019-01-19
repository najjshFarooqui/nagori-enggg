package com.example.applligent.nagoriengineering.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.applligent.nagoriengineering.GeneralPreference;
import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.model.Chat;
import com.example.applligent.nagoriengineering.view.reminders.RemindersActivity;
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
        String notificationTitle = null, notificationBody = null;
        chatDao = MyNagoriApplication.getDatabase().chatDao();

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }



        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null && remoteMessage.getData().get("id") != GeneralPreference.getUserId(getApplicationContext())) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }


        private void sendNotification (String notificationTitle, String notificationBody){
            Intent intent = new Intent(this, RemindersActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setAutoCancel(true)   //Automatically delete the notification
                    .setSmallIcon(R.drawable.turbine) //Notification icon
                    .setContentIntent(pendingIntent)
                    .setContentTitle(dataUser)
                    .setContentText(dataMessage)
                    .setSound(defaultSoundUri);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
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

