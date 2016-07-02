package com.macrohard.cowcal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Amin on 6/6/15.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void reset(View view) {
        MainActivity.count = 0;
        MainActivity.count_txt.setText(MainActivity.DEFAULT_COUNT + " Steps");

        SharedPreferences.Editor e = MainActivity.saveData.edit();
        e.putLong(getString(R.string.step_count), MainActivity.count);
        e.apply();
    }

    public void home(View view) {
        startActivity(MainActivity.home_activity);
    }
}
