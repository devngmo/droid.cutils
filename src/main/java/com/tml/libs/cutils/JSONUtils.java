package com.tml.libs.cutils;

import android.os.Build;

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
            ex.printStackTrace();;
        }
        return null;
    }


}
