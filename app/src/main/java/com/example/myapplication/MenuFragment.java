package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.data.CityDataBaseHelper;
import com.example.myapplication.utils.Preferences;

import java.util.Objects;

public class MenuFragment extends Fragment {

    private Button add_location;
    private CityDataBaseHelper cityDataBaseHelper;
    private SQLiteDatabase database;
    private static final int IDM_OPEN = 101;
    private Cursor cursor;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        cityDataBaseHelper =((App)getActivity().getApplication()).getCityDataBaseHelper();
        database = cityDataBaseHelper.getReadableDatabase();
        add_location = view.findViewById(R.id.add_location);
        add_location.setOnClickListener(v -> {
            ((App) Objects.requireNonNull(getActivity()).getApplication()).getPreferences().putString("", Preferences.Key.EDIT_CITY);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new AddingCitiesFragment())
                    .addToBackStack(MenuFragment.class.getName())
                    .commit();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayout linearLayout = getActivity().findViewById(R.id.linear_city);
        if (database == null) {
            return;
        }
        String[] projection = {
                CityDataBaseHelper.KEY_ID,
                CityDataBaseHelper.APP_PREFERENCES_cityName,
                CityDataBaseHelper.APP_PREFERENCES_pressure_switch,
                CityDataBaseHelper.APP_PREFERENCES_pressure_speed_wind,
                CityDataBaseHelper.APP_PREFERENCES_pressure_wetness};
        cursor = database.query(
                CityDataBaseHelper.TABLE_CONTACTS,
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);
        while (cursor.moveToNext()) {
            int currentID = cursor.getInt(cursor.getColumnIndex(CityDataBaseHelper.KEY_ID));
            String APP_PREFERENCES_cityName = cursor.getString(cursor.getColumnIndex(CityDataBaseHelper.APP_PREFERENCES_cityName));
            log(APP_PREFERENCES_cityName);
            linearLayout.addView(generateView(APP_PREFERENCES_cityName, currentID));
        }
        cursor.close();

    }

    private View generateView(String APP_PREFERENCES_cityName, int currentID) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.layout_city, null, false);
        TextView textView = view.findViewById(R.id.textViewCity);
        textView.setText(APP_PREFERENCES_cityName);
        textView.setTextSize(18f);
        textView.setOnClickListener(v -> {
            ((App) getActivity().getApplication()).getPreferences().putString(String.valueOf(currentID), Preferences.Key.CITY);
            getActivity().onBackPressed();
        });
        textView.setOnLongClickListener(v -> {
            ((App) getActivity().getApplication()).getPreferences().putString(String.valueOf(currentID), Preferences.Key.EDIT_CITY);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new AddingCitiesFragment())
                    .addToBackStack(MenuFragment.class.getName())
                    .commit();
            return true;
        });
        TextView textView1 = view.findViewById(R.id.textView);
        textView1.setText("30 \u2103");
        ImageButton button = view.findViewById(R.id.delCity);
        button.setOnClickListener(v -> {
            deleteRow(String.valueOf(currentID));
            onResume();
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Изменить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //startActivity(new Intent(this, AddingCitiesFragment.class));
        return super.onContextItemSelected(item);
    }

    private void deleteRow(String value) {
        cityDataBaseHelper.getWritableDatabase().delete(CityDataBaseHelper.TABLE_CONTACTS, CityDataBaseHelper.KEY_ID + "=?", new String[]{value});
    }

    public void onDestroyView() {
        super.onDestroyView();
        database.close();
        cityDataBaseHelper.close();
    }
    private void log(String string) {
        //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("MenuFragment", string);
    }
}
