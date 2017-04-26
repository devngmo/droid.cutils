package com.tml.libs.cutils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by TML on 4/26/2017.
 */

public class SysUtils {
    public static void forceShowSoftKeyboard(Context c)
    {
        InputMethodManager imm = (InputMethodManager)c.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public static void hideSoftKeyboard(Activity a) {
        if(a.getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(Context c, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        v.requestFocus();
        inputMethodManager.showSoftInput(v, 0);
    }

    public static void hideSoftKeyboard(Context c, IBinder binder)
    {
        InputMethodManager imm = (InputMethodManager)c.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, 0);
    }
}
