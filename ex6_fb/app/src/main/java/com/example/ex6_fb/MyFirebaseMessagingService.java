package com.example.ex6_fb;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String SERVER = "http://192.168.1.55:8080/";
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        registerToServer(token);
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage){

        if (remoteMessage.getNotification() != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showNotification(remoteMessage.getNotification().getBody());
                }
            });
        }
    }

    private void showNotification(String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String NOTIFICATION_CHANNEL_ID = "ex6_fb_channel";

        //Creating the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Stock Price")
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    private void registerToServer(String token){
        JSONObject reqObject = new JSONObject();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, SERVER + "token/" + token, reqObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("REGISTRATION_TO_SERVER", "Token was successfully saved");
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REGISTRATION_TO_SERVER","Failed to save token " + error);
                    }
                });
        RequestQueueFetcher.getInstance(this).getQueue().add(req);
    }
}
