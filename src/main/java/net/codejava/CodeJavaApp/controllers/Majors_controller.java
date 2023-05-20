package net.codejava.CodeJavaApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.codejava.CodeJavaApp.core.MyController;
import net.codejava.CodeJavaApp.models.Majors_model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bson.Document;
import org.json.*;

@RestController
public class Majors_controller extends MyController {

    @GetMapping("/api/major/list")
    public static ResponseEntity<String> list() {
        Majors_model majorsModel = new Majors_model();
        List<Document> results = majorsModel.select();
        JSONArray jsonArray = new JSONArray();
        for (Document document : results) {
            JSONObject jsonObject = new JSONObject(document.toJson());
            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(responseOK(jsonArray));
    }

    @PostMapping("/api/major/add")
    public static ResponseEntity<?> add(
            @RequestParam("code") String code,
            @RequestParam("name") String name,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        Majors_model majorsModel = new Majors_model();
        Date currentTime = new Date();

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.setTimeZone(timeZone);

        String vietnamTime = formatter.format(currentTime);
        boolean result = majorsModel.add_majors(
                new Document()
                        .append("code", code)
                        .append("name", name)
                        .append("created_at", vietnamTime));
        String response;
        if (result) {
            response = responseOK(null);
        } else {
            response = responseNotOk(400, "not ok", null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/major/update/{id}")
    public static ResponseEntity<?> update(
            @PathVariable("id") String id,
            @RequestParam("code") String code,
            @RequestParam("name") String name,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        Majors_model majorsModel = new Majors_model();
        Document doc = new Document()
                .append("code", code)
                .append("name", name);
        if (majorsModel.update(id, doc)) {
            return ResponseEntity.ok(responseOK(null));
        }
        return ResponseEntity.ok(responseNotOk(200, "not ok", null));
    }

    @DeleteMapping("/api/major/delete/{id}")
    public static ResponseEntity<?> delete(
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        Majors_model majorsModel = new Majors_model();
        if (majorsModel.delete(id)) {
            return ResponseEntity.ok(responseOK(null));
        }
        return ResponseEntity.ok(responseNotOk(400, "not ok", null));
    }
}
