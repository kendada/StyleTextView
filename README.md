### StyleTextView 设置TextView显示样式，支持富文本展示：更改部分字体颜色，添加图标，显示下划线等；并且支持显示类似微博话题，@某用户，以及字体过多时显示收起和展开操作等  

1. 封装SpannableStringBuilder支持富文本显示，简化系统API使用，详见：[TextStylePhrase](https://github.com/kendada/StyleTextView/blob/master/styletextview/src/main/java/com/koudai/styletextview/textstyle/TextStylePhrase.java "Title")。      


2. 支持显示类似微博话题，@某用户，以及自定义规则，详见：[RichTextView](https://github.com/kendada/StyleTextView/blob/master/styletextview/src/main/java/com/koudai/styletextview/RichTextView.java "Title")，不支持展开、收起。[FlexibleRichTextView](https://github.com/kendada/StyleTextView/blob/master/styletextview/src/main/java/com/koudai/styletextview/FlexibleRichTextView.java "Title")，支持展开、收起。      

+ WebUrlPovideStyleData 网址高亮，添加点击
+ MentionUserPovideStyleData @某用户
+ TownTalkPovideStyleData 话题，内置样式和微博不同，详见代码，可自行更改
+ 如果需要自定义样式，可以查看IPovideStyleData，按照里面的规则进行编码，不要实现IPovideStyleData，只需要继承DefaultPovideStyleDataImp就好了。Demo已经有实例，具体可以查看NameProideStyleData。

##### 需要在Application中初始化，AppStyleUtils.init(this)，这里主要是用来获取Resources对象，不然无法使用；

``   
Demo代码如下：
public class MainActivity extends AppCompatActivity {


    private FlexibleRichTextView mFlexibleRichTextView;
    private TextView mContentView;

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

    private void initView(){
        mContentView = findViewById(R.id.content_view);
        mFlexibleRichTextView = findViewById(R.id.rich_text_view);
    }

    private void initData() {
        testStylePhrase();
        showFlexibleRichTextView();
    }

    protected void testStylePhrase() {
        TextStylePhrase mTextStylePhrase = new TextStylePhrase(sourceText);

        TextStylePhrase.TargetPhraseText phraseText1 = new TextStylePhrase.TargetPhraseText("水寒",
                R.color.colorPrimaryDark, 25, -1);

        TextStylePhrase.TargetPhraseText phraseText2 = new TextStylePhrase.TargetPhraseText("一去",
                R.color.colorAccent, 20, Typeface.BOLD);

        TextStylePhrase.TargetPhraseText phraseText3 = new TextStylePhrase.TargetPhraseText("不复返",
                R.color.color0084FB, 14, Typeface.NORMAL);

        TextStylePhrase.TargetPhraseText phraseText4 = new TextStylePhrase.TargetPhraseText("兮",
                R.color.colorC07703, 12, Typeface.NORMAL);

        mTextStylePhrase.setTargetPhraseTextDatas(phraseText1, phraseText2, phraseText3, phraseText4);

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

    private void showFlexibleRichTextView(){
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

``

![Alt text](https://github.com/kendada/StyleTextView/blob/master/app/src/main/res/drawable/style_text_view_image.png)
