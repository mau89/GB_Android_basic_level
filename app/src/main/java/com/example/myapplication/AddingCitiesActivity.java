package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import static android.provider.Telephony.Mms.Part.TEXT;

public class AddingCitiesActivity extends AppCompatActivity {
    String[] city = {"Москва", "Санкт-Петербург", "Новосибирск", "Самара", "Тюмень", "Уфа", "Владивосток"};
    Button add_city;
    String city1;
    private Activity sourceActivity;

    public AddingCitiesActivity(Activity sourceActivity) {
        this.sourceActivity = sourceActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_cities);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


//    };


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

        add_city = findViewById(R.id.add_city);
        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch pressure_switch1 = sourceActivity.findViewById(R.id.pressure_switch);
                Switch pressure_speed1_wind = sourceActivity.findViewById(R.id.pressure_speed_wind);
                Switch pressure_wetness = sourceActivity.findViewById(R.id.pressure_wetness);
                Parsel parsel = new Parsel();
                parsel.cityName = city1;
                parsel.pressure_speed_wind = pressure_speed1_wind.isChecked();
                parsel.pressure_switch = pressure_switch1.isChecked();
                parsel.pressure_wetness = pressure_wetness.isChecked();
                Intent intent = new Intent(sourceActivity, MenuActivity.class);
                intent.putExtra(TEXT, parsel);
                sourceActivity.startActivityForResult(intent,1);

            }
        });
    }

    private void log(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("AddingCitiesActivity", string);
    }
}

