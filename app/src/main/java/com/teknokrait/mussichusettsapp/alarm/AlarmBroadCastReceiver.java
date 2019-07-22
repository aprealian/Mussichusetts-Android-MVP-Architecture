package com.teknokrait.mussichusettsapp.alarm;

import android.app.AliasActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.gson.Gson;
import com.teknokrait.mussichusettsapp.R;
import com.teknokrait.mussichusettsapp.model.Event;
import com.teknokrait.mussichusettsapp.util.Constants;
import com.teknokrait.mussichusettsapp.view.activity.AlarmActivity;
import timber.log.Timber;

import java.text.SimpleDateFormat;

/**
 * Created by Aprilian Nur Wakhid Daini on 7/21/2019.
 */
public class AlarmBroadCastReceiver extends BroadcastReceiver {
    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
//        if(intent!=null){
//            Timber.e("Intent action:: %s", intent.getAction());
//
//            Intent intent1 = new Intent();
//            intent1.setAction("JOB INTENT SERVICE");
//            AlarmJobIntentService.enqueueWork(context,AlarmJobIntentService.class,1,intent1);
//        }

        Gson gson = new Gson();
        String strObj = intent.getStringExtra("event");
        Event event = gson.fromJson(strObj, Event.class);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a, dd-MMM-yyyy");
        String eventDate = formatter.format(event.getEventDate());
        //String eventDate = "12:22, Juni 12 2019";


        //you might want to check what's inside the Intent
        if(intent.getStringExtra("myAction") != null &&
                intent.getStringExtra("myAction").equals("notify")){
            NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    //example for large icon
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(event.getEventName())
                    .setContentText(eventDate)
                    .setOngoing(false)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Intent i = new Intent(context, AlarmActivity.class);
            Gson newGson = new Gson();
            i.putExtra("event", newGson.toJson(event));
            PendingIntent pendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            i,
                            PendingIntent.FLAG_ONE_SHOT
                    );
            // example for blinking LED
            builder.setLights(0xFFb71c1c, 1000, 2000);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);
            builder.setContentIntent(pendingIntent);
            manager.notify(12345, builder.build());
        }

    }
}