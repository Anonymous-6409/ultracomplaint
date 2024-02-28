package com.ultrasoft.ultracomplaint.requestbody;

import javax.validation.constraints.NotNull;

public class PushNotificationRequest {

    @NotNull(message = "Title is required")
    private String title;

    @NotNull(message = "Text is required")
    private String text;

    @NotNull(message = "Style is required")
    private String style;

//    private String picture;

//    @NotNull(message = "Mobile number is required")
//    private String mobile;

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
//    public void setPicture(String picture) {
//        this.picture = picture;
//    }
}
