package com.ultrasoft.ultracomplaint.utility;

import com.ultrasoft.ultracomplaint.requestbody.Data;
import com.ultrasoft.ultracomplaint.requestbody.PushNotificationRequest;
import com.ultrasoft.ultracomplaint.serviceimpl.FCMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Push_Notification_Service {

    private Logger logger = LoggerFactory.getLogger(Push_Notification_Service.class);

    @Autowired
    private FCMService fcmService;


    public void sendPushNotification(Data request) throws Exception {
        try {
            fcmService.sendMessage(getSamplePayloadData(request.getText(),request.getTitle(), request.getStyle()), request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void sendPushNotificationToCustomer(Data request) throws Exception {
        try {
            fcmService.sendMessageToCustomer(getSamplePayloadData(request.getText(),request.getTitle(), request.getStyle()), request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToToken(getSamplePayloadData(request.getText(),request.getTitle(), request.getStyle()),request);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Map<String, String> getSamplePayloadData(String text, String title, String style) {
        Map<String, String> pushData = new HashMap<>();
        pushData.put("messageId", GenerateRandomThing.generateRefString(10));
        pushData.put("text", text);
        pushData.put("title", title);
//        pushData.put("picture", picture);
        pushData.put("style", style);
//        pushData.put("user", "pankaj singh");
        return pushData;
    }
}
