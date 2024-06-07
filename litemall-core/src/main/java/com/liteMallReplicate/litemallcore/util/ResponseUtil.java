package com.liteMallReplicate.litemallcore.util;

import java.util.Map;
import java.util.HashMap;

public class ResponseUtil {
    public static Object ok() {
        Map<String, Object> obj = new HashMap<>();
        obj.put("error", 0);
        obj.put("msg", "ok");
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<>();
        obj.put("error", 0);
        obj.put("msg", "ok");
        obj.put("data", data);
    }
}
