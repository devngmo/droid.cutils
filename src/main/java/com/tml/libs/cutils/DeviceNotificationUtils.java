package com.tml.libs.cutils;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;

/**
 * Created by ManhLinh on 4/15/2017.
 */

public class DeviceNotificationUtils {
    public static void playNotificationSound(Context c) {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(c, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playBeepSound() {
        try {
            final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
