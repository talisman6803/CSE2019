package com.talisman6803.forecaster;

import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.RequiresApi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class JsonParsing {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[] Classify(String target) {
        String jsonData = target;
        try {
            JSONParser jsonParse = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParse.parse(jsonData);
            JSONArray dailyInfo = (JSONArray) jsonObj.get("list");

            int days_num = Integer.parseInt(jsonObj.get("cnt").toString());
            String[] data = new String[days_num];

            for (int i = 0; i < days_num; i++) {
                JSONObject day = (JSONObject) dailyInfo.get(i);
                JSONObject temp = (JSONObject) day.get("temp");
                JSONArray weather = (JSONArray) day.get("weather");
                JSONObject wt = (JSONObject) weather.get(0);

                data[i] = UnixTStoStr(day.get("dt").toString()) + "- " + wt.get("main").toString() + " - "
                        + temp.get("max").toString() + "/" + temp.get("min").toString();
            }  //e.g 일 5월 24 - Rain - 26/14

            return data;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String UnixTStoStr(String dt) {

        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEE MM월 dd ");

        long unixTime = Long.parseLong(dt);
        final String formattedDtm = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("GMT+9"))
                .format(formatter);

        return formattedDtm;
    }
}