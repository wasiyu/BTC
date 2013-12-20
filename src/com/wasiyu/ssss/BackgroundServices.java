package com.wasiyu.ssss;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

/**
 */
public class BackgroundServices extends Service {
    private Handler mWorkHandler;
    private Manager mManager;

    private Runnable mWorkRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("TEST", "BBBBBBBBBB");
            mWorkHandler.postDelayed(mWorkRunnable, 2000);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread("WorkThread");
        handlerThread.start();
        mWorkHandler = new Handler(handlerThread.getLooper());
        mWorkHandler.post(mWorkRunnable);
        try {
            mManager = new Manager();
            mManager.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
