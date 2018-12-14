package com.koudai.styletextview.styledata.imp;

import android.graphics.Typeface;

import com.koudai.styletextview.R;
import com.koudai.styletextview.styledata.DefaultPovideStyleDataImp;

/**
 * @auther jsk
 * @date 2018/10/26
 */
public class WebUrlPovideStyleData extends DefaultPovideStyleDataImp {

    @Override
    public int getHighlightColorId() {
        return R.color.color0084FB;
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
        return "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
    }

    @Override
    public boolean isUseRule() {
        return true;
    }

    @Override
    public String getNeedHighlightText() {
        return null;
    }

    @Override
    public int getRichTextStyle() {
        return 1;
    }

}
