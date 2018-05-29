package com.example.mihribanguzel.devamsizliktakip;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ForgottenActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Spinner questionspinner;
    private EditText answerEditText;
    private Button showButton;
    private TextView answerTextView;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten);

        // TODO forget db den al gel bu paremetreler ile şifreyi
        showButton = (Button) findViewById(R.id.showButton);
        nameEditText =(EditText) findViewById(R.id.nameEditText);
        questionspinner = (Spinner) findViewById(R.id.questionspinner);
        answerEditText =(EditText) findViewById(R.id.answerEditText);
        answerTextView = (TextView) findViewById(R.id.answerTextView);

        //DB objeleri
        dbHelper = new DatabaseHelper(ForgottenActivity.this);
        dbHelper.CreateDataBase();
        db=dbHelper.getWritableDatabase();




        // Button
        Button entryButton = (Button) findViewById(R.id.showButton);
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //aynı kullanıcı var mı diye kontrol edilir.
                Cursor cursor = db.rawQuery("SELECT * FROM tblUye where " +
                                " k_adi= '"+nameEditText.getText().toString()+"' and" +
                                " g_soru= '"+questionspinner.getSelectedItem().toString()+"' and"+
                                " g_cvp= '"+answerEditText.getText().toString()+"'"

                        , null);


                if (cursor.moveToFirst()){

                    do{

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ForgottenActivity.this);
                        alertDialog.setTitle("Şifreniz:");
                        alertDialog.setMessage(cursor.getString(cursor.getColumnIndex("sifre")));
                        alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        });
                        alertDialog.show();

                    }

                    while(cursor.moveToNext());
                }

                // db de veri yoksa
                if(!cursor.moveToFirst()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ForgottenActivity.this);
                    alertDialog.setTitle("Uyarı:");
                    alertDialog.setMessage("Veri tabanında böyle bir kullanıcı yoktur. Bilgileri kontrol ediniz.");
                    alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    alertDialog.show();
                }


                cursor.close();

            }

        });







    }
}
