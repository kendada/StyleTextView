package com.koudai.styletextview.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * @auther jsk
 * @date 2018/11/14
 */
public class AppStyleUtils {

    private static Context mContext;

    private AppStyleUtils(){

    }

    public static void init(Context context){
        mContext = context;
    }

    public static Resources getResources(){
        if (mContext != null){
            return mContext.getResources();
        }
        return null;
    }

    public static int getColor(int colorId){
        Resources resources = getResources();
        if (resources != null){
            return resources.getColor(colorId);
        }
        return -1;
    }

    public static String getString(int strId){
        Resources resources = getResources();
        if (resources != null){
            return resources.getString(strId);
        }
        return "";
    }

}
