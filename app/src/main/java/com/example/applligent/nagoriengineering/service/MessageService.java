package com.example.applligent.nagoriengineering.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
import com.example.applligent.nagoriengineering.dao.ChatDao;
import com.example.applligent.nagoriengineering.model.Chat;
import com.example.applligent.nagoriengineering.view.ChatActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageService extends FirebaseMessagingService {
    private static final String TAG = "Message is here";
    ChatDao chatDao;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        /* There are two types of messages data messages and notification messages. Data messages are handled here in onMessageReceived whether the app is in the foreground or background. Data messages are the type traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app is in the foreground. When the app is in the background an automatically generated notification is displayed. */

        String notificationTitle = null, notificationBody = null;
        String dataMessage = null;
        String dataUser = null;
        String dataTime = null;
        String dataId = null;
        chatDao = MyNagoriApplication.getDatabase().chatDao();


        // Check if message contains a data payload.
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

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        sendNotification(notificationTitle, notificationBody, dataMessage);
    }

    /**
     * //     * Create and show a simple notification containing the received FCM message.
     * //
     */
    private void sendNotification(String notificationTitle, String notificationBody, String dataMessage) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("message", dataMessage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
