package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.BDCity;

public class MenuActivity extends AppCompatActivity {
    private static final String ID_CITY = "ID_CITY";
    Button add_location;
    private static final int USERID = 6000;
    private int countID;
    private BDCity bdCity;

    @Override
    protected void onResume() {
        super.onResume();
        bdCity = new BDCity(this);
        SQLiteDatabase database = bdCity.getReadableDatabase();
        LinearLayout linearLayout = findViewById(R.id.linear_city);
        linearLayout.removeAllViews();
        if (database != null) {
            String[] projection = {
                    BDCity.KEY_ID,
                    BDCity.APP_PREFERENCES_cityName,
                    BDCity.APP_PREFERENCES_pressure_switch,
                    BDCity.APP_PREFERENCES_pressure_speed_wind,
                    BDCity.APP_PREFERENCES_pressure_wetness};
            Cursor cursor = database.query(
                    BDCity.TABLE_CONTACTS,
                    projection,            // столбцы
                    null,                  // столбцы для условия WHERE
                    null,                  // значения для условия WHERE
                    null,                  // Don't group the rows
                    null,                  // Don't filter by row groups
                    null);
            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(cursor.getColumnIndex(BDCity.KEY_ID));
                String APP_PREFERENCES_cityName = cursor.getString(cursor.getColumnIndex(BDCity.APP_PREFERENCES_cityName));
                int APP_PREFERENCES_pressure_speed_wind = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_speed_wind));
                int APP_PREFERENCES_pressure_switch = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_switch));
                int APP_PREFERENCES_pressure_wetness = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_wetness));

                System.out.println(APP_PREFERENCES_cityName);
                TextView textView = new TextView(getApplicationContext());
                textView.setText(APP_PREFERENCES_cityName);
                textView.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT)
                );
                textView.setId(USERID + countID);
                textView.setTextSize(20f);
                textView.setTextColor(Color.BLACK);
                textView.setPadding(10, 10, 10, 10);

                textView.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.putExtra(ID_CITY, currentID);
                    setResult(Activity.RESULT_OK, intent);
                    log("id " + currentID + "город " + APP_PREFERENCES_cityName + " влажность " + APP_PREFERENCES_pressure_wetness
                            + " скорость ветра " + APP_PREFERENCES_pressure_speed_wind + " давление " + APP_PREFERENCES_pressure_switch + "");
                    cursor.close();
                    bdCity.close();
                    finish();
                });
                linearLayout.addView(textView);
                countID++;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        add_location = findViewById(R.id.add_location);
        add_location.setOnClickListener(v -> {
//          Intent intent = new Intent(this, AddingCitiesActivity.class);
            startActivity(new Intent(this, AddingCitiesActivity.class));
        });
    }

    private void log(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("MenuActivity", string);
    }
}
