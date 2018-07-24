package com.andrei.lapitchat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String NOTIFICATION_ID = "my_channel_01";

        if (remoteMessage.getData().size() > 0) {

            String notificationTitle = remoteMessage.getNotification().getTitle();
            String notificationMessage = remoteMessage.getNotification().getBody();

            String click_action = remoteMessage.getNotification().getClickAction();
            String from_user_id = remoteMessage.getData().get("from_user_id");

            System.out.println(notificationTitle);
            System.out.println(notificationMessage);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


                String CHANNEL_ID = "my_channel_01";
                CharSequence name = "my_channel";
                String Description = "This is my channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(false);
                mNotifyMgr.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this, NOTIFICATION_ID)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle(notificationTitle)
                            .setContentText(notificationMessage);

            Intent resultIntent = new Intent(click_action);
            resultIntent.putExtra("user_id", from_user_id);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);

            int mNotificationId = (int) System.currentTimeMillis();
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }
    }
}
