package com.ultrasoft.ultracomplaint.requestbody;

public class Notification {

    private String body;

    private String title;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Notification(String body, String title) {
        super();
        this.body = body;
        this.title = title;
    }
}
