package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.provider.Telephony.Mms.Part.TEXT;

public class MenuActivity extends AppCompatActivity {
    Button add_location;
    private final int USERID = 6000;
    private int countID;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Parsel parsel = (Parsel) getIntent().getExtras().getSerializable(TEXT);
        LinearLayout linearLayout = findViewById(R.id.linear_city);
        if (!parsel.cityName.equals("")) {
            Button button = new Button(getApplicationContext());
            button.setText(parsel.cityName);
            button.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
            );
            button.setId(USERID + countID);
            linearLayout.addView(button);
            countID++;
            log("add button город "+parsel.cityName+" Отображать влажность "+ parsel.pressure_wetness+
                    " Отображать давление "+parsel.pressure_switch+
                    " Отображать скорость ветра "+ parsel.pressure_speed_wind);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        add_location = findViewById(R.id.add_location);
        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AddingCitiesActivity.class));
            }
        });



    }
    private void log(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("MenuActivity", string);
    }
}
