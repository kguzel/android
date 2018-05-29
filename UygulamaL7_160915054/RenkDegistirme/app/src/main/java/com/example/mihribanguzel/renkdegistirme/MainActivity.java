package com.example.mihribanguzel.renkdegistirme;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //bu örnek uygulamamızda butuna her tıklandığında servis yapısı kullanılarak arkada rastgele sayılar oluşturlacak
    //bu sayılarla da buton nesnesinin arkaplanı değiştirlecektir.


    private RenkServisi srvs;
    private boolean isBound = false;
    private Connection cnn = new Connection();

    public class Connection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RenkServisi.CustomBinder customBinder = (RenkServisi.CustomBinder) service;
            srvs = customBinder.getInstance();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    }

    protected void onStart() {
        super.onStart();
        Intent i = new Intent(this, RenkServisi.class);
        bindService(i, cnn, Context.BIND_AUTO_CREATE);
    }

    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(cnn);
            isBound = false;
        }
    }

    public void btnConnect_Click(View v) {
        if (isBound) {
            findViewById(R.id.view1).setBackgroundColor(Color.rgb(
                    srvs.getRandomNumber(),
                    srvs.getRandomNumber(),
                    srvs.getRandomNumber()
            ));

            findViewById(R.id.view2).setBackgroundColor(Color.rgb(
                    srvs.getRandomNumber(),
                    srvs.getRandomNumber(),
                    srvs.getRandomNumber()
            ));

            findViewById(R.id.view3).setBackgroundColor(Color.rgb(
                    srvs.getRandomNumber(),
                    srvs.getRandomNumber(),
                    srvs.getRandomNumber()
            ));
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
