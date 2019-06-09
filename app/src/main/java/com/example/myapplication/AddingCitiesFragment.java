package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.myapplication.data.BDCity;

import java.util.Arrays;


public class AddingCitiesFragment extends Fragment {
    private BDCity bdCity;
    String[] city = {"Выберите город", "Москва", "Санкт-Петербург", "Новосибирск", "Самара", "Тюмень", "Уфа", "Владивосток"};
    Button add_city;
    String city1;
    private Spinner spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_cities, container, false);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = view.findViewById(R.id.select_city);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Город");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Выберите город")) {
                } else {
                    log(spinner.getSelectedItem().toString());
                    city1 = spinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            loadCity(sharedPreferences.getString("EDIT_CITY", ""), view);
        }

        bdCity = new BDCity(getActivity());
        add_city = view.findViewById(R.id.add_city);
        add_city.setOnClickListener(v -> {
            SQLiteDatabase database = bdCity.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            Switch pressure_switch1 = view.findViewById(R.id.pressure_switch);
            Switch pressure_speed1_wind = view.findViewById(R.id.pressure_speed_wind);
            Switch pressure_wetness = view.findViewById(R.id.pressure_wetness);
//            if (sharedPreferences != null) {
//                contentValues.put(BDCity.APP_PREFERENCES_cityName, city1);
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_speed_wind, (pressure_speed1_wind.isChecked() ? 1 : 0));
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_switch, (pressure_switch1.isChecked() ? 1 : 0));
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_wetness, (pressure_wetness.isChecked() ? 1 : 0));
//                bdCity.getWritableDatabase().update(BDCity.TABLE_CONTACTS, contentValues, BDCity.KEY_ID + "=?", new String[]{sharedPreferences.getString("EDIT_CITY", "")});
//                getActivity().onBackPressed();
//            } else {
                contentValues.put(BDCity.APP_PREFERENCES_cityName, city1);
                contentValues.put(BDCity.APP_PREFERENCES_pressure_speed_wind, (pressure_speed1_wind.isChecked() ? 1 : 0));
                contentValues.put(BDCity.APP_PREFERENCES_pressure_switch, (pressure_switch1.isChecked() ? 1 : 0));
                contentValues.put(BDCity.APP_PREFERENCES_pressure_wetness, (pressure_wetness.isChecked() ? 1 : 0));
                database.insert(BDCity.TABLE_CONTACTS, null, contentValues);
                getActivity().onBackPressed();
//            }
        });


        return view;
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_adding_cities);
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner = findViewById(R.id.select_city);
//        spinner.setAdapter(adapter);
//        spinner.setPrompt("Город");
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (parent.getItemAtPosition(position).equals("Выберите город")) {
//                } else {
//                    log(spinner.getSelectedItem().toString());
//                    city1 = spinner.getSelectedItem().toString();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        SharedPreferences sharedPreferences = this.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
//        if (sharedPreferences != null) {
//            loadCity(sharedPreferences.getString("EDIT_CITY", ""));
//        }
//
//        bdCity = new BDCity(this);
//        add_city = findViewById(R.id.add_city);
//        add_city.setOnClickListener(v -> {
//            SQLiteDatabase database = bdCity.getWritableDatabase();
//            ContentValues contentValues = new ContentValues();
//            Switch pressure_switch1 = findViewById(R.id.pressure_switch);
//            Switch pressure_speed1_wind = findViewById(R.id.pressure_speed_wind);
//            Switch pressure_wetness = findViewById(R.id.pressure_wetness);
//            if (sharedPreferences != null) {
//                contentValues.put(BDCity.APP_PREFERENCES_cityName, city1);
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_speed_wind, (pressure_speed1_wind.isChecked() ? 1 : 0));
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_switch, (pressure_switch1.isChecked() ? 1 : 0));
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_wetness, (pressure_wetness.isChecked() ? 1 : 0));
//                bdCity.getWritableDatabase().update(BDCity.TABLE_CONTACTS, contentValues, BDCity.KEY_ID + "=?", new String[]{sharedPreferences.getString("EDIT_CITY", "")});
//                finish();
//            } else {
//                contentValues.put(BDCity.APP_PREFERENCES_cityName, city1);
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_speed_wind, (pressure_speed1_wind.isChecked() ? 1 : 0));
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_switch, (pressure_switch1.isChecked() ? 1 : 0));
//                contentValues.put(BDCity.APP_PREFERENCES_pressure_wetness, (pressure_wetness.isChecked() ? 1 : 0));
//                database.insert(BDCity.TABLE_CONTACTS, null, contentValues);
//                finish();
//            }
//        });
//    }
//
    private void loadCity(String edit_city, View view) {
        bdCity = new BDCity(getActivity());
        SQLiteDatabase database = bdCity.getReadableDatabase();
        if (database != null) {
            String[] projection = {
                    BDCity.KEY_ID,
                    BDCity.APP_PREFERENCES_cityName,
                    BDCity.APP_PREFERENCES_pressure_switch,
                    BDCity.APP_PREFERENCES_pressure_speed_wind,
                    BDCity.APP_PREFERENCES_pressure_wetness};
            String selection = "_id = ?";
            String[] selectionArgs = new String[]{edit_city};
            Cursor cursor = database.query(
                    BDCity.TABLE_CONTACTS,
                    projection,            // столбцы
                    selection,                  // столбцы для условия WHERE
                    selectionArgs,                  // значения для условия WHERE
                    null,                  // Don't group the rows
                    null,                  // Don't filter by row groups
                    null);
            while (cursor.moveToNext()) {
                String APP_PREFERENCES_cityName = cursor.getString(cursor.getColumnIndex(BDCity.APP_PREFERENCES_cityName));
                int APP_PREFERENCES_pressure_speed_wind = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_speed_wind));
                int APP_PREFERENCES_pressure_switch = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_switch));
                int APP_PREFERENCES_pressure_wetness = cursor.getInt(cursor.getColumnIndex(BDCity.APP_PREFERENCES_pressure_wetness));
                spinner.setSelection(Arrays.asList(city).indexOf(APP_PREFERENCES_cityName));
                Switch pressure_switch = view.findViewById(R.id.pressure_switch);
                Switch pressure_wetness = view.findViewById(R.id.pressure_wetness);
                Switch pressure_speed_wind = view.findViewById(R.id.pressure_speed_wind);

                if (APP_PREFERENCES_pressure_switch == 0) {
                    pressure_switch.setChecked(false);
                } else {
                    pressure_switch.setChecked(true);
                }
                if (APP_PREFERENCES_pressure_wetness == 0) {
                    pressure_wetness.setChecked(false);
                } else {
                    pressure_wetness.setChecked(true);
                }
                if (APP_PREFERENCES_pressure_speed_wind == 0) {
                    pressure_speed_wind.setChecked(false);
                } else {
                    pressure_speed_wind.setChecked(true);
                }
            }
        }
    }

    //
    private void log(String string) {
        //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("AddingCitiesFragment", string);
    }
}

