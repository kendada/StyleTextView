package com.koudai.styletextview;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

/**
 * @auther jsk
 * @date 2018/10/16
 */
public class LinkTouchMovementMethod extends LinkMovementMethod {

    private static LinkTouchMovementMethod sInstance;
    public static LinkTouchMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new LinkTouchMovementMethod();
        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        // 因为TextView没有点击事件，所以点击TextView的非富文本时，super.onTouchEvent()返回false；
        // 此时可以让TextView的父容器执行点击事件；
        boolean isConsume =  super.onTouchEvent(widget, buffer, event);
        if (!isConsume && event.getAction() == MotionEvent.ACTION_UP) {
            ViewParent parent = widget.getParent();
            if (parent instanceof ViewGroup) {
                // 获取被点击控件的父容器，让父容器执行点击；
                ((ViewGroup) parent).performClick();
            }
        }
        return isConsume;
    }

}
