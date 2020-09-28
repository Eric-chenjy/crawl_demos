package com.eric.crawl;

import com.eric.crawl.utils.StringTransfer;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class AppMain {
    public static void main(String[] args) {
        String requestHeaders = "GET /shakespeare/notes/7755084/included_collections?page=1&count=7 HTTP/1.1\n" +
                "Host: www.jianshu.com\n" +
                "Connection: keep-alive\n" +
                "Pragma: no-cache\n" +
                "Cache-Control: no-cache\n" +
                "Accept: application/json\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36\n" +
                "Sec-Fetch-Site: same-origin\n" +
                "Sec-Fetch-Mode: cors\n" +
                "Sec-Fetch-Dest: empty\n" +
                "Referer: https://www.jianshu.com/p/9aa969dd1b4d\n" +
                //"Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9,zh-TW;q=0.8,en-US;q=0.7,en;q=0.6\n" +
                "Cookie: _ga=GA1.2.1492798613.1581909568; __gads=ID=5bf9139458c1a0bb-223403b02ac2006f:T=1592812003:RT=1592812003:S=ALNI_MYaLy-J4qthj139K_NP5UyUK1Vntg; __yadk_uid=7nJq5dvmgmAMwQyDifgbeWmjl30RUDnF; locale=zh-CN; _gid=GA1.2.201519707.1601169105; read_mode=day; default_font=font2; Hm_lvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1601023493,1601169105,1601169531,1601169934; Hm_lpvt_0c0e9d9b1e7d617b3e6842e85b9fb068=1601199950; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2216c5c8094d61ac-0a0b4a62519c71-c343162-1327104-16c5c8094d9a16%22%2C%22%24device_id%22%3A%2216c5c8094d61ac-0a0b4a62519c71-c343162-1327104-16c5c8094d9a16%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_utm_source%22%3A%22desktop%22%2C%22%24latest_utm_medium%22%3A%22not-signed-in-like-note-btn-in-bottom%22%2C%22%24latest_utm_campaign%22%3A%22maleskine%22%2C%22%24latest_utm_content%22%3A%22note%22%7D%7D; signin_redirect=https://www.jianshu.com/p/9aa969dd1b4d\n";
        String url = "https://www.jianshu.com/shakespeare/notes/7755084/included_collections?page=1&count=7";
        HashMap hashMap = StringTransfer.stringToHashMap(requestHeaders);
        Headers headers = StringTransfer.SetHeaders(hashMap);
        doGet(headers, url);
        //doPost();
    }

    private static void doGet(Headers headers, String url) {
        //   获取OkhttpClient对象
        OkHttpClient client = new OkHttpClient();
        //    构建request对象
        Request request = new Request.Builder().get().headers(headers).url(url).build();
        //将request封装为call
        Call call = client.newCall(request);
        //    异步调用，设置回调函数
        call.enqueue(new Callback() {
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("失败");
            }

            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                System.out.println(result);
            }
        });

    }

}
