package com.macrohard.cowcal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by Amin on 6/4/15.
 */
public class StatsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Statistics");
        setContentView(R.layout.stats);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void openShop(View view) { startActivity(MainActivity.shopActivity); }
    public void openInv(View view) { startActivity(MainActivity.invActivity); }
    public void openStats(View view) { startActivity(MainActivity.statsActivity); }
    public void openMap(View view) { startActivity(MainActivity.mapActivity); }

}
