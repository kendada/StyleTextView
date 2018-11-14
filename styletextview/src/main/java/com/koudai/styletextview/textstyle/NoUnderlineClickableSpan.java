package com.koudai.styletextview.textstyle;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * @auther jsk
 * @date 2018/9/3
 */
public abstract class NoUnderlineClickableSpan extends ClickableSpan {

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
