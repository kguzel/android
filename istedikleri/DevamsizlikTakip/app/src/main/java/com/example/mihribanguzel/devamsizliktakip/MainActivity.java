package com.example.mihribanguzel.devamsizliktakip;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

private Button createImageButton;
private Button editImageButton;
private Button seeImageButton;
private TextView nameTextView;
private Button exitButton;
private Button addLessonImageButton;
private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setContentView(R.layout.activity_main);

        // Objeleri xmlden alalım
        createImageButton = (Button) findViewById(R.id.createImageButton);
        editImageButton = (Button) findViewById(R.id.editImageButton);
        seeImageButton = (Button) findViewById(R.id.seeImageButton);
        nameTextView=(TextView) findViewById(R.id.nameTextView);
        exitButton = (Button) findViewById(R.id.exitButton);
        addLessonImageButton = (Button) findViewById(R.id.addLessonImageButton);


        // Gonderilen extralar okundu
        final Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        userID = intent.getStringExtra("userID");
        nameTextView.setText(""+name);

        createImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), CreateActivity.class);
                i.putExtra("userID", userID);
                startActivity(i);

            }
        });

        editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), LessonsFollowListActivity.class);
                i.putExtra("userID", userID);
                startActivity(i);

            }
        });

        seeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ResultActivity.class);
                i.putExtra("userID", userID);
                startActivity(i);

            }
        });


        addLessonImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddLessonActivity.class);
                i.putExtra("userID", userID);
                startActivity(i);
            }
        });




        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExitDialog();

                /*
                Toast.makeText(getApplicationContext(),"Alarm Ayarlandı!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), AlarmManager.class);
                intent.putExtra("dersID" ,3);
                intent.putExtra("saat" ,14);
                intent.putExtra("dakika" ,3);
                intent.putExtra("saniye" ,3);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                android.app.AlarmManager alarmManager = (android.app.AlarmManager) getSystemService(Context.ALARM_SERVICE);


                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();

                calSet.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calSet.set(Calendar.HOUR_OF_DAY, 17);
                calSet.set(Calendar.MINUTE,20);
                calSet.set(Calendar.SECOND, 00);
                calSet.set(Calendar.MILLISECOND, 00);

                alarmManager.setInexactRepeating(android.app.AlarmManager.RTC, calSet.getTimeInMillis(), 7*24*60*60*1000, pendingIntent);
                */
            }
        });


        startService();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void startService(){

        android.app.AlarmManager am=(android.app.AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmManager.class);
        intent.putExtra("userID", userID);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        am.setRepeating(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 100 , pendingIntent);

    }


    // Dialog ile gosterme
    private void openExitDialog() {

        final String content = ("Uygulamadan çıkmak istiyor musunuz?");

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage(content);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Evet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                     finish();
                    }
                });

        builder1.setNegativeButton(
                "Hayır",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    @Override
    public void onBackPressed() {
        openExitDialog();
    }
}
