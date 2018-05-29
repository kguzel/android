package com.example.mihribanguzel.devamsizliktakip;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EditFollowActivity extends AppCompatActivity {


    private Spinner dayspinner;
    private EditText timeEditText;
    private EditText time2EditText;
    private EditText lessonEditText;
    private Button entryButton;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Spinner lessonsSpinner;
    private List<Lesson> lessonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Gonderilen extralar okundu
        final Intent intent = getIntent();
        String name = intent.getStringExtra("ders_adi");
        String ders_id = intent.getStringExtra("ders_id");
        String dersdakika = intent.getStringExtra("dersdakika");
        String devamsiz_H = intent.getStringExtra("devamsiz_H");
        String gun = intent.getStringExtra("gun");
        String derssaati = intent.getStringExtra("derssaati");


        dayspinner = (Spinner) findViewById(R.id.dayspinner);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        time2EditText = (EditText) findViewById(R.id.time2EditText);
        lessonsSpinner = (Spinner) findViewById(R.id.lessonsSpinner);


        //gun setle
        dayspinner.setSelection(getIndex(dayspinner, gun));
        timeEditText.setText(derssaati);
        time2EditText.setText(dersdakika);



        //DB objeleri
        dbHelper = new DatabaseHelper(EditFollowActivity.this);
        dbHelper.CreateDataBase();
        db = dbHelper.getWritableDatabase();

        // Get intent
        String userID = intent.getStringExtra("userID");
        lessonList = new ArrayList<Lesson>();

        //aynı kullanıcı var mı diye kontrol edilir.
        Cursor cursor = db.rawQuery("SELECT * FROM tblDers where " +
                " k_id= '" + userID + "'", null);

        if (cursor.moveToFirst()) {
            do {
                Lesson lesson = new Lesson();
                lesson.setDers_id(cursor.getString(cursor.getColumnIndex("d_id")));
                lesson.setDers_adi(cursor.getString(cursor.getColumnIndex("d_adi")));
                lessonList.add(lesson);
            } while (cursor.moveToNext());
        }
        cursor.close();

        String lessons[] = new String[lessonList.size()];

        for (int i = 0; i < lessonList.size(); i++) {
            lessons[i] = lessonList.get(i).getDers_adi();
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lessons);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        lessonsSpinner.setAdapter(spinnerArrayAdapter);

        lessonsSpinner.setSelection(getIndex(lessonsSpinner, name));


        // Button
        Button entryButton = (Button) findViewById(R.id.entryButton);
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //control et todo
                if (true) {
                    updateDB();
                }
            }
        });


    }

    private void updateDB() {

        //Üye bilgileri veritabanına kaydediliyor.
        ContentValues cv = new ContentValues();

        final Intent intent = getIntent();
        String ders_id = intent.getStringExtra("ders_id");

        cv.put("gun", dayspinner.getSelectedItem().toString());
        cv.put("derssaati", timeEditText.getText().toString());
        // todo dakika gelmiyor
        cv.put("dersdakika", time2EditText.getText().toString());
        cv.put("ders_adi", lessonsSpinner.getSelectedItem().toString());

        db.update("tblDersProgrami", cv, "ders_id=" + ders_id, null);

        onBackPressed();
    }

    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(myString)) {
                index = i;
            }
        }
        return index;
    }
}
