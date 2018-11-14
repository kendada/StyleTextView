package com.koudai.styletextview.textstyle;

import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

/**
 * Created by jsk on 2017/12/11
 * 设置字体加粗
 */

public class BoldTextStyleSpan extends ForegroundColorSpan {

    public BoldTextStyleSpan(int color) {
        super(color);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setFakeBoldText(true);
    }
}
