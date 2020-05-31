package com.talisman6803.forecaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity<FragFunction> extends AppCompatActivity {

    public static final String KEY_NAME = "name";
    private static final int REQUEST_CODE = 1;
    private Fragment frg = null;
    private String target = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);



        switch (item.getItemId()) {
            case R.id.settings:
                EventBus.getDefault().register(this);
                toast.setText("Settings clicked");
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.refresh:
                toast.setText("Refresh clicked");
                ForecastFragment forecast = new ForecastFragment();
                Bundle bundle = new Bundle();
                bundle.putString("cityname", target);
                forecast.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, forecast).commit();
                EventBus.getDefault().unregister(this);
                break;
        }

        toast.show();

        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCityData(SettingPreferenceFragment.CityData cityData){
        target = cityData.city;
        Log.d("get city data in MA", target);
    }
}
