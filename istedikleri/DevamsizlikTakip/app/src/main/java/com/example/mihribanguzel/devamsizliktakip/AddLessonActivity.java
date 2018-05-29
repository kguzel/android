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


public class AddLessonActivity extends AppCompatActivity {

    private Button entryButton;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        entryButton = (Button) findViewById(R.id.entryButton);
        editButton = (Button) findViewById(R.id.editButton);

        //gönderilen userID bilgisini alıyoruz.
        final Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), EditLessonsListActivity.class);
                i.putExtra("userID", userID);
                startActivity(i);
            }
        });

        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), AddActivity.class);
                i.putExtra("userID", userID);
                startActivity(i);
            }
        });
    }
}


