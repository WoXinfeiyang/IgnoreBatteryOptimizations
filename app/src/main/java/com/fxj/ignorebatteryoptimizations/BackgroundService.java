package com.fxj.ignorebatteryoptimizations;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;


public class BackgroundService extends Service {

    private static final String TAG = BackgroundService.class.getSimpleName();
    private static final int MSG=0X01;
    private int count=0;

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG:
                    count++;
                    Log.e(TAG,"##Handler.handleMessage##count="+count);
                    sendEmptyMessageDelayed(MSG,1000);
                    break;
            }
        }
    };

    public void onCreate() {
        Log.d(TAG, "**onCreate**");
    }


    public IBinder onBind(Intent intent) {
        Log.d(TAG, "**onBind**");
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "**onStartCommand**");
        mHandler.sendEmptyMessage(MSG);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "**onLowMemory**");
    }

    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "**onUnbind**");
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "**onDestroy**");
    }


}
