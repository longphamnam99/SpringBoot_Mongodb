package net.codejava.CodeJavaApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.codejava.CodeJavaApp.core.MyController;
import net.codejava.CodeJavaApp.models.ClassRoom_model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.*;

@RestController
public class ClassRoom_controller extends MyController {
    @GetMapping("/api/class_room/list")
    public static ResponseEntity<String> list() {
        ClassRoom_model model = new ClassRoom_model();
        List<Document> results = model.getList();

        JSONArray jsonArray = new JSONArray();
        for (Document document : results) {
            JSONObject jsonObject = new JSONObject(document.toJson());
            jsonArray.put(jsonObject);
        }

        return ResponseEntity.ok(responseOK(jsonArray));
    }

    @PostMapping("/api/class_room/add")
    public static ResponseEntity<?> add(@RequestParam("major") String major,
            @RequestParam("name") String name,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        ClassRoom_model model = new ClassRoom_model();
        Date currentTime = new Date();

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.setTimeZone(timeZone);

        String vietnamTime = formatter.format(currentTime);
        boolean result = model.insertOne(
                new Document()
                        .append("major", new ObjectId(major))
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

    @PostMapping("/api/class_room/update/{id}")
    public static ResponseEntity<?> update(
            @PathVariable("id") String id,
            @RequestParam("major") String major,
            @RequestParam("name") String name,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        ClassRoom_model model = new ClassRoom_model();
        Document doc = new Document()
                .append("major", new ObjectId(major))
                .append("name", name);
        if (model.update(id, doc)) {
            return ResponseEntity.ok(responseOK(null));
        }
        return ResponseEntity.ok(responseNotOk(200, "not ok", null));
    }

    @DeleteMapping("/api/class_room/delete/{id}")
    public static ResponseEntity<?> delete(
            @PathVariable("id") String id,
            @RequestHeader("Authorization") String authorizationHeader) {
        if (!checkAuthor(authorizationHeader)) {
            return ResponseEntity.ok(responseNotOk(400, "not ok", null));
        }
        ClassRoom_model model = new ClassRoom_model();
        if (model.delete(id)) {
            return ResponseEntity.ok(responseOK(null));
        }
        return ResponseEntity.ok(responseNotOk(400, "not ok", null));
    }
}
