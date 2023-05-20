package net.codejava.CodeJavaApp.core;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyController {
    public static String response(int code, String message, JSONArray data) {
        JSONObject responseJson = new JSONObject();
        // responseJson.put("author", request());
        responseJson.put("code", code);
        responseJson.put("message", code == 200 ? "ok" : message);
        if (data != null)
            responseJson.put("data", data);
        return responseJson.toString();
    }

    public static boolean checkAuthor(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }

        String token = authorizationHeader.substring(7);

        if (!"mytoken".equals(token)) {
            return false;
        }

        return true;
    }

    public static String responseOK(JSONArray data) {
        if (data != null)
            return response(200, "ok", data);
        else
            return response(200, "ok", null);
    }

    public static String responseNotOk(int code, String message, JSONArray data) {
        if (data != null)
            return response(code, !message.isEmpty() ? message : "not ok", data);
        else
            return response(code, !message.isEmpty() ? message : "not ok", null);
    }
}
