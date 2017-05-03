package com.hello;

/**
 * Created by eugene.levenetc on 10/04/2017.
 */
public class Out {

    public static void tag(Object tag, Object obj) {
        pln(tag.getClass().getSimpleName(), obj);
    }

    public static void pln(String prefix, Object obj) {
        pln(prefix + ": " + obj);
    }

    public static void pln(Object obj) {
        System.out.println(obj);
    }
}
