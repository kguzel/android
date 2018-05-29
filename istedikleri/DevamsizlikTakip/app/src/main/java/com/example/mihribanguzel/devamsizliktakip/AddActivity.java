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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private EditText lessonEditText;
    private Button entryButton;

    private EditText permissionEditText;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        lessonEditText = (EditText) findViewById(R.id.lessonEditText);
        permissionEditText = (EditText) findViewById(R.id.permissionEditText);
        entryButton = (Button) findViewById(R.id.entryButton);


        //DB objeleri
        dbHelper = new DatabaseHelper(this);
        dbHelper.CreateDataBase();
        db = dbHelper.getWritableDatabase();


        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");
                //aynı kullanıcı var mı diye kontrol edilir.
                Cursor cursor = db.rawQuery("SELECT * FROM tblDers where " +
                        " d_adi= '" + lessonEditText.getText().toString() + "' and" +
                        " k_id= '" + userID + "'", null);

                if (cursor.moveToFirst()) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddActivity.this);
                    alertDialog.setTitle("Uyarı");
                    alertDialog.setMessage("Aynı isimli Ders Girişi Yapılamaz.");
                    alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // onBackPressed();
                        }
                    });
                    alertDialog.show();
                }

                // db de veri yoksa
                else if (!cursor.moveToFirst()) {
                    if (contolform()) {
                        addActivityToDB();
                    }
                }

                cursor.close();

            }


        });
    }
    private void addActivityToDB() {

        //Üye bilgileri veritabanına kaydediliyor.
        ContentValues cv = new ContentValues();

        final Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        cv.put("k_id", userID);
        cv.put("d_adi", lessonEditText.getText().toString());
        cv.put("Devamsiz_Hak", permissionEditText.getText().toString());

        if (db.insert("tblDers", null, cv) > 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddActivity.this);
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
    //formun istenilen sekilde doldurulmasını kontrol edelim.
    private boolean contolform() {

        if (lessonEditText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Ders Adı bilgisi boş olamaz", Toast.LENGTH_LONG).show();
            return false;
        }

        if (permissionEditText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Devamsızlık bilgisi boş olamaz", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    }

