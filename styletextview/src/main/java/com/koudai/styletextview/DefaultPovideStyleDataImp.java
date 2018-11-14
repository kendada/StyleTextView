package com.koudai.styletextview;

import android.graphics.Typeface;

/**
 * @auther jsk
 * @date 2018/10/29
 */
public abstract class DefaultPovideStyleDataImp implements IPovideStyleData{

    @Override
    public int getHighlightColorId() {
        return R.color.color333333;
    }

    @Override
    public int getHighlightTextSize() {
        return 0;
    }

    @Override
    public int getTypeface() {
        return Typeface.NORMAL;
    }

    @Override
    public boolean isAddStyle() {
        return true;
    }

    @Override
    public String getRuleText() {
        return null;
    }

    @Override
    public boolean isUseRule() {
        return false;
    }

    @Override
    public String getNeedHighlightText() {
        return null;
    }

    @Override
    public int getRichTextStyle() {
        return -1;
    }
}
