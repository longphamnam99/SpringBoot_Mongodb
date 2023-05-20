package net.codejava.CodeJavaApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.codejava.CodeJavaApp.core.MyController;
import net.codejava.CodeJavaApp.models.User_model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.bson.Document;

@RestController
public class User_controller extends MyController {
    @GetMapping("/api/user/test")
    public boolean test(@RequestHeader("Authorization") String authorizationHeader) {
        return "mytoken".equals(authorizationHeader.substring(7));
    }

    @PostMapping("/api/user/login")
    public static ResponseEntity<?> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        if (email.isEmpty() || password.isEmpty())
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        User_model model = new User_model();
        boolean result = model.authenticate(email, password);
        if (result)
            return ResponseEntity.ok(responseOK(null));
        return ResponseEntity.ok(responseNotOk(400, "not ok", null));
    }

    @PostMapping("/api/user/add")
    public static ResponseEntity<?> add(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }

        if (email.isEmpty() || password.isEmpty())
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        User_model model = new User_model();
        Date currentTime = new Date();

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.setTimeZone(timeZone);

        String vietnamTime = formatter.format(currentTime);
        boolean result = model.insertOne(
                new Document()
                        .append("email", email)
                        .append("password", password)
                        .append("created_at", vietnamTime));
        String response;
        if (result) {
            response = responseOK(null);
        } else {
            response = responseNotOk(400, "not ok", null);
        }
        return ResponseEntity.ok(response);
    }
}
