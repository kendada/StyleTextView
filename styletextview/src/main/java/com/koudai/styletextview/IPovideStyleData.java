package com.koudai.styletextview;

/**
 * @auther jsk
 * @date 2018/10/26
 */
public interface IPovideStyleData {

    int getHighlightColorId(); // 高亮颜色ID

    int getHighlightTextSize(); // 高亮字体大小

    int getTypeface(); // 字体样式 -  正常NORMAL | 加粗BOLD | 斜体ITALIC | 加粗斜体BOLD_ITALIC

    boolean isAddStyle(); // 是否添加这个样式

    String getRuleText(); // 规则

    boolean isUseRule(); // 是否是使用正则

    String getNeedHighlightText(); // 如果不使用正则表达式匹配，则使用这个字符串

    boolean isMatchOne(); // 是否只匹配一个，默认第一个

    int getMatchWhichOne(); // 匹配哪一个，在isMatchOne为true的情况下，默认第一个

    int getRichTextStyle(); // 供前台回调使用，具体区分使用的哪种

}
