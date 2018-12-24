package com.koudai.styletextview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.koudai.styletextview.textstyle.TextStylePhrase;

/**
 * @auther jsk
 * @date 2018/10/16
 */
public abstract class BaseSpannableTextView extends AppCompatTextView {

    private LinkTouchMovementMethod mLinkTouchMovementMethod;

    public BaseSpannableTextView(Context context) {
        this(context, null);
    }

    public BaseSpannableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSpannableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLinkTouchMovementMethod(LinkTouchMovementMethod linkTouchMovementMethod) {
        mLinkTouchMovementMethod = linkTouchMovementMethod;
        if (mLinkTouchMovementMethod != null){
            setMovementMethod(mLinkTouchMovementMethod);
        }
    }

    public abstract void showText();

    public TextStylePhrase createTextStylePhrase(String content) {
        return new TextStylePhrase(content);
    }

    public IExternalStylePhraseData mIExternalStylePhraseData;

    public IExternalStylePhraseData getIExternalStylePhraseData() {
        return mIExternalStylePhraseData;
    }

    public void setExternalStylePhraseData(IExternalStylePhraseData iExternalStylePhraseData) {
        this.mIExternalStylePhraseData = iExternalStylePhraseData;
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
