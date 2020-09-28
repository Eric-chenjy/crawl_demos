package com.eric.crawl.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.Headers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StringTransfer {
    /**
     * 把从chrome拷贝的requestHearder转换为hashmap
     * @param str 要转换的requestHearder
     * @return HashMap
     */
    public static HashMap stringToHashMap(String str) {
        //System.out.println(str);
        String str_after=str.substring(str.indexOf("\n"),str.lastIndexOf("\n")).replaceAll(": ","\": \"").replaceAll("\n","\",\"").replaceFirst("\",","{").replaceAll("$","\"}");
        System.out.println(str_after);
        HashMap hashMap= JSON.parseObject(str_after, HashMap.class);
        return hashMap;
    }
    public static Headers SetHeaders(Map<String, String> headersParams) {
        Headers headers = null;
        okhttp3.Headers.Builder headersbuilder = new okhttp3.Headers.Builder();
        if (!headersParams.isEmpty()) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }
}
