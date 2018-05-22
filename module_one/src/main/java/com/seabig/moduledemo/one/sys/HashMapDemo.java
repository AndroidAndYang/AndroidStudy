package com.seabig.moduledemo.one.sys;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author YJZ
 *         date 2018/5/14
 *         description
 */

public class HashMapDemo {

    public static void main(String[] args)
    {
        HashMap<String, String> hashMap = new HashMap<>(10);

        Map<String,String> linkHashMap = new LinkedHashMap<>(1);

        System.out.println(indexFor(1,10));

    }

    static int indexFor(int h, int length) {
        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
        return h & (length-1);
    }
}
