package ru.mirea;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonConverter {
    public static Map<String, Object> jsonToMap(String str) {
        return new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }

    public static ArrayList<Map<String, Object>> jsonToArray(String str) {
        return new Gson().fromJson(str, new TypeToken<ArrayList<Map<String, Object>>>() {
        }.getType());
    }
}
