package com.koudai.styletextviewdemo.model;

/**
 * @auther jsk
 * @date 2019/1/5
 */
public class RichImage {

    private String text;
    private String src;
    private int width;
    private int height;

    public RichImage() {
    }

    public RichImage(String text, String src, int width, int height) {
        this.text = text;
        this.src = src;
        this.width = width;
        this.height = height;
    }

    public RichImage(String text, String src) {
        this.text = text;
        this.src = src;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
