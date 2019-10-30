package edu.itagd.tgnamo.istodayagooddayto.controller.apicall;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import edu.itagd.tgnamo.istodayagooddayto.model.Weather;

public class JSONParser {

    public Weather parse(String result){
        JSONObject jsonObject = null;
        Weather weather = new Weather();

        try {
            jsonObject = new JSONObject(result);
            JSONObject weatherInfo = jsonObject.getJSONArray("weather").getJSONObject(0);
            weather.setId(weatherInfo.getInt("id"));
            weather.setWeather(weatherInfo.getString("main"));
            weather.setWeatherDesc(weatherInfo.getString("description"));
            weather.setTemperature(jsonObject.getJSONObject("main").getLong("temp"));
            weather.setHumidity(jsonObject.getJSONObject("main").getString("humidity"));
            weather.setPressure(jsonObject.getJSONObject("main").getString("pressure"));
            weather.setCountry(jsonObject.getJSONObject("sys").getString("country"));
            weather.setSunrise(jsonObject.getJSONObject("sys").getLong("sunrise"));
            weather.setSunset(jsonObject.getJSONObject("sys").getLong("sunset"));
            weather.setCity(jsonObject.getString("name"));

            Log.d("test", ""+weatherInfo.getInt("id"));
            Log.d("test", weatherInfo.getString("main"));
            Log.d("test", weatherInfo.getString("description"));
            Log.d("test", ""+jsonObject.getJSONObject("main").getLong("temp"));
            Log.d("test", jsonObject.getJSONObject("main").getString("humidity"));
            Log.d("test", jsonObject.getJSONObject("main").getString("pressure"));
            Log.d("test", jsonObject.getJSONObject("sys").getString("country"));
            Log.d("test", ""+jsonObject.getJSONObject("sys").getLong("sunrise"));
            Log.d("test", ""+jsonObject.getJSONObject("sys").getLong("sunset"));
            Log.d("test", jsonObject.getString("name"));

        }
        catch (JSONException e){
            e.printStackTrace();
            Log.d("test", "Parsing failed");
        }

        return weather;
    }
}
