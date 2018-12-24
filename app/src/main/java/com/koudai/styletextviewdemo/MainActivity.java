package com.koudai.styletextviewdemo;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.koudai.styletextview.BaseRichTextStyle;
import com.koudai.styletextview.FlexibleRichTextView;
import com.koudai.styletextview.RichTextView;
import com.koudai.styletextview.styledata.imp.MentionUserPovideStyleData;
import com.koudai.styletextview.styledata.imp.TextProideStyleData;
import com.koudai.styletextview.styledata.imp.TownTalkPovideStyleData;
import com.koudai.styletextview.styledata.imp.WebUrlPovideStyleData;
import com.koudai.styletextview.textstyle.NoUnderlineClickableSpan;
import com.koudai.styletextview.textstyle.TextStylePhrase;
import com.koudai.styletextview.utils.AvLog;
import com.koudai.styletextview.utils.MatchUtils;
import com.koudai.styletextviewdemo.styledata.NameProideStyleData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private TextView mSpecialTtTextView;
    private RichTextView mSpecialTextView;
    private FlexibleRichTextView mFlexibleRichTextView;
    private TextView mContentView;
    private RichTextView mImageTextView;
    private TextView mDownViewUp;
    private TextView mWeiboLinkViewF;

    private int mStatus = 1; // 默认展开

    private String sourceText = "《易水歌》风萧萧兮易水寒，#V#壮士一去兮不复返";
    private String vTag = "#V#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        mContentView = findViewById(R.id.content_view);
        mFlexibleRichTextView = findViewById(R.id.rich_text_view);
        mSpecialTextView = findViewById(R.id.special_text_view);
        mSpecialTtTextView = findViewById(R.id.special_tt_text_view);
        mImageTextView = findViewById(R.id.image_text_view);
        mDownViewUp = (TextView) findViewById(R.id.up_down_view);
        mWeiboLinkViewF = (TextView) findViewById(R.id.f_weibo_link_view);
    }

    private void initData() {
        showImageTextView();
        testStylePhrase();
        showFlexibleRichTextView();
        showSpecialTextView();
        showSpecialTTTextView();
        setDownUpView();
        setWeiboLinkView("点击查看");
    }

    private void setWeiboLinkView(String linkText){
        String mReplaceText = "网页链接";
        if (!TextUtils.isEmpty(linkText)){
            mReplaceText = linkText;
        }

        String text = "这个是类似微博替换长网址为网页链接的实现测试，"
                +
                "https://www.oschina.net/news/102982/zentao-11-0-released"
                +
                "这个是长网址，需要转换为网页链接。"
                +
                "https://www.juzimi.com/ju/924652?juzipic=jezomr4"
                + "再来一个长网址："
                + "https://www.juzimi.com/ju/924652?juzipic=jezomr4"
                +"。"
                ;

        TextStylePhrase mTextStylePhrase = new TextStylePhrase(text);
        // 原有目标mReplaceText
        List<TextStylePhrase.TextSize> mReplaceTextSizeOriginalList = mTextStylePhrase.searchAllTextSize(mReplaceText);

        List<String> mURList = MatchUtils.matchTargetText(text, MatchUtils.webUrlPattern);
        if (mURList == null) return;

        int mURLSize = mURList.size();
        if (mURLSize == 0) return;
        AvLog.d_bug("mURLSize = " + mURLSize);

        List<TextStylePhrase.TextSize> mAllTextSizeList = new ArrayList<>();
        for (String urlStr : mURList){
            AvLog.d_bug("urlStr = " + urlStr);
            List<TextStylePhrase.TextSize> mTextSizeList = mTextStylePhrase.searchAllTextSize(urlStr);
            if (mTextSizeList != null && mTextSizeList.size() > 0){
                mAllTextSizeList.addAll(mTextSizeList);
            }
        }

        if (mAllTextSizeList == null || mAllTextSizeList.size() == 0) return;
        List<TextStylePhrase.TextSize> mMergeList = new ArrayList<>();
        mMergeList.addAll(mReplaceTextSizeOriginalList);
        mMergeList.addAll(mAllTextSizeList);

        // 排序 -- 原有"网页链接"和长网址
        List<TextStylePhrase.TextSize> mMergeResultList = sortByStart(mMergeList);
        int mMergeResultSize = mMergeResultList.size();
        AvLog.d_bug("mMergeResultSize = " + mMergeResultSize);

        // 替换长网址链接为"网页链接"
        for (TextStylePhrase.TextSize textSize : mAllTextSizeList) {
            text = text.replace(textSize.getText(), mReplaceText);
        }

        // ------------------ 完美的分割线 --------------------

        // 已经完成替换
        TextStylePhrase mDisposeTextStylePhrase = new TextStylePhrase(text);
        List<TextStylePhrase.TextSize> mDisposeTextSizeList = mDisposeTextStylePhrase.searchAllTextSize(mReplaceText);
        int mDisposeSize = mDisposeTextSizeList.size();
        AvLog.d_bug("mDisposeSize = " + mDisposeSize);

        if (mDisposeSize != mMergeResultSize) return;

        for (int i=0; i<mDisposeSize; i++) {
            TextStylePhrase.TextSize textSize = mDisposeTextSizeList.get(i);
            final TextStylePhrase.TextSize mMergeResultTextSize = mMergeResultList.get(i);
            if (!TextUtils.equals(mReplaceText, mMergeResultTextSize.getText())){
                mDisposeTextStylePhrase.setForegroundColorSpan(R.color.color2F93FF, textSize);
                mDisposeTextStylePhrase.setClickableSpan(textSize, new NoUnderlineClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        ToastUtils.showDebug("测试：" + mMergeResultTextSize.getText());
                    }
                });
            }
        }

        mWeiboLinkViewF.setText(mDisposeTextStylePhrase.getSpannableStringBuilder());
        mWeiboLinkViewF.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 排序 - 从小到大
     * */
    private static List<TextStylePhrase.TextSize> sortByStart(List<TextStylePhrase.TextSize> list){
        try {
            return TextStylePhrase.sortByStart(list);
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private void setDownUpView() {
        String text = "这个是上标上标上标上标上标，那个是下标下标下标下标下标！";

        TextStylePhrase mTextStylePhrase = new TextStylePhrase(text);

        // 支持多个相同的字符标记上标
        List<TextStylePhrase.TextSize> mSuperscriptTextSizeList = mTextStylePhrase.searchAllTextSize("上标");
        for (TextStylePhrase.TextSize textSize : mSuperscriptTextSizeList) {
            mTextStylePhrase.setSuperscriptSpan(textSize);
            mTextStylePhrase.setAbsoluteSizeSpan(8, textSize);
            mTextStylePhrase.setForegroundColorSpan(R.color.colorC07703, textSize);
        }
        // 如果想指定哪一个标记为下标，可以使用上面的集合，取其中的一个TextSize进行标记
        TextStylePhrase.TextSize mSuperscriptTextSize = mSuperscriptTextSizeList.get(mSuperscriptTextSizeList.size() - 1);
        mTextStylePhrase.setBackgroundColorSpan(R.color.color0084FB, mSuperscriptTextSize);

        // 标记单个下标
        TextStylePhrase.TextSize mSubscriptTextSize = mTextStylePhrase.getTextSize("下标");
        mTextStylePhrase.setSubscriptSpan(mSubscriptTextSize);
        mTextStylePhrase.setAbsoluteSizeSpan(8, mSubscriptTextSize);
        mTextStylePhrase.setForegroundColorSpan(R.color.colorRed, mSubscriptTextSize);
        mTextStylePhrase.setBackgroundColorSpan(R.color.color0084FB, mSubscriptTextSize);

        mDownViewUp.setText(mTextStylePhrase.getSpannableStringBuilder());
    }

    private void showImageTextView() {
        mImageTextView.removeAllIPovideStyleData();

        TextStylePhrase mTextStylePhrase = new TextStylePhrase(sourceText);
        TextStylePhrase.TextSize imageTextSize = mTextStylePhrase.getTextSize(vTag);
        Drawable drawable = getResources().getDrawable(R.drawable.public_icon_v);
        mTextStylePhrase.setImageSpan(imageTextSize, drawable);

        // 生成外部样式
        RichTextView.ExternalStylePhraseDataDefault mExternalStylePhraseDataDefault =
                new RichTextView.ExternalStylePhraseDataDefault(mTextStylePhrase);

        // 设置外部拓展样式
        mImageTextView.setExternalStylePhraseData(mExternalStylePhraseDataDefault);


        mImageTextView.setOnTagContentClickListenter(new BaseRichTextStyle.OnTagContentClickListenter() {
            @Override
            public void onClick(int style, String text) {
                ToastUtils.show(style + " - " + text);
            }
        });

        mImageTextView.setContentText(sourceText);
        mImageTextView.showText();
    }

    private void showSpecialTTTextView() {
        final String nickName = "@户_703520～12";
        String contentText = nickName + ": 4";

        int mFlags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;

        SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(contentText);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorRed));
        mSpannableStringBuilder.setSpan(colorSpan, 0, nickName.length(), mFlags);
        mSpannableStringBuilder.setSpan(new NoUnderlineClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ToastUtils.show("1 - " + nickName);
            }
        }, 0, nickName.length(), mFlags);

        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorC07703)),
                0, nickName.length(), mFlags);
        mSpannableStringBuilder.setSpan(new NoUnderlineClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ToastUtils.show("2 - " + nickName);
            }
        }, 0, nickName.length(), mFlags);

        mSpannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRed)),
                0, nickName.length(), mFlags);
        mSpannableStringBuilder.setSpan(new NoUnderlineClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ToastUtils.show("3 - " + nickName);
            }
        }, 0, nickName.length(), mFlags);

        NoUnderlineClickableSpan[] mNoUnderlineClickableSpans =
                mSpannableStringBuilder.getSpans(0, nickName.length(), NoUnderlineClickableSpan.class);
        for (NoUnderlineClickableSpan span : mNoUnderlineClickableSpans) {
            AvLog.d("span = " + span);
        }
        mSpannableStringBuilder.removeSpan(mNoUnderlineClickableSpans[0]);
        mSpannableStringBuilder.removeSpan(mNoUnderlineClickableSpans[1]);
