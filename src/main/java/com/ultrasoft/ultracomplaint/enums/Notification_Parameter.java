package com.ultrasoft.ultracomplaint.enums;

public enum Notification_Parameter {

    SOUND("default"),
    COLOR("#FFFF00");

    private String value;

    Notification_Parameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
