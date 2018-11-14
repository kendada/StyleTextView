package com.koudai.styletextviewdemo;

import android.graphics.Typeface;

import com.koudai.styletextview.DefaultPovideStyleDataImp;

/**
 * @auther jsk
 * @date 2018/11/14
 */
public class NameProideStyleData extends DefaultPovideStyleDataImp {

    private String mNickName;

    public NameProideStyleData(String nickName){
        mNickName = nickName;
    }

    @Override
    public int getHighlightColorId() {
        return R.color.color2F93FF;
    }

    @Override
    public int getHighlightTextSize() {
        return 14;
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
        return mNickName;
    }

    @Override
    public int getRichTextStyle() {
        return 100;
    }
}
