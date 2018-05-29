//ResultActivity.java

package com.example.mihribanguzel.uygulamal4_160915054;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    private TextView onayTextView;
    private ImageView pizzaImageView;
    private TextView contextTextView;
    private Button goOnButton;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        onayTextView = (TextView) findViewById(R.id.onayTextView);
        pizzaImageView = (ImageView) findViewById(R.id.pizzaImageView);
        contextTextView = (TextView) findViewById(R.id.contextTextView);
        goOnButton = (Button) findViewById(R.id.goOnButton);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        // Gonderilen extralar okundu
        final Intent intent = getIntent();
        String resultContent = intent.getStringExtra("result_content");
        int pizzaImageID = intent.getIntExtra("pizza_image_id", 0);

        contextTextView.setText(resultContent);
        pizzaImageView.setImageResource(pizzaImageID);


        // click button , bakiye kontrol
        goOnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View clickedView) {

                openNewActivity();

            }
        });

        totalTextView.setText("Tutar: " + intent.getIntExtra("totalAmount", 0) + " tl");


    }

    //yeni bir intent acalim
    private void openNewActivity() {
        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);

        intent.putExtra("totalAmount", getIntent().getIntExtra("totalAmount", 0));

        intent.putExtra("result_content", getIntent().getStringExtra("result_content"));

        startActivity(intent);
    }

}
