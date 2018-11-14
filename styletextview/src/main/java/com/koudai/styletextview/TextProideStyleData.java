package com.koudai.styletextview;

import android.graphics.Typeface;

/**
 * @auther jsk
 * @date 2018/10/29
 */
public class TextProideStyleData extends DefaultPovideStyleDataImp{

    public String mText;

    public int mHighlightColorId = R.color.color333333;

    @Override
    public int getHighlightColorId() {
        return mHighlightColorId;
    }

    public void setHighlightColorId(int highlightColorId){
        mHighlightColorId = highlightColorId;
    }

    @Override
    public int getTypeface() {
        return Typeface.BOLD;
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
        return mText;
    }

    public void setNeedHighlightText(String text){
        mText = text;
    }

    @Override
    public int getRichTextStyle() {
        return 4;
    }
}
