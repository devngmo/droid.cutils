package com.tml.libs.cutils;

import android.util.Log;

import java.util.Map;

/**
 * Created by TML on 4/11/2016.
 */
public class SoapUtils {
    public static String create(String methodName, String xmlns, Map<String, String> params) {
        String wsSoapEnvelope =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                        + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                        + "  <soap:Body>"
                        + String.format("  <%s xmlns=\"%s\">", methodName, xmlns);

        if (params != null) {
            for (String key: params.keySet()
                    )
            {
                wsSoapEnvelope += String.format("    <%s>%s</%s>", key, params.get(key), key);
            }
        }
        wsSoapEnvelope += String.format("  </%s>", methodName)
                + "  </soap:Body>"
                + "</soap:Envelope>";
        return wsSoapEnvelope;
    }



    public static String create(String methodName, String xmlns, String key, String value) {
        String wsSoapEnvelope =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                        + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                        + "  <soap:Body>"
                        + String.format("  <%s xmlns=\"%s\">", methodName, xmlns);

        wsSoapEnvelope += String.format("    <%s>%s</%s>", key, value, key);
        wsSoapEnvelope += String.format("  </%s>", methodName)
                + "  </soap:Body>"
                + "</soap:Envelope>";
        return wsSoapEnvelope;
    }

    public static String create(String methodName, String xmlns) {
        String wsSoapEnvelope =
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                        + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                        + "  <soap:Body>"
                        + String.format("  <%s xmlns=\"%s\">", methodName, xmlns);

        wsSoapEnvelope += String.format("  </%s>", methodName)
                + "  </soap:Body>"
                + "</soap:Envelope>";
        return wsSoapEnvelope;
    }


    public static String getSoapResult(String webMethod, String response) {
        String startTag = String.format("<%sResult>", webMethod);
        String endTag = String.format("</%sResult>", webMethod);
        String oneTag = String.format("<%sResult />", webMethod);


        int startIndex = response.indexOf(startTag) + startTag.length();
        int endIndex = response.indexOf(endTag);
        int oneTagIndex = response.indexOf(oneTag);

        if (startIndex >= 0 && endIndex >= 0) {
            Log.d("AC", "two tag");
            return response.substring(startIndex, endIndex);
        }
        else if (oneTagIndex > 0) {
            Log.d("AC", "one tag");
            return "";
        }
        Log.d("AC", "no tag");
        return  response;
    }
}
