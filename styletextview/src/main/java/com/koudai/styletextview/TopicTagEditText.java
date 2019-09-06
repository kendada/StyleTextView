package com.koudai.styletextview;

import android.content.Context;
import android.util.AttributeSet;

import com.koudai.styletextview.textstyle.TextStylePhrase;
import com.koudai.styletextview.utils.MatchUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther jsk
 * @date 2019-08-29
 */
public class TopicTagEditText extends BaseEditText {

    public TopicTagEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyDeleteTag() {
        return super.onKeyDeleteTag();
    }

    @Override
    public int getForegroundColor() {
        return super.getForegroundColor();
    }

    @Override
    public List<TextStylePhrase.TextSize> searchAllTextSize(TextStylePhrase textStylePhrase, String text){
        List<String> list = MatchUtils.matchTargetText(text, MatchUtils.PATTERN_TOWNTALK);
        if (list == null || list.size() == 0) return null;

        List<TextStylePhrase.TextSize> textSizeList = new ArrayList<>();
        for (String tagText : list){
            List<TextStylePhrase.TextSize> tagList = textStylePhrase.searchAllTextSize(tagText);
            if (tagList != null && tagList.size() > 0){
                textSizeList.addAll(tagList);
            }
        }

        return textSizeList;
    }

}
