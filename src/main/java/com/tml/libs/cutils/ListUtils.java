package com.tml.libs.cutils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

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

    public static List<Integer> createIndicesList(int min, int max) {
        List<Integer> ls = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            ls.add(i);
        }
        return ls;
    }

    /**
     * create random list Indices (min, max)
     *
     * @param min min value (included)
     * @param max max value (included)
     * @return
     */
    public static List<Integer> createRandomIndicesList(int min, int max) {
        List<Integer> ls = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            ls.add(i);
        }

        List<Integer> lsrand = new ArrayList<>();
        while(ls.size() > 0) {
            int index = rnd.nextInt(ls.size());
            lsrand.add(ls.get(index));
            ls.remove(index);
        }
        return lsrand;
    }

    static Random rnd = new Random();
    @NotNull
    public static String pickAny(List<String> items) {
        int idx = rnd.nextInt(items.size());
        return items.get(idx);
    }

    @NotNull
    public static ArrayList<IDQty> pickQty(@NotNull List<String> items, int maxQty) {
        int total = 0;
        ArrayList<IDQty> ls = new ArrayList<>();
        while(total < maxQty) {
            int idx = rnd.nextInt(items.size());
            int qty = MathUtils.clamp( rnd.nextInt(maxQty),1, maxQty - total );
            String pickedItem = items.get(idx);
            boolean addNew = true;
            for (IDQty o : ls) {
                if(o.id == pickedItem) {
                    o.qty += qty;
                    addNew = false;
                    break;
                }
            }
            if (addNew) {
                ls.add(new IDQty(pickedItem, qty));
            }
        }
        return ls;
    }
}
