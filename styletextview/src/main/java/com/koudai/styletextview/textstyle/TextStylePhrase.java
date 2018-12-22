package com.koudai.styletextview.textstyle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import com.koudai.styletextview.utils.StyleTexViewtUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Spanned.SPAN_INCLUSIVE_EXCLUSIVE 从起始下标到终了下标，包括起始下标
 * Spanned.SPAN_INCLUSIVE_INCLUSIVE 从起始下标到终了下标，同时包括起始下标和终了下标
 * Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 从起始下标到终了下标，但都不包括起始下标和终了下标
 * Spanned.SPAN_EXCLUSIVE_INCLUSIVE 从起始下标到终了下标，包括终了下标
 * @auther jsk
 * @date 2018/8/23
 */
public class TextStylePhrase {

    public int mFlags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

    private SpannableStringBuilder mSpannableStringBuilder;
    private String mCourceText;

    /**
     * @param courceText
     * */
    public TextStylePhrase(String courceText){
        mCourceText = courceText;
        mSpannableStringBuilder = new SpannableStringBuilder(courceText);
    }

    /**
     * 改变字体颜色
     * @param colorId 高亮颜色
     * @param targetText 目标字符串
     * */
    public void setForegroundColorSpan(int colorId, String targetText){
        TextSize textSize = getTextSize(targetText);
        if (textSize == null) return;
        setForegroundColorSpan(colorId, textSize.start, textSize.end);
    }

    /**
     * 改变字体颜色
     * @param colorId 高亮颜色
     * @param textSize 目标字符串信息
     * */
    public void setForegroundColorSpan(int colorId, TextSize textSize){
        if (textSize == null) return;
        setForegroundColorSpan(colorId, textSize.start, textSize.end);
    }


