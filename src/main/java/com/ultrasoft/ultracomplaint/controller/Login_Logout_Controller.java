package com.ultrasoft.ultracomplaint.controller;

import com.ultrasoft.ultracomplaint.entity.Tbl_login_Session;
import com.ultrasoft.ultracomplaint.enums.UserRoles;
import com.ultrasoft.ultracomplaint.mapper.Login_Session_Mapper;
import com.ultrasoft.ultracomplaint.requestbody.Login_Request_Body;
import com.ultrasoft.ultracomplaint.responsebody.APIResponseBody;
import com.ultrasoft.ultracomplaint.service.Login_Session_Service;
import com.ultrasoft.ultracomplaint.utility.EmailTokenPojo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@Tag(name = "LOGIN/LOGOUT CONTROLLER")
@CrossOrigin("*")
public class Login_Logout_Controller {

    private final Login_Session_Service session;


    public Login_Logout_Controller(Login_Session_Service session) {
        this.session = session;
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponseBody> userLogin(@RequestBody Login_Request_Body data,
                                                     @RequestParam(required = false) String notificationId) throws Exception{
        try {
            Tbl_login_Session sesData = session.generateSessionAdmin(data, notificationId);
            return new ResponseEntity<>(new APIResponseBody("Login Successfully",
                    Login_Session_Mapper.toShow(sesData)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<APIResponseBody> userLogout(
            @RequestHeader(name = "X-AUTH-TOKEN") String authToken
    ) throws Exception{
        try {
            session.logout(authToken);
            return new ResponseEntity<>(new APIResponseBody("Logout Successfully",
                    "Logout Successfully"), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/forgotpassword/send/email")
    public ResponseEntity<APIResponseBody> forgotPassword(@RequestParam(name = "email") String email,
                                                          @RequestParam UserRoles type){
        try {
            return new ResponseEntity<>(new APIResponseBody("Mail Sent Successfully To Your Email",
                    session.chenagePassword(email, type)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }

    @PostMapping("/reset/password")
    public ResponseEntity<APIResponseBody> changePassword(@RequestParam String email,
                                                          @RequestParam String password,
                                                          @RequestParam String token){
        try {
            return new ResponseEntity<>(new APIResponseBody("Password Changed Successfully",
                    session.resetPassword(email, password, token)), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new APIResponseBody(e.getMessage()), HttpStatus.OK);
        }
    }
}
