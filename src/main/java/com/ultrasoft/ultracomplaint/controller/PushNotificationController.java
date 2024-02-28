package com.ultrasoft.ultracomplaint.controller;

import com.google.gson.Gson;
import com.ultrasoft.ultracomplaint.requestbody.Data;
import com.ultrasoft.ultracomplaint.requestbody.PushNotificationRequest;
import com.ultrasoft.ultracomplaint.responsebody.APIResponseBody;
import com.ultrasoft.ultracomplaint.utility.Push_Notification_Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/push/notification")
@Tag(name = "NOTIFICATION CONTROLLER")
public class PushNotificationController {

    private final Push_Notification_Service pushNotificationService;

    public PushNotificationController(Push_Notification_Service pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping(value = "/token", name = "Notification For Single Engineer")
    public ResponseEntity<APIResponseBody> sendNotification(@RequestBody Data body) {
        try {
            System.out.println("Response -----> "+new Gson().toJson(body));
            pushNotificationService.sendPushNotification(body);
            return new ResponseEntity<>(new APIResponseBody("Notification Sent Successfully", body), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/topic/admin", name = "Notification for All Admin")
    public ResponseEntity<Object> sendTokenNotification(@RequestBody(required = true) PushNotificationRequest request) {
        try {
            pushNotificationService.sendPushNotificationToToken(request);
            return new ResponseEntity<>(new APIResponseBody( "Notification has been sent.", request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new APIResponseBody(
                    e.getMessage()),
                    HttpStatus.OK);
        }
    }
}
