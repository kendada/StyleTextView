package com.koudai.styletextview;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;

import com.koudai.styletextview.textstyle.NoUnderlineClickableSpan;
import com.koudai.styletextview.textstyle.TextStylePhrase;
import com.koudai.styletextview.utils.AppStyleUtils;


/**
 * @auther jsk
 * @date 2018/10/26
 */
public class FlexibleRichTextView extends RichTextView implements BaseRichTextStyle.OnTagContentClickListenter {

    private OnFlexibleClickListener mOnFlexibleClickListener;

    public FlexibleRichTextView(Context context) {
        this(context, null);
    }

    public FlexibleRichTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlexibleRichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private String mBtnText; // 收起、展开
    private int mStatus; // 1. 收起 2. 展开
    private String mEllipsis = "..."; // 省略号

    private int mMaxLength = 50; // 收起的时候，最大显示60个字符


    public void setText(String contentText, int status){
        setText(contentText, false, status);
    }

    /**
     * @param contentText
     * @param isFlexible 是否支持展开、收起
     * @param status
     * */
    public void setText(String contentText, boolean isFlexible, int status){
        setContentText(contentText);

        if(isFlexible){
            setMaxLengthFilter(mMaxLength);
            if (status != 1 && status != 2){
                String content = mContentText;
                if (content.length() > mMaxLength){
                    status = 2;
                } else {
                    general();
                }
            }

            switch (status){
                case 1: // 收起
                    expand();
                    break;
                case 2: // 展开
                    takeUp();
                    break;
                default:
                    general();
                    break;
            }
        } else {
            general();
        }
    }

    /**
     * 不添加展开和收起
     * */
    private void general(){
        String content = mContentText;
        mTextStylePhrase = createTextStylePhrase(content);
        mTextStylePhrase.setForegroundColorSpan(R.color.color999999, mBtnText);

        showText();
    }

    /**
     * 展开
     * */
    private void expand(){
        mStatus = 1;
        mBtnText = getStatusText();
        String content = mContentText + mBtnText;
        int length = content.length();
        setMaxLengthFilter(length);

        setTextStylePhrase(content);
    }

    /**
     * 收起
     * */
    private void takeUp(){
        mStatus = 2;
        mBtnText = getStatusText();
        String content = mContentText;

        if (content.length() > mMaxLength - 6){
            content = content.substring(0, mMaxLength-6) + mEllipsis + mBtnText;
        } else {
            content = content + mEllipsis + mBtnText;
        }

        setMaxLengthFilter(mMaxLength);

        setTextStylePhrase(content);
    }

    private void setTextStylePhrase(String content){
        mTextStylePhrase = createTextStylePhrase(content);
        mTextStylePhrase.setForegroundColorSpan(R.color.color999999, mBtnText);
        mTextStylePhrase.setClickableSpan(mBtnText, new NoUnderlineClickableSpan() {
            @Override
            public void onClick(View widget) {
                switch (mStatus){
                    case 1:
                        takeUp();
                        break;
                    case 2:
                        expand();
                        break;
                }
                if (mOnFlexibleClickListener != null){
                    mOnFlexibleClickListener.onClick(getStatus());
                }
            }
        });

        showText();
    }

    /**
     * 显示的最大字符数量
     * */
    private void setMaxLengthFilter(int maxLengthFilter){
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthFilter)});
    }

    /**
     * 设置收起时最大显示长度
     * */
    public void setMaxLength(int maxLength){
        mMaxLength = maxLength;
    }

    /**
     * 根据状态获取 收起、展开 字符串
     * */
    private String getStatusText(){
        switch (mStatus){
            case 1:
                return " " + AppStyleUtils.getString(R.string.ys_take_up_title_txt);
            case 2:
                return " " + AppStyleUtils.getString(R.string.ys_open_title_txt);
        }
        return "";
    }

    public int getStatus(){
        return mStatus;
    }

    private BaseRichTextStyle.OnTagContentClickListenter mOnTagContentClickListenter;

    public BaseRichTextStyle.OnTagContentClickListenter getOnTagContentClickListenter() {
        return mOnTagContentClickListenter;
    }

    public void setOnTagContentClickListenter(BaseRichTextStyle.OnTagContentClickListenter onTagContentClickListenter) {
        this.mOnTagContentClickListenter = onTagContentClickListenter;
    }

    public OnFlexibleClickListener getOnFlexibleClickListener() {
        return mOnFlexibleClickListener;
    }

    public void setOnFlexibleClickListener(OnFlexibleClickListener onFlexibleClickListener) {
        this.mOnFlexibleClickListener = onFlexibleClickListener;
    }

    @Override
    public void onClick(int style, String text) {
        if (mOnTagContentClickListenter != null){
            mOnTagContentClickListenter.onClick(style, text);
        }
    }

    public interface OnFlexibleClickListener {
        void onClick(int status);
    }

    public String getContentText() {
        return mContentText;
    }

    public void setContentText(String contentText) {
        this.mContentText = contentText;
    }

    public TextStylePhrase getTextStylePhrase() {
        return mTextStylePhrase;
    }

    public void setTextStylePhrase(TextStylePhrase textStylePhrase) {
        this.mTextStylePhrase = textStylePhrase;
    }
}

