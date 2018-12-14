package com.koudai.styletextview.styledata.imp;

import android.graphics.Typeface;

import com.koudai.styletextview.R;
import com.koudai.styletextview.styledata.DefaultPovideStyleDataImp;

/**
 * @auther jsk
 * @date 2018/10/29
 */
public class TextProideStyleData extends DefaultPovideStyleDataImp {

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
    public boolean isMatchOne() {
        return true;
    }

    @Override
    public int getMatchWhichOne() {
        return 0;
    }

    @Override
    public int getRichTextStyle() {
        return 4;
    }
}
