package com.koudai.styletextviewdemo.model;

/**
 * @auther jsk
 * @date 2019/1/5
 */
public class RichText {

    private String text; // 例：<RichText showText="这个是什么地方" color="#FF0000" style="blod" size="20" />
    private String showText; // 例：这个是什么地方
    private String color; // 例：#FF0000
    private String style; // 例：blod
    private int size; // 例：20

    public RichText(){}

    public RichText(String text, String showText, String color, String style, int size) {
        this.text = text;
        this.showText = showText;
        this.color = color;
        this.style = style;
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getShowText() {
        return showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "RichText{" +
                "text='" + text + '\'' +
                ", showText='" + showText + '\'' +
                ", color='" + color + '\'' +
                ", style='" + style + '\'' +
                ", size=" + size +
                '}';
    }
}
