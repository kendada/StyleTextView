package com.koudai.styletextview.styledata;

import android.text.TextUtils;

import com.koudai.styletextview.BaseSpannableTextView;
import com.koudai.styletextview.RichTextView;
import com.koudai.styletextview.textstyle.TextStylePhrase;

/**
 * @auther jsk
 * @date 2018/12/24
 */
public class DefaultTextStylePhraseAgentImp implements ITextStylePhraseAgent{

    public DefaultTextStylePhraseAgentImp(){}

    public DefaultTextStylePhraseAgentImp(RichTextView.IExternalStylePhraseData iExternalStylePhraseData){
        this.mIExternalStylePhraseData = iExternalStylePhraseData;
    }

    @Override
    public TextStylePhrase createTextStylePhrase(String content) {
        if (mIExternalStylePhraseData != null && mIExternalStylePhraseData.getExternalStylePhrase() != null){
            if (TextUtils.equals(content, mIExternalStylePhraseData.getExternalStylePhrase().getCourceText())){
                return mIExternalStylePhraseData.getExternalStylePhrase();
            }
        }
        return new TextStylePhrase(content);
    }

    @Override
    public void setExternalStylePhraseData(BaseSpannableTextView.IExternalStylePhraseData externalStylePhraseData) {
        this.mIExternalStylePhraseData = externalStylePhraseData;
    }

    public RichTextView.IExternalStylePhraseData mIExternalStylePhraseData;

    public RichTextView.IExternalStylePhraseData getIExternalStylePhraseData() {
        return mIExternalStylePhraseData;
    }

}
