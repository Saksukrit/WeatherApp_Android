package com.kyo.hw5_5630213025;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.*;
import java.text.*;

public class MainActivity extends Activity {


    private static String TAG_LOCAL = "name";
    private static String TAG_SYS = "sys";
    private static String TAG_CONNTRY = "country";

    private static String TAG_MAIN = "main";
    private static String TAG_TEMP = "temp";
    private static String TAG_HUMIDITY = "humidity";

    private static String TAG_WIND = "wind";
    private static String TAG_SPEED = "speed";
    private static String TAG_DEG = "deg";


    ProgressDialog dialog;
    ArrayList<Weather> weathers;

    TextView tvTemp, tvLocal, tvDate, tvWind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializer();

        new ParseJSONTask().execute();

    }

    private void initializer() {
        dialog = new ProgressDialog(this);
        weathers = new ArrayList<Weather>();

        tvLocal = (TextView) findViewById(R.id.tvLocal);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvWind = (TextView) findViewById(R.id.tvWind);
    }

    private void updateUi() {
        String local = "";
        String date = "";
        String temp = "";
        String wind = "";
        for (Weather aWeather : weathers) {
            local += aWeather.getLocal();
            date += aWeather.getDate();
            temp += aWeather.toString();
            wind += aWeather.getWind();
        }
        tvLocal.setText(local);
        tvDate.setText(date);
        tvTemp.setText(temp);
        tvWind.setText(wind);
    }


    private class ParseJSONTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            updateUi();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WebServiceHandler webServiceHandler = new WebServiceHandler();

            String jsonStr = webServiceHandler.getJSONData("http://api.openweathermap.org/data/2.5/weather?q=Phuket&appid=2de143494c0b295cca9337e1e96b00e0");

            try {
                Weather aWeather = new Weather();

                JSONObject jsonObject = new JSONObject(jsonStr);

                aWeather.local = jsonObject.getString(TAG_LOCAL);

                JSONObject country = jsonObject.getJSONObject(TAG_SYS);
                aWeather.country = country.getString(TAG_CONNTRY);

                Date dNow = new Date();                                                                 //date
                SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm");
                aWeather.date = ft.format(dNow);


                JSONObject main = jsonObject.getJSONObject(TAG_MAIN);
                aWeather.temp = main.getDouble(TAG_TEMP);
                aWeather.humidity = main.getString(TAG_HUMIDITY);

                JSONObject wind = jsonObject.getJSONObject(TAG_WIND);
                aWeather.speed = wind.getString(TAG_SPEED);
                aWeather.deg = wind.getInt(TAG_DEG);


                weathers.add(aWeather);


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
