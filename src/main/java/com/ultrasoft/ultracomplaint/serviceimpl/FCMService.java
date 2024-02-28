package com.ultrasoft.ultracomplaint.serviceimpl;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ultrasoft.ultracomplaint.entity.Tbl_Admin_Users;
import com.ultrasoft.ultracomplaint.entity.Tbl_Customer_details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Engineer_Details;
import com.ultrasoft.ultracomplaint.entity.Tbl_Notification;
import com.ultrasoft.ultracomplaint.enums.*;
import com.ultrasoft.ultracomplaint.repo.Admin_Repo;
import com.ultrasoft.ultracomplaint.repo.Customer_Repo;
import com.ultrasoft.ultracomplaint.repo.Engineer_Repo;
import com.ultrasoft.ultracomplaint.repo.*;
import com.ultrasoft.ultracomplaint.requestbody.Data;
import com.ultrasoft.ultracomplaint.requestbody.PushNotificationRequest;
import com.ultrasoft.ultracomplaint.utility.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FCMService {

    @Autowired
    private NotificationRepo repo;

    private final Logger logger = LoggerFactory.getLogger(FCMService.class);

    @Autowired
    private Admin_Repo admin_Repo;
    @Autowired
    private Engineer_Repo engineer_Repo;
    @Autowired
    private Customer_Repo customer_Repo;

    public void sendMessage(Map<String, String> data, Data request)
            throws Exception {
        try {
            Message message = getPreconfiguredMessageWithData(data, request);
            System.out.println("Message for single engineer -----> " + new Gson().toJson(message));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(message);
            String response = sendAndGetResponse(message, request);
            logger.info("Sent message with data. Topic: " + "ultra" + ", " + response+ " msg "+jsonOutput);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public void sendMessageToCustomer(Map<String, String> data, Data request)
            throws Exception {
        Message message = getPreconfiguredMessageWithDataToCustomer(data, request);
        System.out.println("Message for single engineer -----> " + new Gson().toJson(message));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        String response = sendAndGetResponse(message, request);
        logger.info("Sent message with data. Topic: " + "ultra" + ", " + response+ " msg "+jsonOutput);
    }

    private Message getPreconfiguredMessageWithDataToCustomer(Map<String, String> data, Data request) throws Exception {

        Tbl_Customer_details engineerDetails = customer_Repo.findByCustomerMobile(request.getNumber());
        if (engineerDetails==null){
            throw new Exception("Invalid Mobile Number");
        }
        Notification notification = Notification.builder().setTitle(request.getTitle()).setBody(request.getText()).build();
        return Message.builder()
                .setNotification(notification)
                .putAllData(data)
                .setToken(engineerDetails.getNotificationId())
                .build();
    }

    private Message getPreconfiguredMessageWithData(Map<String, String> data, Data request) throws Exception {

        try {
            Tbl_Engineer_Details engineerDetails = engineer_Repo.findByEngineerMobile(request.getNumber());
            if (engineerDetails==null){
                throw new Exception("Invalid Mobile Number");
            }
            Notification notification = Notification.builder().setTitle(request.getTitle()).setBody(request.getText()).build();
            return Message.builder().setNotification(notification).putAllData(data).setToken(engineerDetails.getNotificationId())
                    .build();
//            return getPreconfiguredMessageBuilder(request).putAllData(data).setToken(engineerDetails.getNotificationId())
//                    .build();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private Message.Builder getPreconfiguredMessageBuilder(Data request) throws Exception {
        try {
            Notification notification;
            notification = Notification.builder().setTitle(request.getTitle()).setBody(request.getText()).build();
            AndroidConfig androidConfig = getAndroidConfig("ultra");
            ApnsConfig apnsConfig = getApnsConfig("ultra");
            return Message.builder()
                    .setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(notification);
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    private String sendAndGetResponse(Message message, Data request) throws Exception {
        try {
            Tbl_Notification tblNotification = new Tbl_Notification();
            tblNotification.setTitle(request.getTitle());
            tblNotification.setText(request.getText());
            tblNotification.setStyle(request.getStyle());
            tblNotification.setReferenceId(GenerateRandomThing.generateRefString(16));
            tblNotification.setCreatedDate(new Date());
            tblNotification.setUpdatedDate(null);
            tblNotification.setActive(Boolean.TRUE);
            tblNotification.setDeleted(Boolean.FALSE);
            repo.save(tblNotification);
            System.out.println("Sent to topic");
            return FirebaseMessaging.getInstance().sendAsync(message).get();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public void sendMessageToToken(Map<String, String> data, PushNotificationRequest request)
            throws Exception {
        MulticastMessage message = getPreconfiguredMessageToToken(data, request);
        System.out.println("message:"+new Gson().toJson(message));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(message);
        BatchResponse response = sendAndGetResponse(message, request);
        logger.info("Sent message to token. Device token: " + new Gson().toJson(request) + ", " + response+ " msg "+jsonOutput);
    }

    private BatchResponse sendAndGetResponse(MulticastMessage message, PushNotificationRequest request) throws InterruptedException, ExecutionException {
        Tbl_Notification tblNotification = new Tbl_Notification();
        tblNotification.setTitle(request.getTitle());
        tblNotification.setText(request.getText());
        tblNotification.setStyle(request.getStyle());// need to change
        tblNotification.setReferenceId(GenerateRandomThing.generateRefString(16));
        tblNotification.setCreatedDate(new Date());
        tblNotification.setUpdatedDate(new Date());
        tblNotification.setActive(Boolean.TRUE);
        tblNotification.setDeleted(Boolean.FALSE);
        repo.save(tblNotification);
        BatchResponse r = FirebaseMessaging.getInstance().sendMulticastAsync(message).get();
        return r;
    }

    private MulticastMessage getPreconfiguredMessageToToken(Map<String, String> data, PushNotificationRequest request)
            throws Exception {

        List<Tbl_Admin_Users> list = admin_Repo.findAll();
        if(list.isEmpty()) {
            throw new Exception("No Admin Found For Notification");
        }
        list.removeIf(l -> l.getNotificationId()==null);
        List<String> tokens = new ArrayList<>();
        list.stream().map(Tbl_Admin_Users::getNotificationId).forEach(tokens::add);
        List<String> token = tokens.stream().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println("TOKENS: "+new Gson().toJson(token));
        return  getPreconfiguredMessageBuilder(request)
                .putAllData(data)
                .addAllTokens(token)
                .build();

    }

    private MulticastMessage.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        Notification notification;
        notification = Notification.builder().setTitle(request.getTitle()).setBody(request.getText()).build();
        //(need to change Image)
        AndroidConfig androidConfig = getAndroidConfig("ultra");
        ApnsConfig apnsConfig = getApnsConfig("ultra");
        return MulticastMessage.builder().setApnsConfig(apnsConfig).setAndroidConfig(androidConfig).setNotification(notification);
    }

    //ANDROID CONFIG

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setSound(Notification_Parameter.SOUND.getValue())
                        .setColor(Notification_Parameter.COLOR.getValue())
                        .setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory(topic)
                        .setThreadId(topic)
                        .build()).build();
    }
}
