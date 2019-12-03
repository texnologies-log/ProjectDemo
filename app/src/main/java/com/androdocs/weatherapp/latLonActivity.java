package com.androdocs.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class latLonActivity extends AppCompatActivity {

    String lat, lon;
    private Button confirmButton;

    EditText latInput;
    EditText lonInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lat_lon);


        latInput = findViewById(R.id.latText);
        lonInput = findViewById(R.id.lonText);

        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(latLonActivity.this,MainActivity.class);
                lat = latInput.getText().toString();
                lon = lonInput.getText().toString();
                a.putExtra("latValue",lat);
                a.putExtra("lonValue",lon);
                startActivity(a);
            }
        });

    }

}
