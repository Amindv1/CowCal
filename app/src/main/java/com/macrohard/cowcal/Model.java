package com.macrohard.cowcal;

import android.view.View;
import android.widget.PopupWindow;

import java.util.Random;

/**
 * Created by Amin on 2/24/15.
 */
public class Model
{
    private Random r;
    private final int SMALLREWARD = 5;
    private final int MEDIUMREWARD = 500;
    private final int LARGEREWARD = 1000;

    public void tick(View v)
    {
        if ((MainActivity.count % LARGEREWARD) == 0)
        {

        }
        else if ((MainActivity.count % MEDIUMREWARD) == 0)
        {

        }
        else if ((MainActivity.count % SMALLREWARD) == 0)
        {
            PopupWindow pop = new PopupWindow();
            pop.showAsDropDown(v);
        }
    }

}
