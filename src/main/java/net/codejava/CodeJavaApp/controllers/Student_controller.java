package net.codejava.CodeJavaApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.codejava.CodeJavaApp.core.MyController;
import net.codejava.CodeJavaApp.models.Student_model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.*;

@RestController
public class Student_controller extends MyController {
    @GetMapping("/api/student/list")
    public static ResponseEntity<String> list() {
        Student_model model = new Student_model();
        List<Document> results = model.getList();

        JSONArray jsonArray = new JSONArray();
        for (Document document : results) {
            JSONObject jsonObject = new JSONObject(document.toJson());
            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(responseOK(jsonArray));
    }

    @PostMapping("/api/student/add")
    public static ResponseEntity<?> add(@RequestParam("name") String name,
            @RequestParam("class_room_id") String class_room_id,
            @RequestParam("gender") String gender,
            @RequestParam("date_of_birth") String date_of_birth,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        Student_model model = new Student_model();
        Date currentTime = new Date();

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.setTimeZone(timeZone);

        String vietnamTime = formatter.format(currentTime);
        boolean result = model.insertOne(
                new Document()
                        .append("name", name)
                        .append("class_room_id", new ObjectId(class_room_id))
                        .append("gender", gender)
                        .append("date_of_birth", date_of_birth)
                        .append("created_at", vietnamTime));
        String response;
        if (result) {
            response = responseOK(null);
        } else {
            response = responseNotOk(400, "not ok", null);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/student/update/{id}")
    public static ResponseEntity<?> update(
            @PathVariable("id") String id,
            @RequestParam("name") String name,
            @RequestParam("class_room_id") String class_room_id,
            @RequestParam("gender") String gender,
            @RequestParam("date_of_birth") String date_of_birth,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        Student_model model = new Student_model();
        Document doc = new Document()
                .append("name", name)
                .append("class_room_id", new ObjectId(class_room_id))
                .append("gender", gender)
                .append("date_of_birth", date_of_birth);
        if (model.update(id, doc)) {
            return ResponseEntity.ok(responseOK(null));
        }
        return ResponseEntity.ok(responseNotOk(200, "not ok", null));
    }

    @DeleteMapping("/api/student/delete/{id}")
    public static ResponseEntity<?> delete(
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        Student_model model = new Student_model();
        if (model.delete(id)) {
            return ResponseEntity.ok(responseOK(null));
        }
        return ResponseEntity.ok(responseNotOk(400, "not ok", null));
    }
}
