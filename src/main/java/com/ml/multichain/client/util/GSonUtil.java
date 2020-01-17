package com.ml.multichain.client.util;

import com.google.gson.Gson;

public class GSonUtil {
    private static final GSonUtil instance = new GSonUtil();
    private static final Gson gson = new Gson();

    private GSonUtil() {
    }

    public static GSonUtil getInstance() {
        return instance;
    }

    public String object2Json(Object obj) {
        return gson.toJson(obj);
    }

    public Object json2Object(String json, Class cla) {
        return gson.fromJson(json, cla);
    }
}
