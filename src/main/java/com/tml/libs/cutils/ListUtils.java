package com.tml.libs.cutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by TML on 5/16/2017.
 */

public class ListUtils {
    public static List<String> getEntryContains(List<String> entries, String text) {
        List<String> res = new ArrayList<>();
        for (String e : entries
             ) {
            if (e.contains(text))
                res.add(e);
        }
        return res;
    }

    public static void sortStringList(List<String> list, final boolean ascending) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                if (ascending)
                    return a.compareTo(b);
                else
                    return -a.compareTo(b);
            }
        });
    }
}
