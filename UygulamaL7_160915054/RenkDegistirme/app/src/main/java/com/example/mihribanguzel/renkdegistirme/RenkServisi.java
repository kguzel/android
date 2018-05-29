package com.example.mihribanguzel.renkdegistirme;

/**
 * Created by mguzel on 4/12/18.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;


public class RenkServisi extends Service {

    private IBinder mBinder = new CustomBinder();

    public class CustomBinder extends Binder {
        public RenkServisi getInstance() {
            return RenkServisi.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    public int getRandomNumber() {
        Random rnd = new Random();
        return rnd.nextInt(255);
    }
}
