package com.highrange.openeye.openeye;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

public class OpenEyeService extends Service {

    private SimulationData data;


    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        System.out.println("Service created");

        isRunning = true;
        data=new SimulationData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int counter=0;
                while(true) {
                    counter=counter+1;
                    if(counter==data.gpsStart) {
                        Notify(data.gpsMessage);
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.gps);
                        mediaPlayer.start();
                    }
                    if(counter==data.abnormalActivityStart){
                        Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                    try {


                        Thread.sleep(data.timeInterval);
                    }
                    catch (Exception e) {

                    }
                    if(isRunning==false){
                            break;
                    }

                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }

    private void Notify(String notText){

        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.openeye)
                        .setContentTitle("OpenEye")
                        .setContentText(notText);

        Intent resultIntent = new Intent(this, MainActivity.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int id=0;
        mNotificationManager.notify(id, mBuilder.build());



    }

    @Override
    public IBinder onBind(Intent arg0) {
        System.out.println("On bind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;
        System.out.println("Service onDestroy");

    }
}