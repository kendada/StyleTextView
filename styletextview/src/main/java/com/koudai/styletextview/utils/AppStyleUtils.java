package com.koudai.styletextview.utils;

import android.content.Context;

/**
 * @auther jsk
 * @date 2018/11/14
 * v2.0.0 版本删除 请使用 {@link StyleTexViewtUtils} 进行初始化
 */
@Deprecated
public class AppStyleUtils {

    private AppStyleUtils(){

    }

    public static void init(Context context){
        StyleTexViewtUtils.init(context);
    }

}
