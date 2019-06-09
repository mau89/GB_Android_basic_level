package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.myapplication.data.BDCity;


public class MenuFragment extends Fragment {
    private static final String ID_CITY = "ID_CITY";
    Button add_location;
    private BDCity bdCity;
    public static final int IDM_OPEN = 101;
    private Cursor cursor;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        add_location = view.findViewById(R.id.add_location);
        add_location.setOnClickListener(v -> {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container,new AddingCitiesFragment())
                    .addToBackStack(MenuFragment.class.getName())
                    .commit();
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        bdCity = new BDCity(getActivity());
        SQLiteDatabase database = bdCity.getReadableDatabase();
        LinearLayout linearLayout = getActivity().findViewById(R.id.linear_city);
        //linearLayout.removeAllViews();
        if (database != null) {
            String[] projection = {
                    BDCity.KEY_ID,
                    BDCity.APP_PREFERENCES_cityName,
                    BDCity.APP_PREFERENCES_pressure_switch,
                    BDCity.APP_PREFERENCES_pressure_speed_wind,
                    BDCity.APP_PREFERENCES_pressure_wetness};
            cursor = database.query(
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
                log(APP_PREFERENCES_cityName);
                linearLayout.addView(generateView(APP_PREFERENCES_cityName, currentID));
            }
        }
    }

    private View generateView(String APP_PREFERENCES_cityName, int currentID) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.layout_city, null, false);
        TextView textView = view.findViewById(R.id.textViewCity);
        textView.setText(APP_PREFERENCES_cityName);
        textView.setTextSize(18f);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(ID_CITY, currentID);
            //setResult(Activity.RESULT_OK, intent);
            cursor.close();
            bdCity.close();
            getActivity().onBackPressed();
        });
//        textView.setOnLongClickListener(v -> {
//            startActivity(new Intent(getActivity(), AddingCitiesFragment.class));
//            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("EDIT_CITY", String.valueOf(currentID));
//            editor.apply();
//            return true;
//        });
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
        menu.add(Menu.NONE,IDM_OPEN,Menu.NONE,"Изменить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //startActivity(new Intent(this, AddingCitiesFragment.class));
        return super.onContextItemSelected(item);
    }

    public void deleteRow(String value) {
        bdCity.getWritableDatabase().delete(BDCity.TABLE_CONTACTS, BDCity.KEY_ID + "=?", new String[]{value});
        bdCity.close();
    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_menu);
//        add_location = findViewById(R.id.add_location);
//        add_location.setOnClickListener(v -> {
//            startActivity(new Intent(this, AddingCitiesFragment.class));
//        });
//    }
//
    private void log(String string) {
        //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.d("MenuFragment", string);
    }
}
