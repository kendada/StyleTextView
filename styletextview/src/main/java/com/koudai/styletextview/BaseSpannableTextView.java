package com.koudai.styletextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @auther jsk
 * @date 2018/10/16
 */
public class BaseSpannableTextView extends AppCompatTextView {

    private LinkTouchMovementMethod mLinkTouchMovementMethod;

    public BaseSpannableTextView(Context context) {
        this(context, null);
    }

    public BaseSpannableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSpannableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLinkTouchMovementMethod(LinkTouchMovementMethod linkTouchMovementMethod) {
        mLinkTouchMovementMethod = linkTouchMovementMethod;
        if (mLinkTouchMovementMethod != null){
            setMovementMethod(mLinkTouchMovementMethod);
        }
    }

}
