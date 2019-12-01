
package com.androdocs.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StefActivity extends Activity {
    private Button currentWeatherButton;
    String CITY = "serres,gr";
    String API = "d830ac00c13e0f678ca1c3a9112a62e8";

    TextView addressTxt, prognosi1Txt, statusTxt, tempTxt, prognosi2Txt, status2Txt, temp2Txt, prognosi3Txt, status3Txt, temp3Txt, prognosi4Txt, status4Txt, temp4Txt, prognosi5Txt, status5Txt, temp5Txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stefanos);

        currentWeatherButton = findViewById(R.id.currentWeatherButton);
        currentWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        addressTxt = findViewById(R.id.address);
        prognosi1Txt = findViewById(R.id.prognosi1);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
        prognosi2Txt = findViewById(R.id.prognosi2);
        status2Txt = findViewById(R.id.status2);
        temp2Txt = findViewById(R.id.temp2);
        prognosi3Txt = findViewById(R.id.prognosi3);
        status3Txt = findViewById(R.id.status3);
        temp3Txt = findViewById(R.id.temp3);
        prognosi4Txt = findViewById(R.id.prognosi4);
        status4Txt = findViewById(R.id.status4);
        temp4Txt = findViewById(R.id.temp4);
        prognosi5Txt = findViewById(R.id.prognosi5);
        status5Txt = findViewById(R.id.status5);
        temp5Txt = findViewById(R.id.temp5);


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
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/forecast?q=" + CITY + "&lang=el&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject city = jsonObj.getJSONObject("city");

                JSONObject list = jsonObj.getJSONArray("list").getJSONObject(7);
                JSONObject main = list.getJSONObject("main");
                JSONObject weather = list.getJSONArray("weather").getJSONObject(0);


                JSONObject list2 = jsonObj.getJSONArray("list").getJSONObject(15);
                JSONObject main2 = list2.getJSONObject("main");
                JSONObject weather2 = list2.getJSONArray("weather").getJSONObject(0);

                JSONObject list3 = jsonObj.getJSONArray("list").getJSONObject(23);
                JSONObject main3 = list3.getJSONObject("main");
                JSONObject weather3 = list3.getJSONArray("weather").getJSONObject(0);

                JSONObject list4 = jsonObj.getJSONArray("list").getJSONObject(31);
                JSONObject main4 = list4.getJSONObject("main");
                JSONObject weather4 = list4.getJSONArray("weather").getJSONObject(0);

                JSONObject list5 = jsonObj.getJSONArray("list").getJSONObject(39);
                JSONObject main5 = list5.getJSONObject("main");
                JSONObject weather5 = list5.getJSONArray("weather").getJSONObject(0);


                Long Prognosi1 = list.getLong("dt");
                String prognosi1 = "Πρόβλεψη για: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(Prognosi1 * 1000));
                String temp = main.getString("temp") + "°C";
                String weatherDescription = weather.getString("description");

                Long Prognosi2 = list2.getLong("dt");
                String prognosi2 = "Πρόβλεψη για: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(Prognosi2 * 1000));
                String temp2 = main2.getString("temp") + "°C";
                String weatherDescription2 = weather2.getString("description");

                Long Prognosi3 = list3.getLong("dt");
                String prognosi3 = "Πρόβλεψη για: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(Prognosi3 * 1000));
                String temp3 = main3.getString("temp") + "°C";
                String weatherDescription3 = weather3.getString("description");

                Long Prognosi4 = list4.getLong("dt");
                String prognosi4 = "Πρόβλεψη για: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(Prognosi4 * 1000));
                String temp4 = main4.getString("temp") + "°C";
                String weatherDescription4 = weather4.getString("description");

                Long Prognosi5 = list5.getLong("dt");
                String prognosi5 = "Πρόβλεψη για: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(Prognosi5 * 1000));
                String temp5 = main5.getString("temp") + "°C";
                String weatherDescription5 = weather5.getString("description");






                String address = city.getString("name") + "," + city.getString("country");
















                /* Populating extracted data into our views */

                statusTxt.setText(weatherDescription.toUpperCase());
                prognosi1Txt.setText(prognosi1);
                tempTxt.setText(temp);

                prognosi2Txt.setText(prognosi2);
                temp2Txt.setText(temp2);
                status2Txt.setText(weatherDescription2.toUpperCase());

                prognosi3Txt.setText(prognosi3);
                temp3Txt.setText(temp3);
                status3Txt.setText(weatherDescription3.toUpperCase());

                prognosi4Txt.setText(prognosi4);
                temp4Txt.setText(temp4);
                status4Txt.setText(weatherDescription4.toUpperCase());

                prognosi5Txt.setText(prognosi5);
                temp5Txt.setText(temp5);
                status5Txt.setText(weatherDescription5.toUpperCase());

                addressTxt.setText(address);


                /* Views populated, Hiding the loader, Showing the main design */
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }

        }
    }

            public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
            }
}
