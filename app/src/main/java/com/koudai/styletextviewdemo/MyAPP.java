package com.koudai.styletextviewdemo;

import android.app.Application;
import android.content.Context;

import com.koudai.styletextview.utils.StyleTexViewtUtils;


/**
 * @auther jsk
 * @date 2018/11/14
 */
public class MyAPP extends Application {

    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        AppContext = this;

        StyleTexViewtUtils.init(this);

    }
}
