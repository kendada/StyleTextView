package com.koudai.styletextview.textstyle;

import android.text.TextUtils;

import java.util.List;

/**
 * @auther jsk
 * @date 2019-09-26
 */
public class TextSizeUtils {

    /**
     * 获取TextSize
     * @param targetText 目标短语
     * @return TextSize 开始索引 & 结束索引
     * */
    public static TextStylePhrase.TextSize getTextSize(String courceText, String targetText){
        if (TextUtils.isEmpty(courceText) || TextUtils.isEmpty(targetText)) return null;
        if (courceText.contains(targetText)){
            int start = courceText.indexOf(targetText);
            return TextStylePhrase.Builder.obtain(targetText, start, start + targetText.length()).getTextSize();
        }
        return null;
    }

    /**
     * 获取TextSize -- 忽略大小写匹配索引
     * @param targetText 目标短语
     * @return TextSize 开始索引 & 结束索引
     * */
    public static TextStylePhrase.TextSize getTextSizeIgnoreCase(String courceText, String targetText){
        if (TextUtils.isEmpty(courceText) || TextUtils.isEmpty(targetText)) return null;
        return getTextSize(courceText.toUpperCase(), targetText.toUpperCase());
    }

    /**
     * 比较两个TextSize是否一样
     * */
    public static boolean equals(TextStylePhrase.TextSize a, TextStylePhrase.TextSize b){
        if (a == null || b == null) return false;

        return a.getStart() == b.getStart()
                && a.getEnd() == b.getEnd()
                && TextUtils.equals(a.getText(), b.getText());
    }

    /**
     * TextSize排序 - 从小到大
     * */
    public static List<TextStylePhrase.TextSize> sortByStart(List<TextStylePhrase.TextSize> list){
        for (int i=0; i<list.size(); i++){
            for (int j = i + 1; j < list.size(); j++){
                TextStylePhrase.TextSize mTextSizeI = list.get(i);
                TextStylePhrase.TextSize mTextSizeJ = list.get(j);
                if (mTextSizeI.getStart() > mTextSizeJ.getStart()) {
                    list.remove(i);
                    list.add(i, mTextSizeJ);
                    list.remove(j);
                    list.add(j, mTextSizeI);
                }
            }
        }
        return list;
    }
}
