package com.example.ela.pelinmobile.Service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by e on 29/05/16.
 */
public class InstanceIdService extends FirebaseInstanceIdService {
    String TAG = "respon";

    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "onTokenRefresh: " + token);

        // TODO: Implement this method to send any registration to your app's servers.
    }
}
