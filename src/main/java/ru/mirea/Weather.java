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

    public static String getDayWeather(String cityName, int dayIndex){
        try {
            Map<String, Object> weatherMap = getWeatherMap(cityName);
            ArrayList<Map<String,Object>> days = (ArrayList<Map<String, Object>>) weatherMap.get("daily");
            Map<String, Object> dayMap = days.get(dayIndex);
            Map<String,Object> temperatureMap = (Map<String, Object>) dayMap.get("temp");
            ArrayList<Map<String, Object>> states = (ArrayList<Map<String, Object>>) dayMap.get("weather");
            Map<String, Object> stateMap = states.get(0);
            return "Погода: " + stateMap.get("description") +
                    "\nТемпература ночью: " + temperatureMap.get("night") + "°C"+
                    "\nТемпература утром: " + temperatureMap.get("morn") + "°C"+
                    "\nТемпература днем: " + temperatureMap.get("day") + "°C" +
                    "\nТемпература вечером: " + temperatureMap.get("eve") + "°C"+
                    "\nСкорость ветра: " + dayMap.get("wind_speed") +" м/с"+
                    "\nВлажность: " +dayMap.get("humidity")+"%";
        } catch (Exception e) {
            return "Город введен неверно";
        }
    }

    public static String getCurrentWeather(String cityName) {
        try {
            Map<String, Object> weatherMap = getWeatherMap(cityName);
            Map<String, Object> currentMap = (Map<String, Object>) weatherMap.get("current");
            ArrayList<Map<String, Object>> states = (ArrayList<Map<String, Object>>) currentMap.get("weather");
            Map<String, Object> stateMap = states.get(0);
            return "Погода: " + stateMap.get("description") +
                    "\nТемпература: " + currentMap.get("temp") + "°C" +
                    "\nЧувствуется как: " + currentMap.get("feels_like") + "°C" +
                    "\nСкорость ветра: " + currentMap.get("wind_speed") +" м/с" +
                    "\nВлажность: " +currentMap.get("humidity")+"%";
        } catch (Exception e) {
            return "Город введен неверно";
        }
    }


    public static Map<String, Object> getWeatherMap(String cityName) throws Exception {
        String[] coordinates = getCoordinatesByCityName(cityName);
        String weatherRequest = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&" +
                        "exclude=hourly,alerts,minutely&appid=%s&units=metric&lang=ru",
                coordinates[0], coordinates[1], API_KEY);
        String weatherData = getDataFromRequest(weatherRequest);
        return JsonConverter.jsonToMap(weatherData);
    }

    private static String[] getCoordinatesByCityName(String cityName) throws Exception {
        String[] coordinates = new String[2];
        String geocodingRequest = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s",
                cityName, API_KEY);
        String geocodingData = getDataFromRequest(geocodingRequest);
        ArrayList<Map<String, Object>> geocodingArr = JsonConverter.jsonToArray(geocodingData);
        Map<String, Object> geocodingMap = geocodingArr.get(0);
        coordinates[0] = geocodingMap.get("lat").toString();
        coordinates[1] = geocodingMap.get("lon").toString();
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
