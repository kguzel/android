//App1Activity.java


package com.example.mihribanguzel.uygulamal5_160915054;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

public class App1Activity extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;

    private int counter;

    Timer timer;
    TimerTask timerTask;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app1);

        imageView=(ImageView)findViewById(R.id.imageView);

        editText=(EditText)findViewById(R.id.editText);

        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        toggleButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //muzik calma
                MediaPlayer objPlayer = MediaPlayer.create(App1Activity.this,R.raw.tik);
                objPlayer.start();

                if(toggleButton.isChecked()){
                    startTimer();
                }else{
                    objPlayer.stop();
                    objPlayer.release();
                    stoptimertask();
                }
            }
        });
    }

    public void startTimer() {

        timer = new Timer();

        initializeTimerTask();

        //tarifeleme
        timer.schedule(timerTask, Integer.parseInt(editText.getText().toString())*1000, Integer.parseInt(editText.getText().toString())*1000);
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                // ekranda 1 sey degismek icin mecbur
                handler.post(new Runnable() {
                    public void run() {

                        counter++;

                        //muzik calma
                        MediaPlayer objPlayer = MediaPlayer.create(App1Activity.this,R.raw.button_click_2);
                        objPlayer.start();

                        if(counter%2==1){
                            imageView.setImageResource(R.drawable.one);
                        }else{
                            imageView.setImageResource(R.drawable.two);
                        }
                    }
                });
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stoptimertask();
    }
}
