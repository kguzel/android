package com.example.mihribanguzel.mobilfinalsnavi_160915054;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Hesapla extends AppCompatActivity {
private EditText number;
private Button calculate;
private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesapla);

        number = (EditText) findViewById(R.id.number);
        calculate = (Button) findViewById(R.id.calculate);
        result = (TextView) findViewById(R.id.result);

        final Intent intent = getIntent();
        String adi = intent.getStringExtra("myname");
        String soyadi = intent.getStringExtra("mysurname");


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sayi= Integer.parseInt(number.getText().toString());

                result.setText("Sayının karesi= "+ karesi(sayi));
            }
        });
    }

    private int karesi(int sayi){

      return sayi*sayi;
    }
}