//        mSpannableStringBuilder.removeSpan(mNoUnderlineClickableSpans[2]);

        mSpecialTtTextView.setText(mSpannableStringBuilder);
        mSpecialTtTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showSpecialTextView() {
        String nickName = "@不要关注我@了你@";
        String contentText = nickName + " : @不要关注我@了你@ 个咯啦咯啦！@米基 搜索 @不要关注我@了你@ 方法 @不要关注我@了你@";

        mSpecialTextView.removeAllIPovideStyleData();


        MentionUserPovideStyleData mMentionUserPovideStyleData = new MentionUserPovideStyleData();

        mMentionUserPovideStyleData.setExcludeMatchText(nickName);
        mMentionUserPovideStyleData.setExcludeMatchWhichOne(0);

        mSpecialTextView.addRichTextStyle(mMentionUserPovideStyleData);

        TextProideStyleData mTextProideStyleData = new TextProideStyleData();
        mSpecialTextView.addRichTextStyle(mTextProideStyleData);
        mTextProideStyleData.setNeedHighlightText(nickName);

        mSpecialTextView.setOnTagContentClickListenter(new BaseRichTextStyle.OnTagContentClickListenter() {
            @Override
            public void onClick(int style, String text) {
                ToastUtils.show(style + " - " + text);
            }
        });

        mSpecialTextView.setContentText(contentText);

        mSpecialTextView.showText();

    }

    protected void testStylePhrase() {
        TextStylePhrase mTextStylePhrase = new TextStylePhrase(sourceText);

        TextStylePhrase.TextSize phraseText1 = mTextStylePhrase.getTextSize("水寒");
        mTextStylePhrase.setForegroundColorSpan(R.color.colorPrimaryDark, phraseText1);
        mTextStylePhrase.setAbsoluteSizeSpan(25, phraseText1);

        TextStylePhrase.TextSize phraseText2 = mTextStylePhrase.getTextSize("一去");
        mTextStylePhrase.setForegroundColorSpan(R.color.colorAccent, phraseText2);
        mTextStylePhrase.setAbsoluteSizeSpan(20, phraseText2);
        mTextStylePhrase.setStyleSpan(Typeface.BOLD, phraseText2);

        TextStylePhrase.TextSize phraseText3 = mTextStylePhrase.getTextSize("不复返");
        mTextStylePhrase.setForegroundColorSpan(R.color.color0084FB, phraseText3);
        mTextStylePhrase.setAbsoluteSizeSpan(14, phraseText3);
        mTextStylePhrase.setStyleSpan(Typeface.NORMAL, phraseText3);

        TextStylePhrase.TextSize phraseText4 = mTextStylePhrase.getTextSize("兮");
        mTextStylePhrase.setForegroundColorSpan(R.color.colorC07703, phraseText4);
        mTextStylePhrase.setAbsoluteSizeSpan(12, phraseText4);
        mTextStylePhrase.setStyleSpan(Typeface.NORMAL, phraseText4);

        // 添加下划线
        mTextStylePhrase.setUnderlineSpan("风萧萧兮易水寒");

        // 添加删除线
        mTextStylePhrase.setStrikethroughSpan("壮士");

        // 改变部分颜色
        mTextStylePhrase.setForegroundColorSpan(R.color.colorFDDA35, "风萧萧");

        TextStylePhrase.TextSize imageTextSize = mTextStylePhrase.getTextSize(vTag);
        Drawable drawable = getResources().getDrawable(R.drawable.public_icon_v);
        mTextStylePhrase.setImageSpan(imageTextSize, drawable);

        mContentView.setText(mTextStylePhrase.getSpannableStringBuilder());
        mContentView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void showFlexibleRichTextView() {
        String content = "南靖#土楼 以建筑奇特，南靖#土楼 @小米之家 居多而闻名天下。"
                + "福建对于我来说知道最多的可能就是关于#土楼 的传说。"
                + "这么小的孩子不上学在这里做起了生意，竟然没有人管。@人民日报 @南方报业 景区门口乱糟糟的，给人感觉一点都不规范。"
                + "奇怪的是这里有好几个临时售票点，而正规的售票点就在旁边，www.weibo.com 为什么还要设临时的，让人一脸的茫然。"
                + "临时售票点就拦在去往景区里面的路口，只允许一辆车通过，手潮一点 的司机不小心 就会撞到边上。"
                + "很容易出事故，https://www.baidu.com @人民日报 #第一印象 就大打折扣。";

        mFlexibleRichTextView.setText(content, true, mStatus);

        // 内置的三个
        mFlexibleRichTextView.addRichTextStyle(new MentionUserPovideStyleData());
        mFlexibleRichTextView.addRichTextStyle(new TownTalkPovideStyleData());
        mFlexibleRichTextView.addRichTextStyle(new WebUrlPovideStyleData());

        mFlexibleRichTextView.addRichTextStyle(new NameProideStyleData("南靖")); // 自定义
        mFlexibleRichTextView.setOnTagContentClickListenter(new BaseRichTextStyle.OnTagContentClickListenter() {
            @Override
            public void onClick(int style, String text) {
                ToastUtils.show(style + " - " + text);
            }
        });
        mFlexibleRichTextView.setOnFlexibleClickListener(new FlexibleRichTextView.OnFlexibleClickListener() {
            @Override
            public void onClick(int status) {
                mStatus = status;
            }
        });
        mFlexibleRichTextView.showText();
    }

}