    /**
     * 改变字体颜色
     * @param colorId 高亮颜色
     * @param start 字符索引 - 开始
     * @param end 字符索引 - 结束
     * */
    public void setForegroundColorSpan(int colorId, int start, int end){
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getColor(colorId));
        mSpannableStringBuilder.setSpan(colorSpan, start, end, mFlags);
    }

    /**
     * 改变字体大小
     * @param size 字体大小，已转单位 px
     * */
    public void setAbsoluteSizeSpan(int size, String targetText){
        TextSize textSize = getTextSize(targetText);
        if (textSize == null) return;
        setAbsoluteSizeSpan(size, textSize.start, textSize.end);
    }


    /**
     * 改变字体大小
     * @param size 字体大小，已转单位 px
     * */
    public void setAbsoluteSizeSpan(int size, TextSize textSize){
        if (textSize == null) return;
        setAbsoluteSizeSpan(size, textSize.start, textSize.end);
    }

    /**
     * 改变字体大小
     * @param size 字体大小，已转单位 px
     * */
    public void setAbsoluteSizeSpan(int size, int start, int end){
        AbsoluteSizeSpan mAbsoluteSizeSpan = new AbsoluteSizeSpan(dip2px(size));
        mSpannableStringBuilder.setSpan(mAbsoluteSizeSpan, start, end, mFlags);
    }

    /**
     * 粗体
     * @param style {@link android.graphics.Typeface}
     * */
    public void setStyleSpan(int style, String targetText){
        TextSize textSize = getTextSize(targetText);
        if (textSize == null) return;
        setStyleSpan(style, textSize.start, textSize.end);
    }

    /**
     * 粗体
     * @param style {@link android.graphics.Typeface}
     * */
    public void setStyleSpan(int style, TextSize textSize){
        if (textSize == null) return;
        setStyleSpan(style, textSize.start, textSize.end);
    }

    /**
     * 粗体
     * @param style {@link android.graphics.Typeface}
     * */
    public void setStyleSpan(int style, int start, int end){
        StyleSpan mStyleSpan = new StyleSpan(style);
        mSpannableStringBuilder.setSpan(mStyleSpan, start, end, mFlags);
    }

    /**
     * 添加下划线
     * */
    public void setUnderlineSpan(String targetText){
        TextSize textSize = getTextSize(targetText);
        if (textSize == null) return;
        setUnderlineSpan(textSize.start, textSize.end);
    }

    /**
     * 添加下划线
     * */
    public void setUnderlineSpan(TextSize textSize){
        if (textSize == null) return;
        setUnderlineSpan(textSize.start, textSize.end);
    }

    /**
     * 添加下划线
     * */
    public void setUnderlineSpan(int start, int end){
        UnderlineSpan mUnderlineSpan = new UnderlineSpan();
        mSpannableStringBuilder.setSpan(mUnderlineSpan, start, end, mFlags);
    }

    /**
     * 添加删除线
     * */
    public void setStrikethroughSpan(String targetText){
        TextSize textSize = getTextSize(targetText);
        if (textSize == null) return;
        setStrikethroughSpan(textSize.start, textSize.end);
    }

    /**
     * 添加删除线
     * */
    public void setStrikethroughSpan(TextSize textSize){
        if (textSize == null) return;
        setStrikethroughSpan(textSize.start, textSize.end);
    }

    /**
     * 添加删除线
     * */
    public void setStrikethroughSpan(int start, int end){
        StrikethroughSpan mStrikethroughSpan = new StrikethroughSpan();
        mSpannableStringBuilder.setSpan(mStrikethroughSpan, start, end, mFlags);
    }

    /**
     * 添加点击事件
     * @param clickableSpan {@link NoUnderlineClickableSpan}
     * */
    public void setClickableSpan(String targetText, ClickableSpan clickableSpan){
        TextSize textSize = getTextSize(targetText);
        if (textSize == null) return;
        setClickableSpan(textSize.start, textSize.end, clickableSpan);
    }

    /**
     * 添加点击事件
     * @param clickableSpan {@link NoUnderlineClickableSpan}
     * */
    public void setClickableSpan(TextSize textSize, ClickableSpan clickableSpan){
        if (textSize == null) return;
        setClickableSpan(textSize.start, textSize.end, clickableSpan);
    }

    /**
     * 添加点击事件
     * @param clickableSpan {@link NoUnderlineClickableSpan}
     * */
    public void setClickableSpan(int start, int end, ClickableSpan clickableSpan){
        mSpannableStringBuilder.setSpan(clickableSpan, start, end, mFlags);
    }

    /**
     * 添加图片
     * @param drawable 图片资源
     * */
    public void setImageSpan(int start, int end, Drawable drawable){
        if (drawable == null) return;
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mSpannableStringBuilder.setSpan(new CenterImageSpan(drawable), start, end, mFlags);
    }

    /**
     * 添加图片
     * @param drawable 图片资源
     * */
    public void setImageSpan(String tag, Drawable drawable){
        if (TextUtils.isEmpty(tag)) return;
        TextSize textSize = getTextSize(tag);
        if (textSize == null) return;
        setImageSpan(textSize.start, textSize.end, drawable);
    }

    /**
     * 添加图片
     * @param drawable 图片资源
     * */
    public void setImageSpan(TextSize textSize, Drawable drawable){
        if (textSize == null) return;
        setImageSpan(textSize.start, textSize.end, drawable);
    }

    /**
     * 移除某一个Span
     * */
    public void removeSpan(Object what) {
        if (what == null || mSpannableStringBuilder == null) return;
        mSpannableStringBuilder.removeSpan(what);
    }

    /**
     * 替换文本 -- 主要是清除多次设置的Span
     * */
    public void replace(TextSize textSize){
        if (textSize == null || TextUtils.isEmpty(textSize.getText())) return;
        replace(textSize, textSize.getText());
    }
    /**
     * 替换文本 -- 主要是清除多次设置的Span -- hide
     * */
    private void replace(TextSize textSize, CharSequence text){
        if (textSize == null || text == null) return;
        replace(textSize.getStart(), textSize.getEnd(), text);
    }
    /**
     * 替换文本 -- 主要是清除多次设置的Span  -- hide
     * */
    private void replace(int start, int end, CharSequence text){
        if (mSpannableStringBuilder == null || text == null) return;
        mSpannableStringBuilder.replace(start, end, text);
    }

    /**
     * 比较相同索引位置的字符串是否相同
     * */
    public boolean isEquals(TextSize textSize){
        if (textSize == null) return false;
        return isEquals(textSize, textSize.getText());
    }

    /**
     * 比较相同索引位置的字符串是否相同 -- hide
     * */
    private boolean isEquals(TextSize textSize, CharSequence text){
        if (textSize == null) return false;
        return isEquals(textSize.getStart(), textSize.getEnd(), text);
    }

    /**
     * 比较相同索引位置的字符串是否相同 -- hide
     * */
    private boolean isEquals(int start, int end, CharSequence text){
        boolean isEquals = false;

        if (start < 0) return isEquals;

        if (TextUtils.isEmpty(mCourceText)) return isEquals;

        int mCourceTextLength = mCourceText.length();

        if (mCourceTextLength > end){
            String mTargetText = mCourceText.substring(start, end);
            isEquals = TextUtils.equals(mTargetText, text);
        }
        return isEquals;
    }


    /**
     * 获取源字符串
     * */
    public String getCourceText() {
        return mCourceText;
    }

    /**
     * 获取已经格式化的字符串
     * */
    public SpannableStringBuilder getSpannableStringBuilder(){
        return mSpannableStringBuilder;
    }

    /**
     * @param targetText 目标短语
     * @return TextSize 开始索引 & 结束索引
     * */
    public TextSize getTextSize(String targetText){
        if (TextUtils.isEmpty(mCourceText) || TextUtils.isEmpty(targetText)) return null;
        if (mCourceText.contains(targetText)){
            int start = mCourceText.indexOf(targetText);
            return new TextSize(targetText, start, start + targetText.length());
        }
        return null;
    }

    /**
     * 查找源字符串中 targetText 出现的位置情况
     * */
    public List<TextSize> searchAllTextSize(String targetText) {
        List<TextSize> list = new ArrayList<>();
        if (!mCourceText.contains(targetText))  return list;

        int start = mCourceText.indexOf(targetText);//第一个出现的索引位置
        list.add(new TextSize(targetText, start, start + targetText.length()));

        while (start != -1) {
            start = mCourceText.indexOf(targetText, start + 1);//从这个索引往后开始第一个出现的位置
            if (start != -1){
                list.add(new TextSize(targetText, start, start + targetText.length()));
            }
        }

        return list;
    }

    /**
     * 包含目标字符串在源字符串中的索引 开始 & 结束
     * */
    public static class TextSize{
        private int start;
        private int end;
        private String text;

        private TextSize(){}

        private TextSize(int start, int end){
            this(null, start, end);
        }

        private TextSize(String text, int start, int end){
            this.text = text;
            this.start = start;
            this.end = end;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getStart() {
            return start;
        }

        private void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        private void setEnd(int end) {
            this.end = end;
        }
    }

    /**
     * 比较两个TextSize是否一样
     * */
    public static boolean equals(TextStylePhrase.TextSize a, TextStylePhrase.TextSize b){
        if (a == null || b == null) return false;

        return a.start == b.start
                && a.end == b.end
                && TextUtils.equals(a.text, b.text);
    }

    /**
     * 居中显示的ImageSpan
     * */
    public static class CenterImageSpan extends ImageSpan{
        private WeakReference<Drawable> mDrawableRef;

        public CenterImageSpan(Context context, int resourceId, int verticalAlignment) {
            super(context, resourceId, verticalAlignment);
        }

        public CenterImageSpan(@NonNull Drawable d) {
            super(d);
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fontMetricsInt) {
            Drawable drawable = getDrawable();
            Rect rect = drawable.getBounds();
            if (fontMetricsInt != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.descent - fmPaint.ascent;
                int drHeight = rect.bottom - rect.top;
                int centerY = fmPaint.ascent + fontHeight / 2;

                fontMetricsInt.ascent = centerY - drHeight / 2;
                fontMetricsInt.top = fontMetricsInt.ascent;
                fontMetricsInt.bottom = centerY + drHeight / 2;
                fontMetricsInt.descent = fontMetricsInt.bottom;
            }
            return rect.right;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                         int bottom, Paint paint) {
            Drawable drawable = getCachedDrawable();
            canvas.save();
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.descent - fmPaint.ascent;
            int centerY = y + fmPaint.descent - fontHeight / 2;
            int transY = centerY - (drawable.getBounds().bottom - drawable.getBounds().top) / 2;
            canvas.translate(x, transY);
            drawable.draw(canvas);
            canvas.restore();
        }

        private Drawable getCachedDrawable() {
            WeakReference<Drawable> wr = mDrawableRef;
            Drawable d = null;
            if (wr != null) {
                d = wr.get();
            }

            if (d == null) {
                d = getDrawable();
                mDrawableRef = new WeakReference<>(d);
            }

            return d;
        }

    }

    /**
     * 根据ID获取颜色
     * @param colorId
     * */
    public int getColor(int colorId) {
        return StyleTexViewtUtils.getColor(colorId);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        Resources mResources = StyleTexViewtUtils.getResources();
        final float scale;
        if (mResources != null){
            scale = mResources.getDisplayMetrics().density;;
        } else {
            scale = 1;
        }
        return (int) (dpValue * scale + 0.5f);
    }

}
