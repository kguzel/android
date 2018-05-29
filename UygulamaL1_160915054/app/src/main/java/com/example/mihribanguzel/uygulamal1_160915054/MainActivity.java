package com.example.mihribanguzel.uygulamal1_160915054;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // Kullanıcı arayüz objelerini global olarak tanımlayalım.
    private EditText pointEditText;
    private Button calculateButton;
    private TextView resultTextViewIf;
    private TextView resultTextViewSwitch;
    private Button buttonWhile;
    private TextView resultTextViewWhile;
    private Button buttonArray;
    private TextView resultTextViewArray;

    // integer array
    int[] myIntArray = {12,223,32};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Objeleri xmlden alalım
        pointEditText = (EditText) findViewById(R.id.pointEditText);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        resultTextViewIf = (TextView) findViewById(R.id.resultTextViewIf);
        resultTextViewSwitch = (TextView) findViewById(R.id.resultTextViewSwitch);
        buttonWhile = (Button) findViewById(R.id.buttonWhile);
        resultTextViewWhile = (TextView) findViewById(R.id.resultTextViewWhile) ;
        buttonArray = (Button) findViewById(R.id.buttonArray);
        resultTextViewArray = (TextView) findViewById(R.id.resultTextViewArray) ;

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                writeToTextView();

            }
        });

        buttonWhile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                writeToWhileResultToText();

            }
        });


        buttonArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                writeToArrayResultToText();

            }
        });

    }

    private void writeToTextView() {

        // İf ile sonucu ekrana yazirma
        if (Integer.parseInt(pointEditText.getText().toString()) >= 65) {
            resultTextViewIf.setText("if ile sonuç : Geçti");
        } else {
            resultTextViewIf.setText("if ile sonuç : Kaldı");
        }


        int points = Integer.parseInt(pointEditText.getText().toString());
        int signum = Long.signum((long) points - 65);

        // Switch ile sonucu ekrana yazirma
        switch (signum) {
            case -1:
                resultTextViewSwitch.setText("Switch ile sonuç : Kaldı");
                break;
            case 1:
                resultTextViewSwitch.setText("Switch ile sonuç : Geçti");
                break;
            case 0:
                resultTextViewSwitch.setText("Switch ile sonuç : Geçti");
                break;
        }

    }

    // 1 den 5 e kadar sayılari string e toplayarak ekrana yazdıralım
    private void writeToWhileResultToText() {
        int i=1;
        String result="";

        while (i<6){
            result=result + i+". sayi="+i+"\n";
            i++;
        }
        resultTextViewWhile.setText(result);
    }


    // Dizideki eleman sayısı kadar sayı yazdırma
    private void writeToArrayResultToText() {

        String result="";

        for (int i = 1; i < myIntArray.length+1; i++) {
            result=result + "Dizide bulunan eleman sayisı= "+i+"\n";
        }
        resultTextViewArray.setText(result);
    }

}
