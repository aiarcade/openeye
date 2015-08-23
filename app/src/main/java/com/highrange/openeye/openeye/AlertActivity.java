package com.highrange.openeye.openeye;

import com.highrange.openeye.openeye.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.telephony.SmsManager;


public class AlertActivity extends Activity {

    private static final boolean AUTO_HIDE = true;


    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;


    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;




    SimulationData data=new SimulationData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alert);


        PlayGifView pGif = (PlayGifView) findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.alarm);
        playAlert();
        playAlert();
        new CountDownTimer(data.alertTimeout, 1000) {


            TextView time= (TextView)findViewById(R.id.textView);
            public void onTick(long millisUntilFinished) {
                //time.setText(Long.toString(millisUntilFinished / 1000));
            }

            public void onFinish() {
                sendSmsAndFinish();
            }
        }.start();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);



    }

    public void cancelActivity(View v)
    {

        finish();

    }


    public void sendSmsAndFinish()
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(data.defaultPhoneNumber, null,data.alertMsg, null, null);
        Intent intent = new Intent(AlertActivity.this, OpenEyeService.class);
        stopService(intent);
        finish();

    }

    public void playAlert()
    {

        MediaPlayer alarmPlayer = MediaPlayer.create(getApplicationContext(),R.raw.alarm);
        alarmPlayer.start();
    }
}
