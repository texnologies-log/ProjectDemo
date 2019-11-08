
package com.androdocs.weatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

    public class MainActivity extends AppCompatActivity {
        private Button forecastButton;
        String CITY = "serres,gr";
        String API = "d830ac00c13e0f678ca1c3a9112a62e8";

        TextView addressTxt, updated_atTxt, statusTxt, tempTxt,  windTxt, pressureTxt, humidityTxt;

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
                String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
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