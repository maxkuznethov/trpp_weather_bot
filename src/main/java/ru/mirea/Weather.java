package ru.mirea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

public class Weather {
    private static final String API_KEY = "dd2d55e8ce4881fef137b3ac75081732";

    private static String[] getCoordinatesByCityName(String cityName) throws IOException {
        String[] coordinates = new String[2];
        String request = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s",
                cityName, API_KEY);
        String geocodingData = getDataFromRequest(request);
        ArrayList<Map<String,Object>> geocodingArr = JsonConverter.jsonToArray(geocodingData);
        Map<String, Object> geocodingMap = geocodingArr.get(0);
        coordinates[0] = geocodingMap.get("lat").toString();
        coordinates[1]= geocodingMap.get("lon").toString();
        return coordinates;
    }

    private static String getDataFromRequest(String urlString) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null)
            result.append(line);
        reader.close();
        return result.toString();
    }
}
