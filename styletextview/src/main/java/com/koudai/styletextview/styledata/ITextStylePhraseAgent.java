package com.koudai.styletextview.styledata;

import com.koudai.styletextview.BaseSpannableTextView;
import com.koudai.styletextview.textstyle.TextStylePhrase;

/**
 * @auther jsk
 * @date 2018/12/24
 */
public interface ITextStylePhraseAgent {

    /**
     * 创建TextStylePhrase
     * */
    TextStylePhrase createTextStylePhrase(String content);

    /**
     * 设置外部样式
     * 注意：此处是为了兼容之前的版本，引用处不需要更改代码，后期随时移除；需要使用设置外部样式，请使用createTextStylePhrase进行创建
     * */
    void setExternalStylePhraseData(BaseSpannableTextView.IExternalStylePhraseData iExternalStylePhraseData);

}
