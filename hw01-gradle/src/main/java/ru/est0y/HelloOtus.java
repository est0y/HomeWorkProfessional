package ru.est0y;

import com.google.common.collect.*;

public class HelloOtus {
    public static void main(String[] args) {
        String key = "key";
        Multimap<String, String> map = ArrayListMultimap.create();
        map.put(key, "first");
        map.put(key, "second");
        System.out.println(map.get(key));
    }
}
