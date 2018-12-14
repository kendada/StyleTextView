package com.koudai.styletextview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.koudai.styletextview.styledata.IPovideStyleData;
import com.koudai.styletextview.textstyle.NoUnderlineClickableSpan;
import com.koudai.styletextview.textstyle.TextStylePhrase;
import com.koudai.styletextview.utils.AvLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 富文本TextView
 * @auther jsk
 * @date 2018/10/26
 *
 * 1. 先说说匹配用户名在微博系统的文本中，用户名有以下特点：
 *    以@开头以空格、冒号、斜杠/ 结束不以@结束以@开头这很简单，
 *    关键是结束，有多种情况：微博和评论中主动@别人，此时以空格结束、或以行尾结束转发中或评论中回复，
 *    此时以冒号结束转发时在转发内容中主动@别人，此时最后一个@的用户名以 斜杠/ 结束转发时转发文本中的@，
 *    可能超过140字限制，此时用户手动删除超出的字符，用户名可能以行尾结束考虑上述条件，
 *    匹配 @ 后面紧跟不包含上述符号的字符串并不以@结束即可：
 *    /@([^\s|\/|:|@]+)/
 *
 * 2. 再说匹配话题话题匹配就简单多了，必定是以#开头，并以#结束【暂定】。
 *    /#[^#]+#/
 */
public class RichTextView extends BaseSpannableTextView implements BaseRichTextStyle.OnTagContentClickListenter{

    private List<IPovideStyleData> mIPovideStyleDatas = new ArrayList<>();

    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void removeIPovideStyleData(IPovideStyleData iPovideStyleData){
        mIPovideStyleDatas.remove(iPovideStyleData);
    }

    public void removeAllIPovideStyleData(){
        mIPovideStyleDatas.clear();
    }

    public void addRichTextStyle(IPovideStyleData iPovideStyleData){
        mIPovideStyleDatas.add(iPovideStyleData);
    }

    public void addRichTextStyles(List<IPovideStyleData> list){
        if (list == null || list.size() == 0) return;
        mIPovideStyleDatas.addAll(list);
    }

    public void setRichTextStyle(){
        for (IPovideStyleData styleData : mIPovideStyleDatas){
            BaseRichTextStyle baseRichTextStyle = createRichTextStyle(styleData);
            baseRichTextStyle.setRichTextStyle();
        }
    }

    protected String mContentText; // 内容
    protected TextStylePhrase mTextStylePhrase;


    public void setContentText(String contentText){
        mContentText = contentText;
        mTextStylePhrase = createTextStylePhrase(contentText);
    }

    /**
     * 生成TextStylePhrase
     * */
    public TextStylePhrase createTextStylePhrase(String content){
        if (mIExternalStylePhraseData != null && mIExternalStylePhraseData.getExternalStylePhrase() != null){
            if (TextUtils.equals(content, mIExternalStylePhraseData.getExternalStylePhrase().getCourceText())){
                return mIExternalStylePhraseData.getExternalStylePhrase();
            }
        }
        return new TextStylePhrase(content);
    }

    private BaseRichTextStyle createRichTextStyle(IPovideStyleData iPovideStyleData){
        return new DefalutBaseRichTextStyle(mContentText, mTextStylePhrase, iPovideStyleData);
    }

    public void showText(){
        setRichTextStyle();
        setText(mTextStylePhrase.getSpannableStringBuilder());
        setLinkTouchMovementMethod(LinkTouchMovementMethod.getInstance());
    }

    private BaseRichTextStyle.OnTagContentClickListenter mOnTagContentClickListenter;

    @Override
    public void onClick(int style, String text) {
        if (mOnTagContentClickListenter != null){
            mOnTagContentClickListenter.onClick(style, text);
        }
    }

    private class DefalutBaseRichTextStyle extends BaseRichTextStyle{

        public IPovideStyleData mIPovideStyleData;

        public DefalutBaseRichTextStyle(String content, TextStylePhrase textStylePhrase, IPovideStyleData iPovideStyleData) {
            super(content, textStylePhrase);
            mIPovideStyleData = iPovideStyleData;
        }

        @Override
        public int getRichTextStyle() {
            return mIPovideStyleData.getRichTextStyle();
        }

        @Override
        public void setRichTextStyle() {
            if (!mIPovideStyleData.isAddStyle()) return;
            if (mIPovideStyleData.isUseRule()){
                matchByRule();
            } else {
                matchSingleKey();
            }
        }

        /**
         * 匹配单个key
         * */
        private void matchSingleKey(){
            String mNeedHighlightText = mIPovideStyleData.getNeedHighlightText();
            if (TextUtils.isEmpty(mNeedHighlightText)) return;

            List<TextStylePhrase.TextSize> textSizeList = new ArrayList<>();

            List<TextStylePhrase.TextSize> list = mTextStylePhrase.searchAllTextSize(mNeedHighlightText);

            if (mIPovideStyleData.isMatchOne()){
                textSizeList.clear();
                int mMatchWhichOne = mIPovideStyleData.getMatchWhichOne();
                if(list != null && list.size() > 0) {
                    if (mMatchWhichOne < list.size()){
                        textSizeList.add(list.get(mMatchWhichOne));
                    } else {
                        textSizeList.add(list.get(0));
                    }
                }
            } else {
                textSizeList.clear();
                textSizeList.addAll(list);
            }
            setRichStyle(textSizeList);
        }

