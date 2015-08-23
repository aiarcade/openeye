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

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class AlertActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */


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

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.

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
