package com.example.applligent.nagoriengineering.service;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.applligent.nagoriengineering.MyNagoriApplication;
import com.example.applligent.nagoriengineering.R;
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
        reminderDao = MyNagoriApplication.Companion.getDatabase(getApplicationContext()).reminderDao();

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
            reminderModel.setMessage(message);
            reminderModel.setUserID(userID);
            reminderModel.setUser(user);
            reminderModel.setDate(date);
            reminderModel.setTime(time);
            reminderModel.setTitle(title);
            reminderModel.setSendingTime(sendingTime);
            List<ReminderModel> reminderModelList = new ArrayList();
            reminderModelList.add(reminderModel);
            reminderDao.insert(reminderModel);
            showNotification(title, message);

        }
    }

    public void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyNagoriApplication.Companion.getReminderChannel())
                .setContentTitle(title)
                .setSmallIcon(R.drawable.icon_app)
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }
}




