package me.shetj.record.utils;

import android.graphics.Color;

public class Util {

    private Util() {
    }


    public static boolean isBrightColor(int color) {
        if(android.R.color.transparent == color) {
            return true;
        }
        int [] rgb = {Color.red(color), Color.green(color), Color.blue(color)};
        int brightness = (int) Math.sqrt(
                rgb[0] * rgb[0] * 0.241 +
                rgb[1] * rgb[1] * 0.691 +
                rgb[2] * rgb[2] * 0.068);
        return brightness >= 200;
    }

    public static int getDarkerColor(int color) {
        float factor = 0.8f;
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(a,
                Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }

    public static String formatSeconds(int seconds) {
        return getTwoDecimalsValue(seconds / 3600) + ":"
                + getTwoDecimalsValue(seconds / 60) + ":"
                + getTwoDecimalsValue(seconds % 60);
    }

    private static String getTwoDecimalsValue(int value) {
        if (value >= 0 && value <= 9) {
            return "0" + value;
        } else {
            return value + "";
        }
    }

}