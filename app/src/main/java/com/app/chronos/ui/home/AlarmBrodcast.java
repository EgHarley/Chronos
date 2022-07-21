package com.app.chronos.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.app.chronos.R;

public class AlarmBrodcast extends BroadcastReceiver {



    Context context;
    private  TextToSpeech mTts;
    private static boolean isSpeaking = false;
        TextToSpeech tts;
        boolean y = true;
    private static BroadcastReceiver receiver;
    private int resultCode;
    private Intent startingIntent;

    public static boolean wasScreenOn = true;

    @RequiresApi(api = 31)
        @Override
        public void onReceive ( Context context, Intent intent ) {




        Bundle bundle = intent.getExtras();
        String text = bundle.getString("event");
        String date = bundle.getString("date");
        String MMyy = bundle.getString("monthyear");
        String timeS = bundle.getString("timeStart");
        String timeE = bundle.getString("timeEnd");
        String locs = bundle.getString("location");
        String dess = bundle.getString("description");
        String CH = bundle.getString("reminder");
        String TS = bundle.getString("voiceNotif");


        //tts.speak("Your activity " + text + "is comming after 15 minutes", TextToSpeech.QUEUE_ADD, null);

        //String texts = text.getText().toString();

        //String Rdes = bundle.getString("description");

        //Click on Notification

        Intent intent1 = new Intent(context, NotificationMessage.class);

        intent1.putExtra("event", text);
        intent1.putExtra("date", date);
        intent1.putExtra("monthyear", MMyy);
        intent1.putExtra("location", locs);
        intent1.putExtra("description", dess);
        intent1.putExtra("timeStart", timeS);
        intent1.putExtra("timeEnd", timeE);
        intent1.putExtra("reminder", CH);
        intent1.putExtra("voiceNotif", TS);





        //Notification Builder
        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(TextToSpeech.SUCCESS);
        //tts.speak("hehehe",TextToSpeech.QUEUE_ADD):
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001")
                //.setSound(defaultSoundUri)
                ;


        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        contentView.setImageViewResource(R.id.image, R.drawable.ic_baseline_info_24);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        contentView.setTextViewText(R.id.message, text);
        contentView.setTextViewText(R.id.date, date);
        contentView.setTextViewText(R.id.timeStart, timeS);
        contentView.setTextViewText(R.id.remind, CH);
        contentView.setTextViewText(R.id.timeEnd, timeE);
        contentView.setTextViewText(R.id.my, MMyy);
        mBuilder.setSmallIcon(R.drawable.ic_baseline_info_24);
        mBuilder.setAutoCancel(true);
        mBuilder.setOngoing(true);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        //mBuilder.setSound(defaultSoundUri);
        //mBuilder.setOnlyAlertOnce(true);
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH|TextToSpeech.SUCCESS;
        mBuilder.setContent(contentView);
        mBuilder.setContentIntent(pendingIntent);



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ) {
        String channelId = "channel_id";
        NotificationChannel channel = new NotificationChannel(channelId, "channel_name", NotificationManager.IMPORTANCE_HIGH);
        channel.enableVibration(true);
        channel.enableLights(true);
        notificationManager.createNotificationChannel(channel);
        notificationManager.cancel(1);
        notificationManager.getActiveNotifications();
        mBuilder.setChannelId(channelId);

        Notification notification = mBuilder.build();
        notificationManager.notify(6, notification);
            }

                //notificationManager.equals(tts);





    }


}


