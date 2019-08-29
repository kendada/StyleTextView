package com.koudai.styletextview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.koudai.styletextview.textstyle.TextStylePhrase;
import com.koudai.styletextview.utils.AvLog;
import com.koudai.styletextview.utils.MatchUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther jsk
 * @date 2019-08-29
 */
public class TopicTagEditText extends AppCompatEditText implements TextWatcher {

    public TopicTagEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(this);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    AvLog.d("onKey del：" + getText().toString());
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        return onKeyDeleteTag();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    private boolean onKeyDeleteTag(){
        String text = getText().toString();
        TextStylePhrase textStylePhrase = new TextStylePhrase(text);
        List<TextStylePhrase.TextSize> tagList = searchAllTextSize(textStylePhrase, text);

        if (tagList == null || tagList.size() == 0) {
            int lengthTrim = text.trim().length();
            if (lengthTrim == 0){
                setText("");
            }
            return false;
        }

        int selectionStart = getSelectionStart();

        AvLog.d("selectionStart = " + selectionStart);

        TextStylePhrase.TextSize targetTextSize = null;

        for (TextStylePhrase.TextSize textSize : tagList){
            int mStart = textSize.getStart();
            int mEnd = textSize.getEnd();
            if ((mEnd+1) == selectionStart){
                targetTextSize = textSize;
            } else {
                if (mStart < selectionStart && selectionStart <= mEnd){
                    targetTextSize = textSize;
                }
            }
        }

        if (targetTextSize != null){
            AvLog.d("targetTextSize = " + targetTextSize.getText() +
                    ", " + targetTextSize.getStart() +
                    ", " + targetTextSize.getEnd());

            StringBuffer stringBuffer = new StringBuffer(text);
            stringBuffer.delete(targetTextSize.getStart(), targetTextSize.getEnd());

            String mTrimText = stringBuffer.toString();
            int lengthTrim = mTrimText.trim().length();
            if (lengthTrim == 0){
                setText("");
            } else {
                setText(stringBuffer);
                int start = targetTextSize.getStart();
                setSelection(start);
            }

            if (onDelteTagListener != null){
                onDelteTagListener.onDeleteTag(targetTextSize.getText(), targetTextSize.getStart(),
                        targetTextSize.getEnd());
            }

            return true;
        } else {
            AvLog.d("targetTextSize = null");
            int lengthTrim = text.trim().length();
            if (lengthTrim == 0){
                setText("");
            }
        }

        return false;
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = getText().toString();
        if (TextUtils.isEmpty(text)){
            AvLog.d("内容为空");
            return;
        }

        removeTextChangedListener(this);

        TextStylePhrase textStylePhrase = new TextStylePhrase(text);
        List<TextStylePhrase.TextSize> tagList = searchAllTextSize(textStylePhrase, text);

        if (tagList != null && tagList.size() > 0){
            int selectionStart = getSelectionStart();
            TextStylePhrase.TextSize currentTextSize = null;
            for (TextStylePhrase.TextSize textSize : tagList) {
                textStylePhrase.setForegroundColorSpan(Color.parseColor("#9843F6"), textSize);
                if (textSize.getStart() < selectionStart && selectionStart <= textSize.getEnd()){
                    AvLog.d("selectionStart: " + selectionStart + ", textSize: " + textSize.getText());
                    currentTextSize = textSize;
                }
            }
            if (currentTextSize == null){
                AvLog.d("current textSize null");
            }else {
                AvLog.d("current textSize: " + currentTextSize.getText());
            }

            setText(textStylePhrase.getSpannableStringBuilder());
            setSelection(selectionStart);
        }else {
            int selectionStart = getSelectionStart();
            setText(text);
            setSelection(selectionStart);
        }

        addTextChangedListener(this);
    }

    private List<TextStylePhrase.TextSize> searchAllTextSize(TextStylePhrase textStylePhrase, String text){
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

    private OnDelteTagListener onDelteTagListener;

    public OnDelteTagListener getOnDelteTagListener() {
        return onDelteTagListener;
    }

    public void setOnDelteTagListener(OnDelteTagListener onDelteTagListener) {
        this.onDelteTagListener = onDelteTagListener;
    }

    public interface OnDelteTagListener{
        void onDeleteTag(String text, int start, int end);
    }

}
