package com.ultrasoft.ultracomplaint.responsebody;

import com.ultrasoft.ultracomplaint.requestbody.Data;
import com.ultrasoft.ultracomplaint.requestbody.Notification;

public class NotificationResponse {

    private Notification notification;

    private Data data;

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
