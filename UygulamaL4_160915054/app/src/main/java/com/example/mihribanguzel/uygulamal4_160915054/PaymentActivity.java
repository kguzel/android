//PaymentActivity.java

package com.example.mihribanguzel.uygulamal4_160915054;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PaymentActivity extends AppCompatActivity {

    private EditText krediKartiEditText;
    private EditText krediKartiCVCEditText;
    private Button paymentButton;
    private TextView totalTextView;
    private TextView dateTextView;
    Date date2=new Date();
    DateFormat dm = new SimpleDateFormat("dd MM yyyy");
    String date =dm.format(date2);


    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private int limit = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        krediKartiEditText = (EditText) findViewById(R.id.krediKartiEditText);
        krediKartiCVCEditText = (EditText) findViewById(R.id.krediKartiCVCEditText);
        paymentButton = (Button) findViewById(R.id.paymentButton);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);


        final Intent intent = getIntent();
        totalTextView.setText("Toplam Tutar: " + intent.getIntExtra("totalAmount", 0) + " tl");

        // Dp helper create
        dbHelper = new DatabaseHelper(this);
        dbHelper.CreateDataBase();
        db = dbHelper.getWritableDatabase();

        // Button tıklamayı dinleyelim
        paymentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View clickedView) {

                final Intent intent = getIntent();
                if (limit < intent.getIntExtra("totalAmount", 0)) {
                    Toast.makeText(getApplicationContext(), "Yeterli bakiyeniz bulunmamaktadir", Toast.LENGTH_LONG).show();
                    return;
                }

                if (controlForm() == true) {
                    insertToDB();
                    readFromDB();
                }

            }
        });

    }


    private void insertToDB() {

        final Intent intent = getIntent();
        int amount = intent.getIntExtra("totalAmount", 0);
        String detay = "'" + intent.getStringExtra("result_content") + "'";

        db.execSQL("insert into Siparis (SiparisDetayi,Fiyati,KrediKartiNo,CVC,Tarih)" +
                "values(" + detay + "," + amount + "," + krediKartiEditText.getText().toString() + "," + krediKartiCVCEditText.getText().toString() + ", datetime())");

    }


    //formun istenilen sekilde doldurulmasını kontrol edelim.
    private boolean controlForm() {


        return true;
    }


    //db okuma
    private void readFromDB() {

        Cursor c = db.rawQuery("SELECT SiparisDetayi,Fiyati,KrediKartiNo,CVC,Tarih FROM Siparis order by SiparisID desc ", null);
       /*
        if (c.moveToFirst()){
            do {
                // Passing values
                String SiparisDetayi = c.getString(0);
                String Fiyati = c.getString(1);
                int KrediKartiNo = c.getInt(2);
                // Do something Here with values
            } while(c.moveToNext());
        }
        */


        String dbRowText = "DB ye eklendi. ";

        if (c.moveToFirst()) {

            dbRowText = dbRowText + " Siparis Detayi=" + c.getString(0);
            dbRowText = dbRowText + " Fiyati= " + c.getString(1);
            dbRowText = dbRowText + " Kart No= " + c.getInt(2);
            dbRowText = dbRowText + " CVC No= " + c.getInt(3);
            dbRowText = dbRowText + " Tarih= " + c.getString(4);


            Toast.makeText(getApplicationContext(), dbRowText, Toast.LENGTH_LONG).show();
        }

        c.close();
        //db.close();
    }


}



