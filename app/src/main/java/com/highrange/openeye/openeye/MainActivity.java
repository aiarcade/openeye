package com.highrange.openeye.openeye;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

import java.util.List;

public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //startOpenServiceQuiet();
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void startOpenEyeService(View v)
    {


        System.out.println("Starting ...");
        Intent intent = new Intent(MainActivity.this, OpenEyeService.class);

        startService(intent);
    }

    public void stopOpenEyeService(View v)
    {

        System.out.println("Stoping ...");
        Intent intent = new Intent(MainActivity.this, OpenEyeService.class);
        stopService(intent);

    }

    public  void startOpenServiceQuiet()
    {

        Intent intent = new Intent(MainActivity.this, OpenEyeService.class);
        stopService(intent);
    }

}
