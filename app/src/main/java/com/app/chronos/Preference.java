package com.app.chronos;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.app.chronos.ui.home.AddedEvent;
import com.app.chronos.ui.home.AlarmBrodcast;
import com.app.chronos.ui.home.DAOadded;
import com.app.chronos.ui.home.NotificationMessage;
import com.bumptech.glide.ListPreloader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.app.chronos.ui.home.CalendarAdapter.monthYearFromDate;


public class Preference extends PreferenceActivity {

    TextToSpeech tts;
    CheckBoxPreference reminder;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate( Bundle savedInstanceStete ) {
        super.onCreate(savedInstanceStete);
        addPreferencesFromResource(R.xml.settings);



//Load_setting();

    }

    @RequiresApi(api = 31)

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
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");


        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        contentView.setImageViewResource(R.id.image, R.drawable.ic_baseline_info_24);
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE);

        contentView.setTextViewText(R.id.message, text);
        contentView.setTextViewText(R.id.date, date);
        contentView.setTextViewText(R.id.timeStart, timeS);
        contentView.setTextViewText(R.id.timeEnd, timeE);
        contentView.setTextViewText(R.id.my, MMyy);
        mBuilder.setSmallIcon(R.drawable.ic_baseline_info_24);
        mBuilder.setAutoCancel(true);
        mBuilder.setOngoing(true);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setOnlyAlertOnce(true);
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
        mBuilder.setContent(contentView);
        mBuilder.setContentIntent(pendingIntent);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean swtch = sp.getBoolean("reminder", false);
        if (swtch == true) {

            contentView.setTextViewText(R.id.timeStart, timeS);

            if (contentView.equals(true)) {

                tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit ( int i ) {
                        if (i == TextToSpeech.SUCCESS) {
                            tts.setLanguage(Locale.ENGLISH);
                            tts.setSpeechRate(1.0f);
                            tts.setPitch(1);
                            tts.speak("Your activity " + "is comming after 15 minutes", TextToSpeech.QUEUE_ADD, null);
                            return;
                        }
                    }
                });
            } else {

            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            NotificationChannel channel = new NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);


            Notification notification = mBuilder.build();
            notificationManager.notify(1, notification);

        }




    }












}
