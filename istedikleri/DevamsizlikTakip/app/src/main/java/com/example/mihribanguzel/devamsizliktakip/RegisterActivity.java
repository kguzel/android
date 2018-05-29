package com.example.mihribanguzel.devamsizliktakip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends Activity{

    Button kayit_ol;
    EditText kullaniciadi,sifre,gizlisorucevap,tekrarsifre;
    Spinner gizlisoru;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    Boolean giris= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        kayit_ol = (Button) findViewById(R.id.entryButton);
        kullaniciadi= (EditText)findViewById(R.id.nameEditText);
        gizlisorucevap = (EditText)findViewById(R.id.answerEditText);
        gizlisoru = (Spinner) findViewById(R.id.questionspinner);
        sifre = (EditText)findViewById(R.id.passEditText);
        tekrarsifre = (EditText)findViewById(R.id.passAgainEditText);

        dbHelper = new DatabaseHelper(RegisterActivity.this);
        dbHelper.CreateDataBase();
        db=dbHelper.getWritableDatabase();

        kayit_ol.setOnClickListener(new View.OnClickListener() {//Kayıt ol butonu tıklanınca

            @Override
            public void onClick(View v) {

                if (!kullaniciadi.getText().toString().equals("")&& !sifre.getText().toString().equals("")
                            && !tekrarsifre.getText().toString().equals("")&& !gizlisorucevap.getText().toString().equals(""))
                    {
                        if(sifre.getText().toString().equals(tekrarsifre.getText().toString())) {
                            //aynı kullanıcı var mı diye kontrol edilir.
                            Cursor cek = db.rawQuery("SELECT * FROM tblUye where k_adi='"+kullaniciadi.getText().toString()+"'", null);
                            if (cek.moveToFirst()) {
                                giris=false;
                            }
                            cek.close();
                            //Aynı kullanıcı adında üye yok ise kayıt oluşturulur.
                            if(giris) {
                                //Üye bilgileri veritabanına kaydediliyor.
                                ContentValues cv = new ContentValues();
                                cv.put("k_adi", kullaniciadi.getText().toString());
                                cv.put("sifre", sifre.getText().toString());
                                cv.put("g_soru", gizlisoru.getSelectedItem().toString());
                                cv.put("g_cvp", gizlisorucevap.getText().toString());

                                if (db.insert("tblUye", null, cv) > 0) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
                                    alertDialog.setTitle("Tebrikler");
                                    alertDialog.setMessage("Kayıt işlemi gerçekleşmiştir.");
                                    alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    });
                                    alertDialog.show();
                                }
                            }else
                            {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
                                alertDialog.setTitle("Uyarı");
                                alertDialog.setMessage("Aynı kullanıcı adında üye bulunmaktadır !");
                                alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int which) {

                                    }
                                });
                                alertDialog.show();
                            }
                        }
                        else
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
                            alertDialog.setTitle("Uyarı");
                            alertDialog.setMessage("Girilen şifreler uyuşmamaktadır. Lütfen kontrol ediniz !");
                            alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int which) {

                                }
                            });
                            alertDialog.show();
                        }
                    }
                else
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
                        alertDialog.setTitle("Uyarı");
                        alertDialog.setMessage("Lütfen bilgileri eksiksiz doldurunuz !");
                        alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {

                            }
                        });
                        alertDialog.show();
                    }
                }
        });
    }
}