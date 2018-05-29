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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditLessonActivity extends AppCompatActivity {


    private EditText permissionEditText,lessonEditText;
    private Button entryButton;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<Lesson> lessonList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);

        // Gonderilen extralar okundu
        final Intent intent = getIntent();
        String name = intent.getStringExtra("ders_adi");
        String ders_id = intent.getStringExtra("ders_id");
        int devamsiz_Hak = intent.getIntExtra("devamsiz_Hak",0);



        lessonEditText=(EditText)findViewById(R.id.lessonEditText);
        permissionEditText=(EditText)findViewById(R.id.permissionEditText);

        lessonEditText.setText(name);
        permissionEditText.setText(""+devamsiz_Hak);

        //DB objeleri
        dbHelper = new DatabaseHelper(EditLessonActivity.this);
        dbHelper.CreateDataBase();
        db=dbHelper.getWritableDatabase();


        // Button
        Button entryButton = (Button) findViewById(R.id.entryButton);
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contolform()) {
                    updateDB();
                }
            }
        });

    }

    private void updateDB(){

        //Üye bilgileri veritabanına kaydediliyor.
        ContentValues cv = new ContentValues();

        final Intent intent = getIntent();

        String d_id = intent.getStringExtra("ders_id");

        cv.put("d_adi", lessonEditText.getText().toString());
        cv.put("Devamsiz_Hak", permissionEditText.getText().toString());

        db.update("tblDers", cv, "d_id="+d_id, null);

        onBackPressed();
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
