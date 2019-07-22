package com.teknokrait.mussichusettsapp.alarm;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import timber.log.Timber;

/**
 * Created by Aprilian Nur Wakhid Daini on 7/21/2019.
 */
public class AlarmService extends Service {

    String TAG = "AlarmService";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent!=null){
            Timber.e(" Intent action :: %s", intent.getAction());
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.e("onDestroy ");
    }
}