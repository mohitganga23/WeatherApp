package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relative_layout;
    Button btn_getForecast;
    TextInputLayout et_place;
    ListView list_forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        btn_getForecast = findViewById(R.id.btn_getForecast);
        et_place = findViewById(R.id.et_location);
        list_forecast = findViewById(R.id.forecast_list_view);

        btn_getForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityForecastByName(et_place.getEditText().getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Snackbar snackbar = Snackbar
                                .make(relative_layout, "Something Wrong! Cannot get data", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        list_forecast.setAdapter(arrayAdapter);
                    }
                });
            }
        });
    }
}