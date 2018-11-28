package com.koudai.styletextview.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther jsk
 * @date 2018/11/14
 */
public class MatchUtils {

    private MatchUtils(){}

    /**
     * 话题正则表达式
     */
    public static final String REGEX_TOWNTALK = "#([^\\s]+)";
    public static final Pattern PATTERN_TOWNTALK = Pattern.compile(REGEX_TOWNTALK);

    /**
     * 用户@正则表达式
     * */
    public static final String REGEX_USER_MENTION = "@([^\\s]+)";
    public static final Pattern PATTERN_USER_MENTION = Pattern.compile(REGEX_USER_MENTION);


    /**
     * 网址正则表达式
     */
    public static final Pattern webUrlPattern = Pattern.compile("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?", Pattern.CASE_INSENSITIVE);



    public static List<String> matchTargetText(String text, Pattern pattern) {
        List<String> targetList = new ArrayList<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int matchStart = matcher.start();
            int matchEnd = matcher.end();
            String tmptarget = text.substring(matchStart, matchEnd);
            targetList.add(tmptarget);
            text = text.replace(tmptarget, "");
            matcher = pattern.matcher(text);
        }
        return targetList;
    }

    private static void searchAllIndex(String content, String key) {
        int start = content.indexOf(key);//第一个出现的索引位置
        while (start != -1) {
            start = content.indexOf(key, start + 1);//从这个索引往后开始第一个出现的位置
        }
    }

}