        /**
         * 通过正则表达式匹配
         * */
        private void matchByRule(){
            // 获取需要剔除的字符串TextSize
            TextStylePhrase.TextSize mExcludeMatchTextSize = getExcludeMatchTextSize(mIPovideStyleData);

            List<String> mentionUserList = matchTargetText(mContent, Pattern.compile(mIPovideStyleData.getRuleText()));

            for (final String mentionUserText : mentionUserList){
                List<TextStylePhrase.TextSize> textSizeList = mTextStylePhrase.searchAllTextSize(mentionUserText);
                // 移除需要剔除的TextSize
                removeExcludeMatchTextSize(mExcludeMatchTextSize, textSizeList);
                setRichStyle(textSizeList);
            }
        }

        private void setRichStyle(List<TextStylePhrase.TextSize> textSizeList){
            for (final TextStylePhrase.TextSize textSize : textSizeList){
                if (textSize == null) continue;
                AvLog.d_bug("textSize - "
                        + "textSize.getStart() = " + textSize.getStart()
                        + " , textSize.getEnd() = " + textSize.getEnd()
                        + " , textSize.getText() = " + textSize.getText());
                mTextStylePhrase.replace(textSize);
                mTextStylePhrase.setForegroundColorSpan(mIPovideStyleData.getHighlightColorId(), textSize);
                mTextStylePhrase.setStyleSpan(mIPovideStyleData.getTypeface(), textSize);
                if (mIPovideStyleData.getHighlightTextSize() > 0){
                    mTextStylePhrase.setAbsoluteSizeSpan(mIPovideStyleData.getHighlightTextSize(), textSize);
                }
                mTextStylePhrase.setClickableSpan(textSize, new NoUnderlineClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        RichTextView.this.onClick(getRichTextStyle(), textSize.getText());
                    }
                });
            }
        }
    }

    /**
     * 获取需要剔除的字符串TextSize
     * */
    private TextStylePhrase.TextSize getExcludeMatchTextSize(IPovideStyleData iPovideStyleData){
        if (iPovideStyleData == null) return null;

        String mExcludeMatchText = iPovideStyleData.getExcludeMatchText();
        int mExcludeMatchWhichOne = iPovideStyleData.getExcludeMatchWhichOne();

        TextStylePhrase.TextSize excludeMatchTextSize = null;

        if (!TextUtils.isEmpty(mExcludeMatchText)){
            List<TextStylePhrase.TextSize> mSearchAllTextSize = mTextStylePhrase.searchAllTextSize(mExcludeMatchText);
            if (mSearchAllTextSize.size() > mExcludeMatchWhichOne && mExcludeMatchWhichOne >= 0){
                excludeMatchTextSize = mSearchAllTextSize.get(mExcludeMatchWhichOne);
            }
            mSearchAllTextSize.clear();
        }

        return excludeMatchTextSize;
    }

    /**
     * 移除需要剔除的TextSize
     * */
    private void removeExcludeMatchTextSize(TextStylePhrase.TextSize needRemoveTextSize,
                                            List<TextStylePhrase.TextSize> textSizeList){
        if (needRemoveTextSize == null || textSizeList == null || textSizeList.size() == 0) return;

        Iterator<TextStylePhrase.TextSize> it = textSizeList.iterator();
        while (it.hasNext()){
            TextStylePhrase.TextSize textSize = it.next();
            if (TextStylePhrase.equals(needRemoveTextSize, textSize)){
                it.remove();
            }
        }
    }

    public BaseRichTextStyle.OnTagContentClickListenter getOnTagContentClickListenter() {
        return mOnTagContentClickListenter;
    }

    public void setOnTagContentClickListenter(BaseRichTextStyle.OnTagContentClickListenter onTagContentClickListenter) {
        this.mOnTagContentClickListenter = onTagContentClickListenter;
    }

    public IExternalStylePhraseData mIExternalStylePhraseData;

    public IExternalStylePhraseData getIExternalStylePhraseData() {
        return mIExternalStylePhraseData;
    }

    public void setExternalStylePhraseData(IExternalStylePhraseData externalStylePhraseData) {
        this.mIExternalStylePhraseData = externalStylePhraseData;
    }

    public interface IExternalStylePhraseData {
        TextStylePhrase getExternalStylePhrase();
    }

    public static class ExternalStylePhraseDataDefault implements IExternalStylePhraseData{

        private TextStylePhrase mTextStylePhrase;

        public ExternalStylePhraseDataDefault(TextStylePhrase textStylePhrase){
            mTextStylePhrase = textStylePhrase;
        }

        @Override
        public TextStylePhrase getExternalStylePhrase() {
            return mTextStylePhrase;
        }
    }

}
