package com.example.mihribanguzel.uygulamal3_160915054;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {


    private TextView onayTextView;
    private ImageView pizzaImageView;
    private TextView contextTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        onayTextView = (TextView)findViewById(R.id.onayTextView);
        pizzaImageView =(ImageView) findViewById(R.id.pizzaImageView);
        contextTextView = (TextView) findViewById(R.id.contextTextView);

        Intent intent = getIntent();
        String resultContent = intent.getStringExtra("result_content");
        int pizzaImageID = intent.getIntExtra("pizza_image_id",0);

        contextTextView.setText(resultContent);
        pizzaImageView.setImageResource(pizzaImageID);

    }

}
