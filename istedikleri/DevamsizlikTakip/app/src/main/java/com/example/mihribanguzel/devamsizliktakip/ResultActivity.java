package com.example.mihribanguzel.devamsizliktakip;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String userID;
    List<Lesson> lessonList;
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //DB objeleri
        dbHelper = new DatabaseHelper(ResultActivity.this);
        dbHelper.CreateDataBase();
        db = dbHelper.getWritableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFromDB();
    }

    private void getListFromDB(){

        // Gonderilen extralar okundu
        final Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        listView = (ListView) findViewById(R.id.listview);

        lessonList = new ArrayList<Lesson>();

        //aynı kullanıcı var mı diye kontrol edilir.
        Cursor cursor = db.rawQuery("SELECT sum(y_devamsiz) as count,ders_id,ders_adi,gun,dersdakika,Devamsiz_H,derssaati FROM tblDersProgrami where " +
                " k_id= '"+userID+"'  group by(ders_adi)", null);



        if (cursor.moveToFirst()){
            do{
                Lesson lesson = new Lesson();
                lesson.setDers_id(cursor.getString(cursor.getColumnIndex("ders_id")));
                lesson.setDers_adi(cursor.getString(cursor.getColumnIndex("ders_adi")));
                lesson.setGun(cursor.getString(cursor.getColumnIndex("gun")));
                lesson.setDersdakika(cursor.getString(cursor.getColumnIndex("dersdakika")));
                lesson.setDevamsiz_H(cursor.getInt(cursor.getColumnIndex("Devamsiz_H")));
                lesson.setDerssaati(cursor.getString(cursor.getColumnIndex("derssaati")));
                lesson.setY_Devamsiz(cursor.getInt(cursor.getColumnIndex("count")));
                lessonList.add(lesson);
            }while(cursor.moveToNext());
        }
        cursor.close();

        ResultAdapter lessonsAdapter = new ResultAdapter(this, lessonList);

        listView.setAdapter(lessonsAdapter);

    }


}
