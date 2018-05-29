package com.example.mihribanguzel.devamsizliktakip;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LessonsFollowListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String userID;
    List<Lesson> lessonList;
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_follow_list);
        //DB objeleri
        dbHelper = new DatabaseHelper(LessonsFollowListActivity.this);
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
        Cursor cursor = db.rawQuery("SELECT * FROM tblDersProgrami where " +
                " k_id= '"+userID+"'  order by (gun)", null);

        if (cursor.moveToFirst()){
            do{
                Lesson lesson = new Lesson();
                lesson.setDers_id(cursor.getString(cursor.getColumnIndex("ders_id")));
                lesson.setDers_adi(cursor.getString(cursor.getColumnIndex("ders_adi")));
                lesson.setGun(cursor.getString(cursor.getColumnIndex("gun")));
                lesson.setDersdakika(cursor.getString(cursor.getColumnIndex("dersdakika")));
                lesson.setDevamsiz_H(cursor.getInt(cursor.getColumnIndex("Devamsiz_H")));
                lesson.setDerssaati(cursor.getString(cursor.getColumnIndex("derssaati")));
                lesson.setY_Devamsiz(cursor.getInt(cursor.getColumnIndex("Y_Devamsiz")));
                lessonList.add(lesson);
            }while(cursor.moveToNext());
        }
        cursor.close();

        LessonsAdapter lessonsAdapter = new LessonsAdapter(this, lessonList);

        listView.setAdapter(lessonsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openExitDialog(position);
            }

        });
    }

    // Dialog ile gosterme
    private void openExitDialog(final int position) {

        final Lesson lesson=lessonList.get(position);

        final String content = ("Bu dersi düzenlemek veya silmek istiyor musunuz?");

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(content);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "SİL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        lessonList.remove(position);
                        ((LessonsAdapter) listView.getAdapter()).notifyDataSetChanged();

                        //silme
                        db.delete("tblDersProgrami", "ders_id" + "=" + lesson.getDers_id(), null) ;
                    }
                });

        builder1.setNegativeButton(
                "DÜZENLE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), EditFollowActivity.class);
                        intent.putExtra("ders_adi", lesson.getDers_adi());
                        intent.putExtra("ders_id", lesson.getDers_id());
                        intent.putExtra("dersdakika", lesson.getDersdakika());
                        intent.putExtra("devamsiz_H", lesson.getDevamsiz_H());
                        intent.putExtra("gun", lesson.getGun());
                        intent.putExtra("k_id", lesson.getK_id());
                        intent.putExtra("derssaati", lesson.getDerssaati());
                        intent.putExtra("userID", userID);

                        startActivity(intent);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
