package com.example.mihribanguzel.devamsizliktakip;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by kadirguzel on 5/12/18.
 */

public class AlarmManager extends BroadcastReceiver {

    private DatabaseHelper dbHelper;
    private static SQLiteDatabase db;
    static NotificationManager mNotificationManager;
    static Lesson lesson;
    static String userID;


    @Override
    public void onReceive(Context context, Intent intent) {

        HashMap<Integer,String> daylist = new HashMap<Integer,String>();
        daylist.put(Calendar.SUNDAY,"Pazar");
        daylist.put(Calendar.MONDAY,"Pazartesi");
        daylist.put(Calendar.TUESDAY,"Salı");
        daylist.put(Calendar.WEDNESDAY,"Çarşamba");
        daylist.put(Calendar.THURSDAY,"Perşembe");
        daylist.put(Calendar.FRIDAY,"Cuma");
        daylist.put(Calendar.SATURDAY,"Cumartesi");




        /*
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        // Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        //ringtone.play();

        */

        userID= intent.getStringExtra("userID");

        //db den derslere bak

        //DB objeleri
        dbHelper = new DatabaseHelper(context);
        dbHelper.CreateDataBase();
        db = dbHelper.getWritableDatabase();


        //aynı kullanıcı var mı diye kontrol edilir.
        Cursor cursor = db.rawQuery("SELECT * FROM tblDersProgrami where " +
                " k_id= '"+userID+"'", null);

        List<Lesson> lessonList=new ArrayList<Lesson>();

        if (cursor.moveToFirst()){
            do{
                Lesson lesson = new Lesson();
                lesson.setDers_id(cursor.getString(cursor.getColumnIndex("ders_id")));
                lesson.setDers_adi(cursor.getString(cursor.getColumnIndex("ders_adi")));
                lesson.setGun(cursor.getString(cursor.getColumnIndex("gun")));
                lesson.setDersdakika(cursor.getString(cursor.getColumnIndex("dersdakika")));
                lesson.setDevamsiz_H(cursor.getInt(cursor.getColumnIndex("Devamsiz_H")));
                lesson.setDerssaati(cursor.getString(cursor.getColumnIndex("derssaati")));
                lessonList.add(lesson);
            }while(cursor.moveToNext());
        }
        cursor.close();

      //  lesson=lessonList.get(2);
        // showPush(context,lesson);

        Calendar calendar = Calendar.getInstance();


        for(int i=0;i<lessonList.size();i++){

            if(  Integer.parseInt(lessonList.get(i).getDersdakika())==calendar.get(Calendar.MINUTE) &&
                    Integer.parseInt(lessonList.get(i).getDerssaati())==calendar.get(Calendar.HOUR_OF_DAY) &&
                    lessonList.get(i).getGun().equals(daylist.get(calendar.get(Calendar.DAY_OF_WEEK)))
                    ){
                      lesson=lessonList.get(i);
                      showPush(context,lesson);
                      break;
            }
        }


        for(int i=0;i<lessonList.size();i++){

            if( Integer.parseInt(lessonList.get(i).getDersdakika())==calendar.get(Calendar.MINUTE)+5 &&
                    Integer.parseInt(lessonList.get(i).getDerssaati())==calendar.get(Calendar.HOUR_OF_DAY) &&
                    lessonList.get(i).getGun().equals(daylist.get(calendar.get(Calendar.DAY_OF_WEEK)))
                    ){
                lesson=lessonList.get(i);
                showAlertPush(context,lesson);
                break;
            }
        }

    }


    private void showAlertPush(Context context,Lesson lesson) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("");
        bigText.setBigContentTitle("5 dakika sonra "+lesson.getDers_adi()+" dersi başlayacaktır.");
        bigText.setSummaryText("");

        //mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Devamsılık Takibi");
        mBuilder.setContentText("5 dakika sonra "+lesson.getDers_adi()+" dersi başlayacaktır.");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        //mBuilder.setStyle(bigText);



        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        mNotificationManager.notify(0, mBuilder.build());


    }


    private void showPush(Context context,Lesson lesson){


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext(), "notify_001");
        Intent ii = new Intent(context.getApplicationContext(), LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("");
        bigText.setBigContentTitle(lesson.getDers_adi() + " dersine girdiniz mi?");
        bigText.setSummaryText("");

        //mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Devamsılık Takibi");
        mBuilder.setContentText(lesson.getDers_adi() + " dersine girdiniz mi?");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        //mBuilder.setStyle(bigText);


        RemoteViews notificationView = new RemoteViews(context.getPackageName(),
                R.layout.pushlayout);



        notificationView.setTextViewText(R.id.dersText, lesson.getDers_adi() + " dersine girdiniz mi?");

        Intent switchIntent = new Intent(context, switchButtonListener.class);

        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0,
                switchIntent, 0);


        notificationView.setOnClickPendingIntent(R.id.yes,
                pendingSwitchIntent);

        Intent switchIntent2 = new Intent(context, switchButtonListener2.class);
        PendingIntent pendingSwitchIntent2 = PendingIntent.getBroadcast(context, 0,
                switchIntent2, 0);

        notificationView.setOnClickPendingIntent(R.id.no,
                pendingSwitchIntent2);


        mBuilder.setContent(notificationView);



        mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        mNotificationManager.notify(0, mBuilder.build());

    }



    public static class switchButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mNotificationManager.cancelAll();
        }
    }


    public static class switchButtonListener2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            //Üye bilgileri veritabanına kaydediliyor.
            ContentValues cv = new ContentValues();

            String ders_id = lesson.getDers_id();
            cv.put("Y_Devamsiz",(lesson.getY_Devamsiz()+1));

            db.update("tblDersProgrami", cv, "ders_id" + " = ? AND " + "k_id" + " = ?",
                    new String[]{ders_id, userID});

            mNotificationManager.cancelAll();

        }
    }

}
