
package com.androdocs.weatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androdocs.httprequest.HttpRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button forecastButton;
        String CITY = "serres,gr";
        String API = "d830ac00c13e0f678ca1c3a9112a62e8";


        String latMain, lonMain;


        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView addressTxt, updated_atTxt, statusTxt, tempTxt,  windTxt, pressureTxt, humidityTxt;

        Map<String, Object> weatherhm = new HashMap<>();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            forecastButton = findViewById(R.id.forecastButton);
            forecastButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openForecastActivity();
                }
            });
            addressTxt = findViewById(R.id.address);
            updated_atTxt = findViewById(R.id.updated_at);
            statusTxt = findViewById(R.id.status);
            tempTxt = findViewById(R.id.temp);
            windTxt = findViewById(R.id.wind);
            pressureTxt = findViewById(R.id.pressure);
            humidityTxt = findViewById(R.id.humidity);

            latMain=getIntent().getExtras().getString("latValue");
            lonMain=getIntent().getExtras().getString("lonValue");

            new weatherTask().execute();
        }

        class weatherTask extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                /* Showing the ProgressBar, Making the main design GONE */
                findViewById(R.id.loader).setVisibility(View.VISIBLE);
                findViewById(R.id.mainContainer).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.GONE);
            }

            protected String doInBackground(String... args) {
              // String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&lang=el&units=metric&appid=" + API);
              String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=" + latMain + "&lon=" + lonMain + "&lang=el&units=metric&appid=" + API);
              //  String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=12&lon=12");
                return response;
            }

            @Override
            protected void onPostExecute(String result) {


                try {
                    JSONObject jsonObj = new JSONObject(result);
                    JSONObject main = jsonObj.getJSONObject("main");
                    JSONObject sys = jsonObj.getJSONObject("sys");
                    JSONObject wind = jsonObj.getJSONObject("wind");
                    JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                    Long updatedAt = jsonObj.getLong("dt");
                    String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                    String temp = main.getString("temp") + "°C";
                    String pressure = main.getString("pressure") + " hpa";
                    String humidity = main.getString("humidity") + "%";

                    String windSpeed = wind.getString("speed");
                    String weatherDescription = weather.getString("description");

                    String address = jsonObj.getString("name") + ", " + sys.getString("country");


                    /* Populating extracted data into our views */
                    addressTxt.setText(address);
                    updated_atTxt.setText(updatedAtText);
                    statusTxt.setText(weatherDescription.toUpperCase());
                    tempTxt.setText(temp);
                    windTxt.setText(windSpeed);
                    pressureTxt.setText(pressure);
                    humidityTxt.setText(humidity);

                    weatherhm.put("update_time", updatedAtText);
                    weatherhm.put("weather_description", weatherDescription);
                    weatherhm.put("temperature", temp);

                    db.collection("weatherdb")
                            .add(weatherhm)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });



                    /* Views populated, Hiding the loader, Showing the main design */
                    findViewById(R.id.loader).setVisibility(View.GONE);
                    findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    findViewById(R.id.loader).setVisibility(View.GONE);
                    findViewById(R.id.errorText).setVisibility(View.VISIBLE);
                }

            }
        }




        public void openForecastActivity(){
            Intent intent = new Intent(this, ForecastActivity.class);
            startActivity(intent);
        }
    }