package com.talisman6803.forecaster;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutionException;

public class ForecastFragment extends Fragment {

    public ForecastFragment() {
        // Required empty public constructor
    }

    public String r_data = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        FetchWeatherTask fetchWeatherTask = new FetchWeatherTask();
        JsonParsing jsonParsing = new JsonParsing();
        String[] dailyList = new String[0];

        String target_city = getArguments().getString("cityname");
        Log.d("get city successfully", target_city);

        Integer citycode = 0;
        try {
            if(target_city.equals("Seoul"))
                citycode = 1835847;
            else if(target_city.equals("Daejeon"))
                citycode = 1835235;
            else if(target_city.equals("Daegu"))
                citycode = 1835327;
            else if(target_city.equals("Busan"))
                citycode = 1838524;
            else
                Log.i("name on GCV in FF", "failed maybe...");
            Log.d("citycode in FF", citycode.toString());
            r_data = fetchWeatherTask.execute(citycode).get();
            dailyList = jsonParsing.Classify(r_data);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        fetchWeatherTask.cancel(true);

        ListView listView = (ListView) view.findViewById(R.id.dailylist);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                dailyList
        );
        listView.setAdapter(listViewAdapter);
        EventBus.getDefault().unregister(this);
        return view;
    }
}
