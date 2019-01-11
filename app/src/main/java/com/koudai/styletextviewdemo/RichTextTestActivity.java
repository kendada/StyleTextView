package com.koudai.styletextviewdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.koudai.styletextview.textstyle.NoUnderlineClickableSpan;
import com.koudai.styletextview.textstyle.TextStylePhrase;
import com.koudai.styletextview.utils.AvLog;
import com.koudai.styletextview.utils.MatchUtils;
import com.koudai.styletextviewdemo.model.RichImage;
import com.koudai.styletextviewdemo.model.RichText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 此次案例主要介绍富文本实现的一种方式
 * 针对图片主要是定义标签<RichImage>，与HTML<img>类似
 * @auther jsk
 * @date 2019/1/5
 */
public class RichTextTestActivity extends AppCompatActivity {

    private TextView mTextViewRich;

    public static void start(Context context) {
        Intent intent = new Intent(context, RichTextTestActivity.class);
        context.startActivity(intent);
    }

    private static String mContent =
            "倘若你看不到我那绚烂天空，我又何必解释我的不同\n" +
                    "——华晨宇<RichText text=\"《我和我》\" color=\"#FF0000\" style=\"blod\" size=\"20\" />\n" +
                    "<RichImage src=\"https://file.juzimi.com/weibopic/jrzeme4.jpg\" width=\"510\" height=\"379\" />\n" +
                    "治生不求富，读书不求官，修德不求报，为文不求传，譬如饮酒不醉，陶然有余欢，中含不尽意，欲辨已忘言。\n" +
                    "——曾国藩\n" +
                    "<RichImage src=\"https://file.juzimi.com/weibopic/jpzdmi2.jpg\" width=\"495\" height=\"338\"/>\n" +
                    "我知道我会为我的性格和生活方式吃无数亏，吞无数恶果，但至少大到理想，小到闪念，我几乎都没有放过。所以就算我的生活里充满挫败甚至后悔，但遗憾并不多。\n" +
                    "——韩寒《我所理解的生活》\n" +
                    "<RichImage src=\"https://file.juzimi.com/weibopic/juzrmi7.jpg\" width=\"245\" height=\"200\" />\n" +
                    "有的人看上去内向，不爱打闹，但他的内心其实和外表所呈现的恰恰相反，只是你没有和他相处，成为他的朋友而已。有的人看上去很开朗，爱说话，他的内心也会很阳光，热情；但这并不代表他没有内向的一面，只是被他掩饰了。而这两种人的共同点就在于都有自己独特的魅力，谁也不高贵，谁也不平庸。\n" +
                    "<RichImage src=\"https://file.juzimi.com/weibopic/jdzaml3.jpg\" width=\"600\" height=\"600\" />\n" +
                    "如果拖着所有人下地狱了，谁来救赎她？ 她当时是这样想的，现在依然是这样想的，只是，终究今人非故人。\n" +
                    "——沧澜止戈《一女御皇》\n" +
                    "<RichImage src=\"https://file.juzimi.com/weibopic/jrzeml1.jpg\" width=\"500\" height=\"347\" />\n" +
                    "其实一家人，住在一个屋檐下，照样可以各过各的日子，可是从心里产生的那种顾忌，才是一个家之所以为家的意义。我这一辈子怎么做，也不能像做菜一样，把所有的材料都集中起来才下锅，当然，吃到嘴里是酸甜苦辣，各尝各的味。\n" +
                    "——李安《饮食男女》\n" +
                    "<RichImage src=\"http://pic26.nipic.com/20121227/10193203_131357536000_2.jpg\" width=\"1024\" height=\"685\" />\n";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text_test_layout);

        initView();
        initData();
    }

    private void initView() {
        mTextViewRich = (TextView) findViewById(R.id.rich_text_view);
    }

    private void initData(){
        List<RichImage> richImages = getRichImageData();
        List<RichText> richTexts = getRichTextData();
        setData(richImages, richTexts);
    }


    private void setData(List<RichImage> list, List<RichText> richTexts){
        if (list == null || list.size() == 0) return;

        final TextStylePhrase textStylePhrase = new TextStylePhrase(mContent);

        for (final RichImage richImage : list){
            final TextStylePhrase.TextSize textSize = textStylePhrase.getTextSize(richImage.getText());
            textStylePhrase.setImageSpan(textSize, getResources().getDrawable(R.drawable.default_error));
            Glide.with(this)
                    .load(richImage.getSrc())
                    .centerCrop()
                    .skipMemoryCache(false)
                    .override(richImage.getWidth(), richImage.getHeight())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            AvLog.d_bug("Glide - resource = " + resource);
                            textStylePhrase.setImageSpan(textSize, resource);

                            mTextViewRich.setText(textStylePhrase.getSpannableStringBuilder());
                            mTextViewRich.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    });

            textStylePhrase.setClickableSpan(textSize, new NoUnderlineClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    ToastUtils.show(richImage.getSrc());
                }
            });
        }

        mTextViewRich.setText(textStylePhrase.getSpannableStringBuilder());
        mTextViewRich.setMovementMethod(LinkMovementMethod.getInstance());
    }


    private List<RichImage> getRichImageData (){
        List<RichImage> list = new ArrayList<>();

        Pattern mRichImagePattern = Pattern.compile("<(RichImage)(.*?)(/>|></RichImage>|>)");

        List<String> targetList = MatchUtils.matchTargetText(mContent, mRichImagePattern);

        for (String text : targetList){
            String src = getTextByTag(text, "src");

            String widthText = getTextByTag(text, "width");
            String heightText = getTextByTag(text, "height");

            int width = Integer.valueOf(filterNotNumber(widthText));
            int height = Integer.valueOf(filterNotNumber(heightText));

            RichImage richImage = new RichImage(text, src, width, height);
            list.add(richImage);
        }

        return list;
    }

    private List<RichText> getRichTextData(){
        List<RichText> list = new ArrayList<>();

        Pattern mRichImagePattern = Pattern.compile("<(RichText)(.*?)(/>|></RichText>|>)");

        List<String> targetList = MatchUtils.matchTargetText(mContent, mRichImagePattern);
        for (String text : targetList){
            String showText = getTextByTag(text, "text");
            String color = getTextByTag(text, "color");
            String style = getTextByTag(text, "style");
            String size = getTextByTag(text, "size");

            RichText richText = new RichText(text, showText, color, style, Integer.valueOf(filterNotNumber(size)));
            list.add(richText);
            AvLog.d_bug("richText = " + richText);
        }

        return list;
    }

    private String getTextByTag(String text, String tag){
        String resultText = null;
        Pattern srcPattern = Pattern.compile("(" + tag + ")=(\"|\')(.*?)(\"|\')");
        Matcher srcMatcher = srcPattern.matcher(text);
        if (srcMatcher.find()) {
            resultText = srcMatcher.group(3);
        }
        return resultText;
    }

    /**
     * 确认字符串可以转换为浮点类型
     * @param text 已保证text只包含一个"."
     */
    public static String filterNotNumber(String text) {
        if (TextUtils.isEmpty(text)) {
            return "0";
        }
        char[] chars = text.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : chars) {
            if ((c >= '0' && c <= '9') || c == '.') {
                builder.append(c);
            }
        }
        return builder.toString();
    }









}
