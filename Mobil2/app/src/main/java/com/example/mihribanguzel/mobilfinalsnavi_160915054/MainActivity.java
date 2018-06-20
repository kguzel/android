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
    private CheckBox rememberME;
    String sonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText) findViewById(R.id.name);
        surname=(EditText) findViewById(R.id.surname);
        save=(Button) findViewById(R.id.save);
        result=(TextView) findViewById(R.id.result);
        rememberME=(CheckBox) findViewById(R.id.rememberMe);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean isChecked = preferences.getBoolean("rememberME", false);

        rememberME.setChecked(isChecked);

        String nameText = preferences.getString("name", "");
        String surnameText = preferences.getString("surname", "");

        name.setText(nameText);
        surname.setText(surnameText);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
                writeToTextView();
            }
        });
    }

    private void writeToTextView (){
        if ((name.getText().toString().equals("Mihriban")) &&   (surname.getText().toString().equals("Güzel"))    ) {

            result.setText("İşlem başarı ile gerçekleştirildi. Sayfaya yönendiriliyorsunuz.  " + sonuc);

            Intent intent = new Intent(getApplicationContext(), IslemYapActivity.class);

            intent.putExtra("Name", name.getText().toString());

            intent.putExtra("Surname", surname.getText().toString());

            startActivity(intent);
        }
        else
        {
            result.setText("Kullanıcı adı ya da şifreniz yalıştır.  " + sonuc);

        }
        return;
    }
    private void check(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();

        if (rememberME.isChecked()) {
            sonuc = "\n Beni hatırla seçildi.";

            editor.putBoolean("rememberME", true);

            editor.putString("name", name.getText().toString());

            editor.putString("surname",surname.getText().toString());

        } else {
            sonuc = "\n Beni hatırla seçilmedi.";

            editor.putBoolean("rememberME", false);

            editor.putString("name", "");

            editor.putString("surname","");
        }

        editor.commit();
    }

}
