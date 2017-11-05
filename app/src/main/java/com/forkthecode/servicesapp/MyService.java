package com.forkthecode.servicesapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;

public class MyService extends Service {

    MediaPlayer player;
    Notification notification;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

//        throw new UnsupportedOperationException("Not yet implemented");


        return new MyBinder();
    }


    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        player.start();
        Intent actionIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,actionIntent,0);
        notification = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Title").setContentIntent(pendingIntent).build();
        startForeground(100,notification);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
    }

    void play(){
        player.start();
    }

    void pause(){
        player.pause();
    }


    public class MyBinder extends Binder {

        public MyService getService(){
            return MyService.this;
        }

    }


}
