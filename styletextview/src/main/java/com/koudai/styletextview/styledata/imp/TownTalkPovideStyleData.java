package com.koudai.styletextview.styledata.imp;

import android.graphics.Typeface;

import com.koudai.styletextview.R;
import com.koudai.styletextview.styledata.DefaultPovideStyleDataImp;
import com.koudai.styletextview.utils.MatchUtils;

/**
 * 话题
 * @auther jsk
 * @date 2018/10/26
 */
public class TownTalkPovideStyleData extends DefaultPovideStyleDataImp {

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
        return MatchUtils.REGEX_TOWNTALK;
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
        return 2;
    }

}
