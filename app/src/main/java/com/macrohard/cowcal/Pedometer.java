package com.macrohard.cowcal;

/**
 * Created by Amin on 1/13/15.
 */
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Pedometer implements SensorEventListener
{
    private final static String TAG = "StepDetector";
    private float mLimit = 6.66F;
    private float mLastValues[] = new float[3 * 2];
    private float mScale[] = new float[2];
    private float mYOffset;
    private static long pre = 0;
    private static boolean b = false;
    //the amount of time that has to go by before the step count will be incremented again
    private long max_step_time;

    private float mLastDirections[] = new float[3 * 2];
    private float mLastExtremes[][] = {new float[3 * 2], new float[3 * 2]};
    private float mLastDiff[] = new float[3 * 2];
    private int mLastMatch = -1;

    private StepListener mStepListener;

    public Pedometer() {
        int h = 480;
        mYOffset = h * 0.5f;
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));

        max_step_time = 600;
    }

    public Pedometer(long max_step_time) {
        this();
        this.max_step_time = (long) max_step_time;
    }

    public Pedometer(long max_step_time, float mLimit) {
        this(max_step_time);
        this.mLimit = mLimit;
    }

    public void setStepListener(StepListener sl)
    {
        mStepListener = sl;
    }

    public void onSensorChanged(SensorEvent event)
    {
        Sensor sensor = event.sensor;
        synchronized (this)
        {
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            {
                float vSum = 0;
                for (int i = 0; i < 3; i++)
                {
                    final float v = mYOffset + event.values[i] * mScale[1];
                    vSum += v;
                }
                int k = 0;
                float v = vSum / 3;

                float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));

                if (direction == -mLastDirections[k])
                {
                    // Direction changed
                    int extType = (direction > 0 ? 0 : 1); // minumum or maximum?
                    mLastExtremes[extType][k] = mLastValues[k];
                    float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1 - extType][k]);

                    if (diff > mLimit)
                    {
                        boolean isAlmostAsLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                        boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                        boolean isNotContra = (mLastMatch != 1 - extType);

                        if (isAlmostAsLargeAsPrevious && isPreviousLargeEnough && isNotContra)
                        {
                                long dif = System.currentTimeMillis() - pre;

                                if (!b || dif >= max_step_time)
                                {
                                    b = true;
                                    mStepListener.onStep();
                                    pre = System.currentTimeMillis();

                                    Log.i(TAG, "step");
                                }

                            mLastMatch = extType;
                        }
                        else
                        {
                            mLastMatch = -1;
                        }
                    }
                    mLastDiff[k] = diff;
                }
                mLastDirections[k] = direction;
                mLastValues[k] = v;

            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

}