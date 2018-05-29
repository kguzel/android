package com.example.mihribanguzel.isik;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_LIGHT;

public class MainActivity extends AppCompatActivity {

    //sensör örneğimiz bu kadar basit.

    //ilk önce değişkenlerimizi tanımlıyoruz.1 sensor manager ve verileri yazıdrabilmek için textview nesneleri
    SensorManager mSensorManager;
    TextView ivmeText, isikText;
    Sensor ivme, isik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //arayüz elemanlarımıza erişiyoruz
        ivmeText = (TextView) findViewById(R.id.ivme);
        isikText = (TextView) findViewById(R.id.isik);

        //sensör erişimi için manager nesnemizi oluşturuyoruz
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ivme = mSensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
        isik = mSensorManager.getDefaultSensor(TYPE_LIGHT);

        mSensorManager.registerListener(sensorDinleyici, ivme, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorDinleyici, isik, SensorManager.SENSOR_DELAY_GAME);

        //sensorDinleyici fonksiyonunu şimdi yazacağız
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorDinleyici, ivme, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(sensorDinleyici, isik, SensorManager.SENSOR_DELAY_GAME);

    }

    SensorEventListener sensorDinleyici = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            Sensor sensor = event.sensor;//event.sensor ile hangi sensor çalışmış onu öğreniyoruz
            if (sensor.getType() == TYPE_ACCELEROMETER)
                ivmeText.setText(event.sensor.getName() + "" + event.values[0] + "\n" + event.values[1] + "\n" + event.values[2]);
            else if (sensor.getType() == TYPE_LIGHT) {
                isikText.setText(event.sensor.getName() + "\n" + event.values[0]);
            }

            //sensöre göre arayüz elemanlarının içeriğini güncelliyoruz.
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
