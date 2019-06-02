package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.BDCity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;
    ImageButton menu;
    ImageButton info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.menu);
        menu.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
        info = findViewById(R.id.info);
        info.setOnClickListener(v -> System.out.println("info"));
        log("onCreate");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            return;
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        System.out.println(data.getIntExtra("ID_CITY", 1));
        BDCity bdCity = new BDCity(this);
        SQLiteDatabase database = bdCity.getReadableDatabase();

        if (database != null) {
            String[] projection = {
                    BDCity.KEY_ID,
                    BDCity.APP_PREFERENCES_cityName,
                    BDCity.APP_PREFERENCES_pressure_switch,
                    BDCity.APP_PREFERENCES_pressure_speed_wind,
                    BDCity.APP_PREFERENCES_pressure_wetness};
            String selection = "_id = ?";
            String[] selectionArgs = new String[]{String.valueOf(data.getIntExtra("ID_CITY", 1))};
            Cursor cursor = database.query(
                    BDCity.TABLE_CONTACTS,
                    projection,            // столбцы
                    selection,                  // столбцы для условия WHERE
                    selectionArgs,                  // значения для условия WHERE
                    null,                  // Don't group the rows
                    null,                  // Don't filter by row groups
                    null);
            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(cursor.getColumnIndex(BDCity.KEY_ID));
                String APP_PREFERENCES_cityName = cursor.getString(cursor.getColumnIndex(BDCity.APP_PREFERENCES_cityName));
                int APP_PREFERENCES_pressure_speed_wind = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_speed_wind));
                int APP_PREFERENCES_pressure_switch = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_switch));
                int APP_PREFERENCES_pressure_wetness = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_wetness));
                log("id " + currentID + "город " + APP_PREFERENCES_cityName + " влажность " + APP_PREFERENCES_pressure_wetness
                        + " скорость ветра " + APP_PREFERENCES_pressure_speed_wind + " давление " + APP_PREFERENCES_pressure_switch + "");
                System.out.println(APP_PREFERENCES_cityName);
                TextView textViewCity = findViewById(R.id.City);
                textViewCity.setText(APP_PREFERENCES_cityName);
                TextView textView4 = findViewById(R.id.textView4);
                TextView textView5 = findViewById(R.id.textView5);
                TextView textView6 = findViewById(R.id.textView6);
                if (APP_PREFERENCES_pressure_switch == 0) {
                    textView4.setVisibility(View.GONE);
                } else {
                    textView4.setVisibility(View.VISIBLE);
                }
                if (APP_PREFERENCES_pressure_wetness == 0) {
                    textView5.setVisibility(View.GONE);
                } else {
                    textView5.setVisibility(View.VISIBLE);
                }
                if (APP_PREFERENCES_pressure_speed_wind == 0) {
                    textView6.setVisibility(View.GONE);
                } else {
                    textView6.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    private void log(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("MainActivity", string);
    }
}

