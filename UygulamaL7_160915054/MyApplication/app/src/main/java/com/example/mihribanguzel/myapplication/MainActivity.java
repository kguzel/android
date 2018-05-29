package com.example.mihribanguzel.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Button button1;
    private TextView textView1;
    private EditText NameEditText;
    private EditText PassEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        textView1 = (TextView) findViewById(R.id.textView1);
        NameEditText =(EditText) findViewById(R.id.NameEditText);
        PassEditText =(EditText) findViewById(R.id.PassEditText);





        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Kaydet();




                SharedPreferences settings = getSharedPreferences("rmytukuy", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();



                editor.putString("adi","mih");
                editor.commit();








                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                String name = preferences.getString("adi", "");




            }
        });



    }
    private void Kaydet() {

        textView1.setText("İsim: "+ NameEditText.getText().toString() + "   Şifre: " + PassEditText.getText().toString());

    }
}
