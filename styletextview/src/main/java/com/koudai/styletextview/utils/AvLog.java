package com.koudai.styletextview.utils;

import android.util.Log;

import com.koudai.styletextview.BuildConfig;

/**
 * @auther jsk
 * @date 2018/12/10
 */
public class AvLog {

    public final static String TAG = AvLog.class.getSimpleName();

    public static void d(String text){
        Log.d(TAG, text);
    }

    public static void e(String text){
        Log.e(TAG, text);
    }

    public static void d_bug(String text){
        if (BuildConfig.DEBUG){
            d(text);
        }
    }

}
