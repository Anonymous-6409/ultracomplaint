package com.ultrasoft.ultracomplaint.requestbody;


public class Data {

//    private static final long serialVersionUID = 7283389362503039330L;

    private String title;

    private String text;

    private String style;

//    private String picture;

    private String number;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

//    public String getPicture() {
//        return picture;
//    }
//
//    public void setPicture(String picture) {
//        this.picture = picture;
//    }
//
//    public Data(String title, String text, String style, String picture) {
//        this.title = title;
//        this.text = text;
//        this.style = style;
//        this.picture = picture;
//    }

    public Data() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
