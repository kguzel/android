package com.example.mihribanguzel.devamsizliktakip;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CreateActivity extends AppCompatActivity {

    private Spinner dayspinner;
    private Spinner lessonsSpinner;
    private TimePicker timeTimePicker;
    //private EditText time2EditText;
    private EditText lessonEditText;

    private Button entryButton;
    private List<Lesson> lessonList;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String hourOfDayS, minuteS;
    static Button timeButton;

    //sayfa açılırken gerçekleşecek olaylar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        dayspinner = (Spinner) findViewById(R.id.dayspinner);
        //time2EditText = (EditText) findViewById(R.id.time2EditText) ;
        lessonEditText = (EditText) findViewById(R.id.lessonEditText);

        lessonsSpinner = (Spinner) findViewById(R.id.lessonsSpinner);


//DB objeleri
        dbHelper = new DatabaseHelper(CreateActivity.this);
        dbHelper.CreateDataBase();
        db = dbHelper.getWritableDatabase();

//gönderilen userID bilgisini alıyoruz.
        final Intent intent = getIntent();
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

// veritabanına eklenen ders isimleri listeye atılıyor.
        String lessons[] = new String[lessonList.size()];

        for (int i = 0; i < lessonList.size(); i++) {
            lessons[i] = lessonList.get(i).getDers_adi();
        }
// listeye atılan ders isimleri spinner a çağrılıyor.
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lessons);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        lessonsSpinner.setAdapter(spinnerArrayAdapter);

// butona tıklandığında
        Button entryButton = (Button) findViewById(R.id.entryButton);
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//verilerin girilip girilmediğini kontrol et ve database e ekle
                if (contolform()) {
                    addActivityToDB();
                }
            }
        });

// saat bilgisini almak için bir buton tanımladık.
        timeButton = (Button) findViewById(R.id.timeButton);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// ?? //todo
                TimePicker mTimePicker = new TimePicker();
                mTimePicker.show(getFragmentManager(), "Saati Seç");
            }
        });

    }

    //Üye bilgileri veritabanına kaydediliyor.
    private void addActivityToDB() {

        final Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        Cursor cursor = db.rawQuery("SELECT * FROM tblDers where " +
                " d_adi= '" + lessonsSpinner.getSelectedItem().toString() + "' and" +
                " k_id= '" + userID + "'", null);

        if (cursor.moveToFirst()) {

            Lesson lesson = new Lesson();
            int dh=cursor.getInt(cursor.getColumnIndex("Devamsiz_Hak"));

            ContentValues cv = new ContentValues();

            cv.put("k_id", userID);
            cv.put("gun", dayspinner.getSelectedItem().toString());
            cv.put("derssaati", hourOfDayS);
            cv.put("dersdakika", minuteS);
            cv.put("ders_adi", lessonsSpinner.getSelectedItem().toString());

            float i=(15f*dh)/100;

            if((float)((15*dh)/100) - i !=0){
                i=i+1;
            }

            cv.put("Devamsiz_H", i);

            if (db.insert("tblDersProgrami", null, cv) > 0) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateActivity.this);
                alertDialog.setTitle("Bilgi");
                alertDialog.setMessage("Ders ekleme işlemi gerçekleştirildi.");
                alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                alertDialog.show();
            }

        }

    }


    //formun istenilen sekilde doldurulmasını kontrol edelim.
    private boolean contolform() {

        if (timeButton.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Saat bilgisi boş olamaz", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    //saat bilgisini alert dialog ile alalım
    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        //?? //todo
        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            // displayCurrentTime.setText("Selected Time: " + String.valueOf(hourOfDay) + " : " + String.valueOf(minute));

            minuteS = String.valueOf(minute);
            hourOfDayS = String.valueOf(hourOfDay);

            timeButton.setText("" + String.valueOf(hourOfDay) + " : " + String.valueOf(minute));

        }
    }

}
