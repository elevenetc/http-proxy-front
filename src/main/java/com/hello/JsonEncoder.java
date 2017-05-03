package com.hello;

import com.google.gson.Gson;

/**
 * Created by eugene.levenetc on 18/04/2017.
 */
public class JsonEncoder {

    private static Gson gson = new Gson();

    public static <T> String toString(T object) {
        return gson.toJson(object);
    }

    public static <T> T toObject(String string, Class<T> clazz) {
        return gson.fromJson(string, clazz);
    }
}
