package com.tml.libs.cutils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TML on 19/01/2018.
 */

public class StaticMap {
    static Map<String, Object> map = new HashMap<>();
    public static final String ARG_STATIC_REF_ID = "static.ref.id";

    static int newRefID = 0;
    public static String genNewRefID() {
        return "refid." + newRefID++;
    }

    public static String putNewObject(Object object) {
        String refID = genNewRefID();
        map.put(refID, object);
        return refID;
    }

    public static Object get(String id) {
        return map.get(id);
    }
}
