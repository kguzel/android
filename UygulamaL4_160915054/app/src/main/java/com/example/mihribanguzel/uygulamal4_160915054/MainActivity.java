//MainActivity.java

package com.example.mihribanguzel.uygulamal4_160915054;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView pizzaCountTextView;
    private Spinner spinner;
    private RadioGroup rgOptionType;
    private CheckBox sucukCheckBox;
    private CheckBox mantarCheckBox;
    private CheckBox salamCheckBox;
    private CheckBox sosisCheckBox;
    private CheckBox zeytinCheckBox;
    private CheckBox karidesCheckBox;
    private CheckBox kavurmaCheckBox;
    private CheckBox kasarCheckBox;
    private CheckBox misirCheckBox;
    private TextView doughOptionTextView;
    private ToggleButton toggleButton;
    private Button biberliImageButton;
    private Button mantarliImageButton;
    private Button sebzelimageButton;
    private Button getOrderButton;

    //Seçilen pizza sayisi
    private String selectedPizzaCount;

    //Seçilen pizza adi
    private String selectedPizzaName;

    //Seçilen pizza resmin idsi
    private int selectedPizzaImageID;

    //Toplam fiyat
    private int totalAmount;

    private int EXTRA = 2;
    private int PIZZA_LITTLE_AMOUNT = 10;
    private int PIZZA_MEDIUM_AMOUNT = 15;
    private int PIZZA_LARGE_AMOUNT = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Objeleri xmlden alalım
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        pizzaCountTextView = (TextView) findViewById(R.id.pizzaCountTextView);
        spinner = (Spinner) findViewById(R.id.spinner);
        rgOptionType = (RadioGroup) findViewById(R.id.rgOptionType);

        sucukCheckBox = (CheckBox) findViewById(R.id.sucukCheckBox);
        mantarCheckBox = (CheckBox) findViewById(R.id.mantarCheckBox);
        salamCheckBox = (CheckBox) findViewById(R.id.salamCheckBox);
        sosisCheckBox = (CheckBox) findViewById(R.id.sosisCheckBox);
        misirCheckBox = (CheckBox) findViewById(R.id.misirCheckBox);
        zeytinCheckBox = (CheckBox) findViewById(R.id.zeytinCheckBox);
        karidesCheckBox = (CheckBox) findViewById(R.id.karidesCheckBox);
        kavurmaCheckBox = (CheckBox) findViewById(R.id.kavurmaCheckBox);
        kasarCheckBox = (CheckBox) findViewById(R.id.kasarCheckBox);

        doughOptionTextView = (TextView) findViewById(R.id.doughOptionTextView);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        biberliImageButton = (Button) findViewById(R.id.biberliImageButton);
        mantarliImageButton = (Button) findViewById(R.id.mantarliImageButton);
        sebzelimageButton = (Button) findViewById(R.id.sebzelimageButton);
        getOrderButton = (Button) findViewById(R.id.getOrderButton);


        // Spinner item secilmesini dinleyelim
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                selectedPizzaCount = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        // Button tıklamayı dinleyelim
        getOrderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View clickedView) {

                if (controlForm() == true) {
                    openDialog();
                }

            }
        });


        biberliImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View clickedView) {
                selectedPizzaName = biberliImageButton.getText().toString();
                selectedPizzaImageID = R.drawable.biberli_pizza;
            }
        });


        mantarliImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View clickedView) {
                selectedPizzaName = mantarliImageButton.getText().toString();
                selectedPizzaImageID = R.drawable.mantarli_pizza;
            }
        });


        sebzelimageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View clickedView) {
                selectedPizzaName = sebzelimageButton.getText().toString();
                selectedPizzaImageID = R.drawable.sebzeli_pizza;
            }
        });

    }

    // Dialog ile gosterme
    private void openDialog() {

        int selectedId = rgOptionType.getCheckedRadioButtonId();
        RadioButton radioButtonSecimXX = (RadioButton) findViewById(selectedId);
        String boy = radioButtonSecimXX.getText().toString();

        if (boy.equals("Küçük")) {
            totalAmount = totalAmount + Integer.parseInt(selectedPizzaCount) * PIZZA_LITTLE_AMOUNT;
        } else if (boy.equals("Orta")) {
            totalAmount = totalAmount + Integer.parseInt(selectedPizzaCount) * PIZZA_MEDIUM_AMOUNT;
        } else if (boy.equals("Büyük")) {
            totalAmount = totalAmount + Integer.parseInt(selectedPizzaCount) * PIZZA_LARGE_AMOUNT;
        }

        String hamurTipi = toggleButton.getText().toString();
        String pizzaTipi = selectedPizzaName;
        String malzemeler = getMalzemeler();


        final String content = ("Sipariş Detayları:\n Adet:" + selectedPizzaCount + "\n " +
                "Boy:" + boy + "\n " +
                "Hamur Tipi:" + hamurTipi + "\n " +
                "Pizza Tipi:" + pizzaTipi + "\n " +
                "Malzemeler:" + malzemeler + "\n ");

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage(content);
        builder1.setTitle("Sipariş Verilmeye Hazır");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Gonder",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //yeni bir intent acalim
                        openNewActivity(content);

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "İptal",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    //yeni bir intent acalim, extralar ekleyelim ve aktivity baslatalim
    private void openNewActivity(String content) {
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

        intent.putExtra("result_content", content);

        intent.putExtra("totalAmount", totalAmount);

        //Resmin source idsini gonderiyoruz
        intent.putExtra("pizza_image_id", selectedPizzaImageID);

        startActivity(intent);
    }

    // Secili malzemeleri ureterek geri doner
    private String getMalzemeler() {

        String malzemeler = "";

        if (sucukCheckBox.isChecked()) {
            malzemeler = "Sucuk";
            totalAmount = totalAmount + EXTRA;
        }
        if (mantarCheckBox.isChecked()) {
            malzemeler += " Mantar";
            totalAmount = totalAmount + EXTRA;
        }
        if (salamCheckBox.isChecked()) {
            malzemeler += " Salam";
            totalAmount = totalAmount + EXTRA;
        }
        if (sosisCheckBox.isChecked()) {
            malzemeler += " Sosis";
            totalAmount = totalAmount + EXTRA;
        }
        if (zeytinCheckBox.isChecked()) {
            malzemeler += " Zeytin";
            totalAmount = totalAmount + EXTRA;
        }
        if (karidesCheckBox.isChecked()) {
            malzemeler += " Karides";
            totalAmount = totalAmount + EXTRA;
        }
        if (kavurmaCheckBox.isChecked()) {
            malzemeler += " Kavurma";
            totalAmount = totalAmount + EXTRA;
        }
        if (kasarCheckBox.isChecked()) {
            malzemeler += " Kaşar";
            totalAmount = totalAmount + EXTRA;
        }
        if (misirCheckBox.isChecked()) {
            malzemeler += " Mısır";
            totalAmount = totalAmount + EXTRA;
        }
        return malzemeler;
    }


    //formun istenilen sekilde doldurulmasını kontrol edelim.
    private boolean controlForm() {


        if (rgOptionType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Pizza boyunu seçiniz", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;


    }
}
