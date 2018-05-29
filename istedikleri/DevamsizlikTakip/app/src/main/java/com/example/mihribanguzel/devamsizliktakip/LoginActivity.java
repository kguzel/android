package com.example.mihribanguzel.devamsizliktakip;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {


    private EditText nameEditText;
    private EditText passEditText;
    private Button enterButton, registerButton;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private TextView hatirlatmaTextView;
    private String userID;
    private CheckBox rememberCheckBox;
    MySharedPreference mySharedPreference;
    Context context = this;
    String name_string, sifre_string;

//sayfa açılırken gerçekleşecek olaylar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);




// Objeleri xmlden alalım
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        passEditText = (EditText) findViewById(R.id.passEditText);
        rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);
        enterButton = (Button) findViewById(R.id.enterButton);
        hatirlatmaTextView = (TextView) findViewById(R.id.hatirlatmaTextView);
        rememberCheckBox = (CheckBox) findViewById(R.id.rememberCheckBox);
        registerButton = (Button) findViewById(R.id.registerButton);

//DB objeleri
        dbHelper = new DatabaseHelper(LoginActivity.this);
        dbHelper.CreateDataBase();
        db = dbHelper.getWritableDatabase();

//telefonun diskine verileri anahtar kelime değerleri(key) ile yazıp value değerlerini okumak için ortam oluşturuyoruz.
        mySharedPreference = new MySharedPreference();

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contolform() == true) {
                    if (checkUserFromDB()) {
                        openDialog();
                    } else {
                        //DB de boyle bir kullanici adi veya sifre yoktur
                        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
                        alertDialog.setTitle("Uyarı");
                        alertDialog.setMessage("Lütfen bilgilerinizi kontrol ediniz!");
                        alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

//şifremi unuttum textviewine tıklandığında ForgettenActivitiy e gider.
        hatirlatmaTextView.setOnClickListener(new View.OnClickListener() {//kayıt ekranı açılır

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgottenActivity.class);
                startActivity(i);
            }
        });


        /*
        if (contolform() == true) {
            if(checkUserFromDB()){
                openDialog();
            }else {
                //DB de boyle bir kullanici adi veya sifre yoktur
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Girilen şifreler uyuşmamaktadır. Lütfen kontrol ediniz !");
                alertDialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                    }
                });
                alertDialog.show();
            }
        }
        */

    }




    @Override
    protected void onResume() {
        super.onResume();


        if (mySharedPreference.getValueBoolean(this, "remember")) {
            nameEditText.setText(mySharedPreference.getValue(this, "username"));
            passEditText.setText("");
        } else {
            nameEditText.setText("");
            passEditText.setText("");
        }

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }




// DB den user kontrol ediyoruz
    private boolean checkUserFromDB() {

//aynı kullanıcı var mı diye kontrol edilir.
        Cursor cek = db.rawQuery("SELECT * FROM tblUye where " +
                "k_adi= '" + nameEditText.getText().toString() + "' " +
                "and sifre= '" + passEditText.getText().toString() + "'", null);

        if (!cek.moveToFirst()) {
            cek.close();
            // Boyle bir kayıt db de yok
            return false;
        }

        userID = cek.getString(cek.getColumnIndex("k_id"));

        cek.close();
        return true;
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

        return true;
    }

// Dialog ile Hoşgeldin mesajı veriyoruz
    private void openDialog() {

        final String content = ("Hoşgeldin " + nameEditText.getText().toString() + "\n ");

        AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
        builder1.setMessage(content);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Tamam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //yeni bir intent acalim
                        openNewActivity(content);

                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

//yeni bir intent acalim, extralar ekleyelim ve aktivity baslatalim
    private void openNewActivity(String content) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("name", nameEditText.getText().toString());
        intent.putExtra("userID", userID);

        startActivity(intent);

        if (rememberCheckBox.isChecked()) {
            mySharedPreference.save(context, "username", nameEditText.getText().toString());
        } else {
            mySharedPreference.save(context, "username", "");
        }

        mySharedPreference.saveBoolean(context, "remember", rememberCheckBox.isChecked());
    }

}
