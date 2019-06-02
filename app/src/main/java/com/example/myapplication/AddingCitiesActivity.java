package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.data.BDCity;


public class AddingCitiesActivity extends AppCompatActivity {
    private BDCity bdCity;
    String[] city = {"Москва", "Санкт-Петербург", "Новосибирск", "Самара", "Тюмень", "Уфа", "Владивосток"};
    Button add_city;
    String city1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_cities);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = findViewById(R.id.select_city);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Город");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                log(spinner.getSelectedItem().toString());
                city1 = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bdCity = new BDCity(this);
        add_city = findViewById(R.id.add_city);
        add_city.setOnClickListener(v -> {
            Switch pressure_switch1 = findViewById(R.id.pressure_switch);
            Switch pressure_speed1_wind = findViewById(R.id.pressure_speed_wind);
            Switch pressure_wetness = findViewById(R.id.pressure_wetness);
            SQLiteDatabase database = bdCity.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(BDCity.APP_PREFERENCES_cityName, city1);
            contentValues.put(BDCity.APP_PREFERENCES_pressure_speed_wind, (pressure_speed1_wind.isChecked() ? 1 : 0));
            contentValues.put(BDCity.APP_PREFERENCES_pressure_switch, (pressure_switch1.isChecked() ? 1 : 0));
            contentValues.put(BDCity.APP_PREFERENCES_pressure_wetness, (pressure_wetness.isChecked() ? 1 : 0));
            database.insert(BDCity.TABLE_CONTACTS, null, contentValues);
            finish();
        });
    }

    private void log(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("AddingCitiesActivity", string);
    }
}

