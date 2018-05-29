package com.example.mihribanguzel.daire;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;
    DisplayMetrics metrics = new DisplayMetrics();
    AnimatedView animatedView = null;
    ShapeDrawable mDrawable = new ShapeDrawable();
    ShapeDrawable mDrawable2 = new ShapeDrawable();
    public static int x;
    public static int y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        animatedView = new AnimatedView(this);
        animatedView.setBackgroundColor(Color.BLACK);

        setContentView(animatedView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x -= (int) event.values[0];
            y += (int) event.values[1];

        }
    }

    public class AnimatedView extends android.support.v7.widget.AppCompatImageView {


        static final int height = 50;
        static final int width = 50;

        int maxX = metrics.widthPixels;
        int maxY = metrics.heightPixels;

        public AnimatedView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub

            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable2 = new ShapeDrawable(new OvalShape());

            mDrawable.setBounds(x, y, x + width, y + height);
            mDrawable2.setBounds(x+100, y, x + 2* width, y + + 2* height);

        }

        @Override
        protected void onDraw(Canvas canvas) {

            Log.d("x değerleri", maxX + "/" + x);
            Log.d("y değerleri", maxY + "/" + y);

            if (x < 0)
                x = 0;
            else if (x > maxX - 500)
                x = maxX - 500;

            if (y < 0)
                y = 0;
            else if (y > maxY - 500)
                y = maxY - 500;

            mDrawable.setBounds(x, y, x + width, y + height);
            mDrawable.draw(canvas);
            mDrawable.getPaint().setColor(Color.YELLOW);


            mDrawable2.setBounds(y+width, x+width, y + 2* width, x + 2*height);
            mDrawable2.draw(canvas);
            mDrawable2.getPaint().setColor(Color.RED);

            invalidate();
        }
    }
}

