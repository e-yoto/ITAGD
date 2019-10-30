package edu.itagd.tgnamo.istodayagooddayto.controller.apicall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIController {
    private final static String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=45.5017&lon=-73.5673&appid=8daeb0d0788550bfd74026a8efa74bc2";
    public APIController(){}

    public String getResults(){
        String results = "";
        try {
            // 1. Open connection with URL
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 2. Open InputStream to connection
            connection.connect();
            InputStream input = connection.getInputStream();
            // 3. Download and decode the string response using builder
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            results = builder.toString();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return results;
    }
}
