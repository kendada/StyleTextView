package com.koudai.styletextview;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.AttributeSet;

/**
 * @auther jsk
 * @date 2019/3/26
 */
public class EllipsizeRichTextView extends RichTextView{

    private static final String THREE_DOTS = "...";
    private static final int THREE_DOTS_LENGTH = THREE_DOTS.length();

    public EllipsizeRichTextView(Context context) {
        this(context, null);
    }

    public EllipsizeRichTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EllipsizeRichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Layout layout = getLayout();

        if (layout.getLineCount() >= getMaxLines()) {
            CharSequence charSequence = getText();
            int lastCharDown = layout.getLineVisibleEnd(getMaxLines()-1);

            if (lastCharDown >= THREE_DOTS_LENGTH && charSequence.length() > lastCharDown){
                CharSequence subCharSequence = charSequence.subSequence(0, lastCharDown - THREE_DOTS_LENGTH) + THREE_DOTS;
                setContentText(subCharSequence.toString());
                showText();
            }
        }

        super.onDraw(canvas);
    }
}
