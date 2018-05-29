package com.example.ogrenci.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    // Kullanıcı arayüz objelerini global olarak tanımlayalım.
    private EditText nameEditText;
    private EditText passEditText;
    private Button saveButton;
    private TextView resultTextView;
    private CheckBox rememberCheckBox;
    private RadioGroup rgUserType;
    private Spinner spinner;
    private DatePicker datePicker;

    //Seçilen il ismini tutan değişken
    String seletedCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // ekrana bu xml layoutu setler.
        setContentView(R.layout.activity_main);

        // Objeleri xmlden alalım
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
        saveButton = (Button) findViewById(R.id.saveButton);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);
        rgUserType = (RadioGroup) findViewById(R.id.rgUserType);
        spinner = (Spinner) findViewById(R.id.spinner);
        datePicker = (DatePicker) findViewById(R.id.datePicker);


        // Spiiner item secilmesini dinleyelim
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                seletedCity = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        // Button tıklamayı dinleyelim
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View clickedView) {

                if (contolform() == true) {
                    writeToTextView();
                }

            }
        });
    }

    //Sonucu text viewe yazmak için fonksiyon kullanalım
    private void writeToTextView() {

        String sonuc = " İsim: ";

        sonuc += nameEditText.getText().toString();

        sonuc += "\n Şifre: " + passEditText.getText().toString();

        if (rememberCheckBox.isChecked()) {
            sonuc += "\n Beni hatırla seçildi.";
        } else {
            sonuc += "\n Beni hatırla seçilmedi.";
        }


        int selectedId = rgUserType.getCheckedRadioButtonId();

        RadioButton radioButtonSecimXX;

        radioButtonSecimXX = (RadioButton) findViewById(selectedId);

        sonuc += "\n Kullanıcı Tipi: " + radioButtonSecimXX.getText().toString();

        sonuc += "\n Secilen il: " + seletedCity;

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        sonuc = sonuc + "\n Secilen tarih: " + day + "." + month + "." + year;

        resultTextView.setText(sonuc);

    }

    //formun istenilen sekilde doldurulmasını kontrol edelim.
    private boolean contolform() {

        if (nameEditText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "İsim boş olamaz", Toast.LENGTH_LONG).show();
            return false;
        }

        if (passEditText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Şifre boş olamaz", Toast.LENGTH_LONG).show();
            return false;
        }

        if (rgUserType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Kullanıcı tipini seçiniz", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;


    }
}
