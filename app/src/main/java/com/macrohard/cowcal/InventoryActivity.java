package com.macrohard.cowcal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Amin on 6/4/15.
 */
public class InventoryActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);
        setTitle("My Inventory");
        Intent intent = getIntent();

        TextView invtext = (TextView) findViewById(R.id.invtext);

        if (invtext != null)
            invtext.setText("hi!");
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
