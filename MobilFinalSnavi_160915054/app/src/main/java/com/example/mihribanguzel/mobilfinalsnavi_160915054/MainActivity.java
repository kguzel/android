package com.example.mihribanguzel.mobilfinalsnavi_160915054;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText name;
    private EditText surname;
    private Button save;
    private TextView result;
    private CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        save = (Button) findViewById(R.id.save);
        result = (TextView) findViewById(R.id.result);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);

        controlRememberME();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkNameandSurname();

                Intent intent = new Intent (MainActivity.this, Hesapla.class);
                String sName = name.getText().toString();
                intent.putExtra("myname", sName);
                String surName = surname.getText().toString();
                intent.putExtra("mysurname", surName);

                startActivity(intent);

            }
        });
    }

    private void checkNameandSurname() {

        if ((name.getText().toString()).equals("Mihriban") && (surname.getText().toString()).equals("Güzel")) {

            result.setText("İşlem başarılı. Giriş yapıldı.");

            if (rememberMe.isChecked()) {

                saveToPreference();

            } else {
                clearPreference();
            }

        } else {

            result.setText("İşlem başarılı DEĞİL. Giriş yapılMADI.");

        }
    }

    private void saveToPreference() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("rememberME", true);
        editor.putString("name", name.getText().toString());
        editor.putString("surname", surname.getText().toString());
        editor.commit();
    }


    private void controlRememberME() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean isChecked = preferences.getBoolean("rememberME", false);

        if (isChecked) {

            String adi = preferences.getString("name", "");
            String soyadi = preferences.getString("surname", "");

            name.setText(adi);
            surname.setText(soyadi);
            rememberMe.setChecked(true);

        }

    }

    private void clearPreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("rememberME", false);
        editor.commit();

    }
}
