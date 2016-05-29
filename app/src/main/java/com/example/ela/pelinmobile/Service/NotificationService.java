package com.example.ela.pelinmobile.Service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by e on 29/05/16.
 */
public class NotificationService extends FirebaseMessagingService {
    String TAG = "respon";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getMessageId());
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getNotification());
        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData());
    }
}
