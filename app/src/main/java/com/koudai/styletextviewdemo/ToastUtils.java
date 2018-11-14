package com.koudai.styletextviewdemo;

import android.view.Gravity;
import android.widget.Toast;

/**
 * @auther jsk
 * @date 2018/11/14
 */
public class ToastUtils {

    public static void show(String text){
        show(text, Gravity.CENTER);
    }

    public static void show(String text, int gravity){
        if (MyAPP.AppContext == null) return;

        sToast = createToast();
        if (sToast == null) return;

        sToast.setGravity(gravity, 0, 0);
        sToast.setText(text);

        sToast.show();
    }

    public static void showDebug(String text){
        showDebug(text, Gravity.CENTER);
    }

    public static void showDebug(String text, int gravity){
        if (BuildConfig.DEBUG){
            show(text, gravity);
        }
    }

    private static Toast sToast;

    private static Toast createToast(){
        if (MyAPP.AppContext == null) return null;
        if (sToast == null){
            sToast = Toast.makeText(MyAPP.AppContext, "", Toast.LENGTH_SHORT);
        }
        return sToast;
    }


}
