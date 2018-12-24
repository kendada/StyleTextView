package com.koudai.styletextview;

import android.content.Context;
import android.util.AttributeSet;

import com.koudai.styletextview.textstyle.TextStylePhrase;
import com.koudai.styletextview.utils.WeiBoUrlLinkTextStyleUtils;

import java.util.regex.Pattern;

/**
 * 类似微博长网址URL替换为固定文案"网页链接"
 * 注意，此类不支持外部拓展TextStylePhrase
 * @auther jsk
 * @date 2018/12/24
 */
public class WeiBoUrlLinkRichTextView extends FlexibleRichTextView{

    private String mLinkText;
    private int mHighlightColorId = -1;
    private Pattern mWebUrlPattern = null;

    public WeiBoUrlLinkRichTextView(Context context) {
        this(context, null, 0);
    }

    public WeiBoUrlLinkRichTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeiBoUrlLinkRichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getLinkText() {
        return mLinkText;
    }

    public void setLinkText(String linkText) {
        this.mLinkText = linkText;
    }

    public int getHighlightColorId() {
        return mHighlightColorId;
    }

    public void setHighlightColorId(int highlightColorId) {
        this.mHighlightColorId = highlightColorId;
    }

    public Pattern getWebUrlPattern() {
        return mWebUrlPattern;
    }

    public void setWebUrlPattern(Pattern webUrlPattern) {
        this.mWebUrlPattern = webUrlPattern;
    }

    @Override
    public TextStylePhrase createTextStylePhrase(String content) {
        WeiBoUrlLinkTextStyleUtils mWeiBoUrlLinkTextStyleUtils = new WeiBoUrlLinkTextStyleUtils(content);

        mWeiBoUrlLinkTextStyleUtils.setLinkText(mLinkText); // 设置替换文案
        mWeiBoUrlLinkTextStyleUtils.setHighlightColorId(mHighlightColorId); // 设置高亮颜色
        mWeiBoUrlLinkTextStyleUtils.setWebUrlPattern(mWebUrlPattern); // 设置匹配规则
        mWeiBoUrlLinkTextStyleUtils.setOnLikeWeiboLinkTextClickListener(mOnLikeWeiboLinkTextClickListener); // 设置点击事件

        TextStylePhrase textStylePhrase = mWeiBoUrlLinkTextStyleUtils.getLikeWeiboLinkTextStylePhrase();
        if (textStylePhrase != null){
            return textStylePhrase;
        }
        return new TextStylePhrase(content);

    }

    private WeiBoUrlLinkTextStyleUtils.OnLikeWeiboLinkTextClickListener mOnLikeWeiboLinkTextClickListener;

    public WeiBoUrlLinkTextStyleUtils.OnLikeWeiboLinkTextClickListener getOnLikeWeiboLinkTextClickListener() {
        return mOnLikeWeiboLinkTextClickListener;
    }

    public void setOnLikeWeiboLinkTextClickListener(WeiBoUrlLinkTextStyleUtils.OnLikeWeiboLinkTextClickListener listener) {
        this.mOnLikeWeiboLinkTextClickListener = listener;
    }
}
