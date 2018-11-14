package com.koudai.styletextview;

import com.koudai.styletextview.textstyle.TextStylePhrase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 富文本样式
 * @auther jsk
 * @date 2018/10/26
 */
public abstract class BaseRichTextStyle {

    public TextStylePhrase mTextStylePhrase;
    public String mContent;

    public IPovideStyleData mIPovideStyleData;

    public BaseRichTextStyle(String content, TextStylePhrase textStylePhrase){
        mContent = content;
        mTextStylePhrase = textStylePhrase;
    }

    /**
     * 匹配特定的字符串
     * @param text
     * @param pattern
     * */
    public List<String> matchTargetText(String text, Pattern pattern){
        List<String> targetList = new ArrayList<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int matchStart = matcher.start();
            int matchEnd = matcher.end();
            String tmptarget = text.substring(matchStart, matchEnd);
            targetList.add(tmptarget);
            text = text.replace(tmptarget,"");
            matcher = pattern.matcher(text);
        }
        return targetList;
    }

    public TextStylePhrase getTextStylePhrase() {
        return mTextStylePhrase;
    }

    public void setTextStylePhrase(TextStylePhrase textStylePhrase) {
        this.mTextStylePhrase = textStylePhrase;
    }

    public abstract int getRichTextStyle();

    public abstract void setRichTextStyle(); // 设置样式：话题 | AT某用户 | 展开、收起 | ...


    public interface OnTagContentClickListenter{
        void onClick(int style, String text);
    }

}

