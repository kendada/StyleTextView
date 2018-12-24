package com.koudai.styletextview.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.koudai.styletextview.R;
import com.koudai.styletextview.textstyle.NoUnderlineClickableSpan;
import com.koudai.styletextview.textstyle.TextStylePhrase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @auther jsk
 * @date 2018/12/24
 */
public class WeiBoUrlLinkTextStyleUtils {

    public final static String M_REPLACE_TEXT = "网页链接";

    private String mSourceText;
    private String mLinkText = M_REPLACE_TEXT;
    private int mHighlightColorId = R.color.color0084FB;
    private Pattern mWebUrlPattern = MatchUtils.webUrlPattern;

    public WeiBoUrlLinkTextStyleUtils(String sourceText){
        this(sourceText, M_REPLACE_TEXT);
    }

    public WeiBoUrlLinkTextStyleUtils(String sourceText, String linkText){
        this.mSourceText = sourceText;
        if (!TextUtils.isEmpty(linkText)){ // 必须保证替换文案有内容
            this.mLinkText = linkText;
        }
    }

    public String getLinkText() {
        return mLinkText;
    }

    public void setLinkText(String linkText) {
        if (!TextUtils.isEmpty(linkText)){
            this.mLinkText = linkText;
        }
    }

    public int getHighlightColorId() {
        return mHighlightColorId;
    }

    public void setHighlightColorId(int highlightColorId) {
        if (highlightColorId > 0){
            this.mHighlightColorId = highlightColorId;
        }
    }

    public Pattern getWebUrlPattern() {
        return mWebUrlPattern;
    }

    public void setWebUrlPattern(Pattern webUrlPattern) {
        if (webUrlPattern != null){
            this.mWebUrlPattern = webUrlPattern;
        }
    }

    public TextStylePhrase getLikeWeiboLinkTextStylePhrase(){
        // 执行替换逻辑
        List<TextStylePhrase.TextSize> mMergeResultList = doReplaceText();
        if (mMergeResultList == null || mMergeResultList.size() == 0) return null;
        int mMergeResultSize = mMergeResultList.size();
        AvLog.d_bug("mMergeResultSize = " + mMergeResultSize);

        TextStylePhrase mDisposeTextStylePhrase = new TextStylePhrase(mSourceText);
        List<TextStylePhrase.TextSize> mDisposeTextSizeList = mDisposeTextStylePhrase.searchAllTextSize(mLinkText);
        int mDisposeSize = mDisposeTextSizeList.size();
        AvLog.d_bug("mDisposeSize = " + mDisposeSize);

        if (mDisposeSize != mMergeResultSize) return null;

        for (int i=0; i<mDisposeSize; i++) {
            TextStylePhrase.TextSize textSize = mDisposeTextSizeList.get(i);
            final TextStylePhrase.TextSize mMergeResultTextSize = mMergeResultList.get(i);
            if (!TextUtils.equals(mLinkText, mMergeResultTextSize.getText())){
                mDisposeTextStylePhrase.setForegroundColorSpan(mHighlightColorId, textSize);
                mDisposeTextStylePhrase.setClickableSpan(textSize, new NoUnderlineClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        if (mOnLikeWeiboLinkTextClickListener != null){
                            mOnLikeWeiboLinkTextClickListener.onClick(mMergeResultTextSize.getText(), mMergeResultTextSize);
                        }
                    }
                });
            }
        }

        return mDisposeTextStylePhrase;
    }

    /**
     * 替换Url字符串
     * */
    private List<TextStylePhrase.TextSize> doReplaceText(){
        TextStylePhrase mTextStylePhrase = new TextStylePhrase(mSourceText);
        // 原有目标mReplaceText
        List<TextStylePhrase.TextSize> mReplaceTextSizeOriginalList = mTextStylePhrase.searchAllTextSize(mLinkText);

        List<String> mURList = MatchUtils.matchTargetText(mSourceText, mWebUrlPattern);
        if (mURList == null) return null;

        int mURLSize = mURList.size();
        if (mURLSize == 0) return null;
        AvLog.d_bug("mURLSize = " + mURLSize);

        List<TextStylePhrase.TextSize> mAllTextSizeList = new ArrayList<>();
        for (String urlStr : mURList){
            AvLog.d_bug("urlStr = " + urlStr);
            List<TextStylePhrase.TextSize> mTextSizeList = mTextStylePhrase.searchAllTextSize(urlStr);
            if (mTextSizeList != null && mTextSizeList.size() > 0){
                mAllTextSizeList.addAll(mTextSizeList);
            }
        }

        if (mAllTextSizeList == null || mAllTextSizeList.size() == 0) return null;
        List<TextStylePhrase.TextSize> mMergeList = new ArrayList<>();
        mMergeList.addAll(mReplaceTextSizeOriginalList);
        mMergeList.addAll(mAllTextSizeList);

        // 排序 -- 原有"网页链接"和长网址
        List<TextStylePhrase.TextSize> mMergeResultList = sortByStart(mMergeList);

        // 替换长网址链接为"网页链接"
        for (TextStylePhrase.TextSize textSize : mAllTextSizeList) {
            mSourceText = mSourceText.replace(textSize.getText(), mLinkText);
        }

        return mMergeResultList;
    }

    public interface OnLikeWeiboLinkTextClickListener{
        void onClick(String text, TextStylePhrase.TextSize textSize);
    }

    private OnLikeWeiboLinkTextClickListener mOnLikeWeiboLinkTextClickListener;

    public void setOnLikeWeiboLinkTextClickListener(OnLikeWeiboLinkTextClickListener listener) {
        this.mOnLikeWeiboLinkTextClickListener = listener;
    }

    /**
     * 排序 - 从小到大
     * */
    private List<TextStylePhrase.TextSize> sortByStart(List<TextStylePhrase.TextSize> list){
        try {
            return TextStylePhrase.sortByStart(list);
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
