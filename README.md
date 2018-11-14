### StyleTextView 设置TextView显示样式，支持富文本展示：更改部分字体颜色，添加图标，显示下划线等；并且支持显示类似微博话题，@某用户，以及字体过多时显示收起和展开操作等  

1. 封装SpannableStringBuilder支持富文本显示，简化系统API使用，详见：[TextStylePhrase](https://github.com/kendada/StyleTextView/blob/master/styletextview/src/main/java/com/koudai/styletextview/textstyle/TextStylePhrase.java "Title")。      


2. 支持显示类似微博话题，@某用户，以及自定义规则，详见：[RichTextView](https://github.com/kendada/StyleTextView/blob/master/styletextview/src/main/java/com/koudai/styletextview/RichTextView.java "Title")，不支持展开、收起。[FlexibleRichTextView](https://github.com/kendada/StyleTextView/blob/master/styletextview/src/main/java/com/koudai/styletextview/FlexibleRichTextView.java "Title")，支持展开、收起。      

+ WebUrlPovideStyleData 网址高亮，添加点击
+ MentionUserPovideStyleData @某用户
+ TownTalkPovideStyleData 话题，内置样式和微博不同，详见代码，可自行更改

![Alt text](https://github.com/kendada/StyleTextView/blob/master/app/src/main/res/drawable/style_text_view_image.png)
