package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Random;

public class Notification_receiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        String[] quotesArray = new String[] {
                "We were on a break!",
                "Well, maybe I don’t need your money. Wait, wait, I said maybe!",
                "Joey doesn’t share food!",
                "Hi, I’m Chandler. I make jokes when I’m uncomfortable.",
                "I wish I could, but I don’t want to",
                "Pivot!",
                "It tastes like feet!",
                "What’s not to like? Custard: good. Jam: good. Meat: good!",
                "Your little Harmonica is hammered.",
                "How you doin'?",
                "Oh. My. God.",
                "I don’t even have a ‘pla.'"
        };
        Random rand = new Random();
        int randLocation = rand.nextInt(quotesArray.length); //Generating a random number to chose a quote from the array

        Intent repeating_intent = new Intent(context,Repeating_activity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //In case version is 26 or upper
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("ID","channel",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationChannel.getId())
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.star_on)
                    .setContentTitle("FRIENDS Quote")
                    .setContentText(quotesArray[randLocation])
                    .setAutoCancel(true);
            notificationManager.notify(100,builder.build());
        }
        else{ //In case version is 25 or under
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.star_on)
                    .setContentTitle("FRIENDS Quote")
                    .setContentText(quotesArray[randLocation])
                    .setAutoCancel(true);
            notificationManager.notify(100,builder.build());
        }
    }
}
