package com.macrohard.cowcal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity implements StepListener {

    private Pedometer ped;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Model model;
    private PowerManager.WakeLock wl;
    private Random r;

    public static final long ITEM_TIME = 1500;
    public static TextView count_txt;
    public static TextView item_txt;
    public static Intent invActivity;
    public static Intent statsActivity;
    public static Intent shopActivity;
    public static Intent mapActivity;
    public static Intent settings_activity;
    public static Intent home_activity;

    public static long count;
    public static SharedPreferences saveData;

    public final static long DEFAULT_COUNT = 0;
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        model = new Model();
        count_txt = (TextView) findViewById(R.id.pedometer);
        item_txt = (TextView) findViewById(R.id.item_text);

        if (count_txt != null)
            count_txt.setText("0 Steps");

        if (item_txt != null)
            item_txt.setText("");

        ped = new Pedometer();
        ped.setStepListener(this);

        r = new Random();
        count = 0;
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(ped, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag");
        wl.acquire();

        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        mapActivity = new Intent(this, MapActivity.class);
        shopActivity = new Intent(this, ShopActivity.class);
        invActivity = new Intent(this, InventoryActivity.class);
        statsActivity = new Intent(this, StatsActivity.class);
        settings_activity = new Intent(this, SettingsActivity.class);
        home_activity = new Intent(this, MainActivity.class);
        saveData = getPreferences(Context.MODE_PRIVATE);

        load();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!wl.isHeld())
            wl.acquire();
        mSensorManager.registerListener(ped, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wl.isHeld())
            wl.release();
        mSensorManager.unregisterListener(ped);

        System.out.println("Saving count");
        SharedPreferences.Editor e = saveData.edit();
        e.putLong(getString(R.string.step_count), count);
        e.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings)
            open_settings();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("count", count);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state from savedInstanceState
        count = savedInstanceState.getLong("count");
        count_txt.setText(count + " Steps");
    }

    public void onStep()
    {
        count++;
        count_txt.setText(count + "Steps");

        int num = r.nextInt(100);

        if ((count % 2) == 0 && num <= 50) {
            long c = System.currentTimeMillis();
            long diff = 0;

            item_txt.setText("You won an item!");
        }

        model.tick(getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public void load() {
        count = saveData.getLong(getString(R.string.step_count), DEFAULT_COUNT);
        count_txt.setText(count + " Steps");

        Log.i(TAG, "loading");
    }

    public void open_settings() {
        startActivity(settings_activity);
    }

    public void openShop(View view) { startActivity(shopActivity); }
    public void openInv(View view) { startActivity(invActivity); }
    public void openStats(View view) { startActivity(statsActivity); }
    public void openMap(View view) { startActivity(mapActivity); }
}