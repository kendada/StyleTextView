package com.koudai.styletextview.textstyle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
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
 *
 * 以下待整理：
 * 1. URLSpan 文本超链接 2018-12-24 完成
 * 2. SuggestionSpan 相当于占位符，一般输入框使用
 * 3. DynamicDrawableSpan 设置图片，基于文本基线或底部对齐
 * 4. RelativeSizeSpan 相对大小（文本字体）
 * 5. ScaleXSpan 基于x轴缩放
 * 6. TextAppearanceSpan 文本外貌（包括字体、大小、样式和颜色）
 * 7. TypefaceSpan 文本字体
 *
 * 8. RasterizerSpan 光栅效果
 * 9. TabStopSpan 制表位偏移样式，距离每行的leading margin的偏移量，据测试在首行加入制表符时才产生效果
 *
 * 10. MetricAffectingSpan 父类，一般不用
 * 11. ReplacementSpan 父类，一般不用
 *
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
     * 设置上标
     * @param targetText 注意目标字符的唯一性
     * */
    public void setSuperscriptSpan(String targetText){
        if (TextUtils.isEmpty(targetText)) return;
        setSuperscriptSpan(getTextSize(targetText));
    }

    /**
     * 设置上标
     * */
    public void setSuperscriptSpan(TextSize textSize){
        if (textSize == null) return;
        setSuperscriptSpan(textSize.start, textSize.end);
    }

    /**
     * 设置上标
     * */
    public void setSuperscriptSpan(int start, int end){
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        mSpannableStringBuilder.setSpan(superscriptSpan, start, end, mFlags);
    }

    /**
     * 设置下标
     * @param targetText 注意目标字符的唯一性
     * */
    public void setSubscriptSpan(String targetText){
        if (TextUtils.isEmpty(targetText)) return;
        setSubscriptSpan(getTextSize(targetText));
    }

    /**
     * 设置下标
     * */
    public void setSubscriptSpan(TextSize textSize){
        if (textSize == null) return;
        setSubscriptSpan(textSize.start, textSize.end);
    }

    /**
     * 设置下标
     * */
    public void setSubscriptSpan(int start, int end){
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        mSpannableStringBuilder.setSpan(superscriptSpan, start, end, mFlags);
    }

    /**
     * 设置字体字体背景颜色
     * */
    public void setBackgroundColorSpan(int colorId, String targetText){
        if (TextUtils.isEmpty(targetText)) return;
        setBackgroundColorSpan(colorId, getTextSize(targetText));
    }

    /**
     * 设置字体字体背景颜色
     * */
    public void setBackgroundColorSpan(int colorId, TextSize textSize){
        if (textSize == null) return;
        setBackgroundColorSpan(colorId, textSize.start, textSize.end);
    }

    /**
     * 设置字体字体背景颜色
     * */
    public void setBackgroundColorSpan(int colorId, int start, int end){
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(getColor(colorId));
        mSpannableStringBuilder.setSpan(backgroundColorSpan, start, end, mFlags);
    }

    /**
     * 设置URLSpan
     * */
    public void setURLSpan(String url, String targetText){
        if (TextUtils.isEmpty(targetText)) return;
        TextSize textSize = getTextSize(targetText);

        setURLSpan(url, textSize);
    }
    /**
     * 设置URLSpan
     * */
    public void setURLSpan(String url, TextSize textSize){
        if (textSize == null) return;
        setURLSpan(url, textSize.start, textSize.end);
    }

    /**
     * 设置URLSpan
     * */
    public void setURLSpan(String url, int start, int end){
        if (TextUtils.isEmpty(url)) return;
        URLSpan urlSpan = new URLSpan(url);
        mSpannableStringBuilder.setSpan(urlSpan, start, end, mFlags);
    }

    /**
     * 设置模糊效果
     * */
    public void setBlurMaskFilterSpan(float radius, BlurMaskFilter.Blur style, String targetText){
        if (TextUtils.isEmpty(targetText)) return;
        TextSize textSize = getTextSize(targetText);

        if (textSize == null) return;

        MaskFilter filter = new BlurMaskFilter(radius, style);
        setMaskFilterSpan(filter, textSize.start, textSize.end);
    }

    /**
     * 设置模糊效果
     * */
    public void setBlurMaskFilterSpan(float radius, BlurMaskFilter.Blur style, TextSize textSize){
        if (textSize == null) return;
        MaskFilter filter = new BlurMaskFilter(radius, style);
        setMaskFilterSpan(filter, textSize.start, textSize.end);
    }

    /**
     * 设置模糊效果
     * @param radius 指定要模糊的范围，必须大于0
     * @param style Normal 对应物体边界的内部和外部都将进行模糊
     *              SOLID 图像边界外产生一层与Paint颜色一致阴影效果，不影响图像的本身
     *              OUTER 图像边界外产生一层阴影，图像内部镂空
     *              INNER 在图像边界内部产生模糊效果，外部不绘制
     * */
    public void setBlurMaskFilterSpan(float radius, BlurMaskFilter.Blur style, int start, int end){
        MaskFilter filter = new BlurMaskFilter(radius, style);
        setMaskFilterSpan(filter, start, end);
    }

    /**
     * 设置浮雕效果
     * */
    public void setEmbossMaskFilterSpan(float[] direction, float ambient, float specular, float blurRadius, String targetText){
        if (TextUtils.isEmpty(targetText)) return;

        TextSize textSize = getTextSize(targetText);
        if (textSize == null) return;

        setEmbossMaskFilterSpan(direction, ambient, specular, blurRadius, textSize.start, textSize.end);
    }

    /**
     * 设置浮雕效果
     * */
    public void setEmbossMaskFilterSpan(float[] direction, float ambient, float specular, float blurRadius, TextSize textSize){
        if (textSize == null) return;
        setEmbossMaskFilterSpan(direction, ambient, specular, blurRadius, textSize.start, textSize.end);
    }

    /**
     * 设置浮雕效果
     * @param direction 是一个含有三个float元素的数组，对应x、y、z三个方向上的值；用于指定光源方向
     * @param ambient 环境光的因子 （0~1），0~1表示从暗到亮
     * @param specular 镜面反射系数，越接近0，反射光越强
     * @param blurRadius 模糊半径，值越大，模糊效果越明显
     * */
    public void setEmbossMaskFilterSpan(float[] direction, float ambient, float specular, float blurRadius, int start, int end){
        MaskFilter filter = new EmbossMaskFilter(direction, ambient, specular, blurRadius);
        setMaskFilterSpan(filter, start, end);
    }

    public void setMaskFilterSpan(MaskFilter maskFilter, int start, int end){
        if (maskFilter == null) return;
        MaskFilterSpan maskFilterSpan=new MaskFilterSpan(maskFilter);
        mSpannableStringBuilder.setSpan(maskFilterSpan, start, end, mFlags);
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
     * 是否包含外部创建的文本
     * */
    public boolean contains(TextSize textSize){
        if (textSize == null) return false;

        if (TextUtils.isEmpty(mCourceText)) return false;

        boolean indexBool = textSize.start >= 0 && textSize.end <= mCourceText.length();

        if (TextUtils.isEmpty(textSize.text)){
            return indexBool;
        }

        return mCourceText.contains(textSize.text) && indexBool;
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

    public final static class Builder {

        String source = null;
        int start = -1;
        int end = -1;
        Object tag = null;

        private Builder(){}

        public static Builder obtain(int start, int end){
            return obtain(null, start, end, null);
        }

        public static Builder obtain(String source, int start, int end){
            return obtain(source, start, end, null);
        }

        public static Builder obtain(String source, int start, int end, Object tag){
            Builder mBuilder = new Builder();

            mBuilder.source = source;
            mBuilder.start = start;
            mBuilder.end = end;
            mBuilder.tag = tag;

            return mBuilder;
        }

        public TextSize getTextSize(){
            if (start == -1 || end == -1){
                return null;
            }
            return new TextSize(source, start, end, tag);
        }
    }

    /**
     * 包含目标字符串在源字符串中的索引 开始 & 结束
     * */
    public static class TextSize{
        private int start;
        private int end;
        private String text;
        private Object tag; // 作为一种数据扩展

        private TextSize(){}

        private TextSize(int start, int end){
            this(null, start, end, null);
        }

        private TextSize(String text, int start, int end){
            this(text, start, end, null);
        }

        private TextSize(String text, int start, int end, Object tag){
            this.text = text;
            this.start = start;
            this.end = end;
            this.tag = tag;
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

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
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
     * TextSize排序 - 从小到大
     * */
    public static List<TextStylePhrase.TextSize> sortByStart(List<TextStylePhrase.TextSize> list){
        for (int i=0; i<list.size(); i++){
            for (int j = i + 1; j < list.size(); j++){
                TextStylePhrase.TextSize mTextSizeI = list.get(i);
                TextStylePhrase.TextSize mTextSizeJ = list.get(j);
                if (mTextSizeI.getStart() > mTextSizeJ.getStart()) {
                    list.remove(i);
                    list.add(i, mTextSizeJ);
                    list.remove(j);
                    list.add(j, mTextSizeI);
                }
            }
        }
        return list;
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
