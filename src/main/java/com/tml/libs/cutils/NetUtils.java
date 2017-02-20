package com.tml.libs.cutils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetUtils {
    private static final String TAG = "NetUtils";
    public static boolean UseMockSettings = false;
    public static boolean MockSettingNetworkAvailable = false;

    public static boolean isNetworkAvailable(Context c)
    {
        if (UseMockSettings)
        {
            return MockSettingNetworkAvailable;
        }

        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String httpGetJSON(String urlLink) throws Exception {
        URL url = new URL(urlLink);
        Log.d(TAG, "URL: " + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Log.d(TAG, "openned... .");
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        Log.d(TAG, "content length " + conn.getContentLength());
        Log.d(TAG, "content type " + conn.getContentType());

        String output;
        Log.d(TAG, "Output from Server .... \n");
        String jsonStr = "";
        while ((output = br.readLine()) != null) {
            jsonStr += output;
//	        if(output.contains("\"link\": \"")){
//	            String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
//	            Log.d(TAG, link);       //Will print the google search links
//	        }
            //Log.d(TAG, output);
        }
        conn.disconnect();
        return jsonStr;
    }
}
