package com.tml.libs.cutils;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TML on 3/29/2017.
 */

public class JSONUtils {
    public static String getString(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getString(JsonObject obj, String key, String defaultReturn) {
        try {
            return obj.get(key).getAsString();
        }
        catch (Exception ex) {
            Gson gson = new Gson();
            StaticLogger.W("property ["+key+"] not found in : " + gson.toJson(obj));
            ex.printStackTrace();
        }
        return defaultReturn;
    }
}
