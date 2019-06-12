package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;

import com.example.myapplication.data.CityDataBaseHelper;
import com.example.myapplication.utils.Preferences;

public class App extends Application {
    private static int screenWidth;
    private Preferences preferences;
    private CityDataBaseHelper cityDataBaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new Preferences(PreferenceManager.getDefaultSharedPreferences(this));
        cityDataBaseHelper = cityDataBaseHelper = new CityDataBaseHelper(this);
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public CityDataBaseHelper getCityDataBaseHelper() {
        return cityDataBaseHelper;
    }

    public static int getScreenWidth(Context context) {

        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }
        return screenWidth;
    }
}
