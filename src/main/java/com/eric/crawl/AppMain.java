package com.eric.crawl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eric.crawl.beans.IndustryDto;
import com.eric.crawl.utils.CrawlUtils;
import com.eric.crawl.utils.ExcelWriter;
import okhttp3.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AppMain {
    public static void main(String[] args) throws IOException {
        String requestHeaders = ":authority: www.zhipin.com\n" +
                ":method: GET\n" +
                ":path: /wapi/zpCommon/data/oldindustry.json\n" +
                ":scheme: https\n" +
                "accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                "accept-encoding: gzip, deflate, br\n" +
                "accept-language: zh-CN,zh;q=0.9,zh-TW;q=0.8,en-US;q=0.7,en;q=0.6\n" +
                "cache-control: no-cache\n" +
                "cookie: lastCity=101010100; sid=sem_pz_bdpc_dasou_title; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1601348893; __zp_seo_uuid__=4e6d5cd4-37e0-4fa2-9b04-5110f268f1fc; __g=sem_pz_bdpc_dasou_title; __c=1601348941; __l=l=%2Fwww.zhipin.com%2Fbeijing%2F&r=&g=%2Fwww.zhipin.com%2Fbeijing%2F%3Fsid%3Dsem_pz_bdpc_dasou_title&friend_source=0&friend_source=0; __a=98690315.1572400199.1572400199.1601348941.6.2.2.2; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1601348943\n" +
                "pragma: no-cache\n" +
                "sec-fetch-dest: document\n" +
                "sec-fetch-mode: navigate\n" +
                "sec-fetch-site: none\n" +
                "sec-fetch-user: ?1\n" +
                "upgrade-insecure-requests: 1\n" +
                "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36\n";
        String url = "https://www.zhipin.com/wapi/zpCommon/data/oldindustry.json";
        HashMap hashMap = CrawlUtils.stringToHashMap(requestHeaders);
        Headers headers = CrawlUtils.SetHeaders(hashMap);
        ArrayList<IndustryDto> industryDtos = doGet(headers, url);
        String filePath = "F:\\git_code\\crawl_demos\\files\\test.xlsx";
        Workbook workbook = ExcelWriter.exportData(industryDtos);
        FileOutputStream fileOutputStream = null;
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);
        fileOutputStream.flush();
    }

    private static ArrayList<IndustryDto> doGet(Headers headers, String url) throws IOException {
        final ArrayList<IndustryDto> industryDtolist = new ArrayList<>();

        //   获取OkhttpClient对象
        OkHttpClient client = new OkHttpClient();
        //    构建request对象
        Request request = new Request.Builder().get().headers(headers).url(url).build();
        //将request封装为call
        Call call = client.newCall(request);
        //同步调用
        Response response = call.execute();
        String result = response.body().string();
        JSONArray jsonArray = JSONObject.parseObject(result).getJSONArray("zpData");

        for (int i = 0; i < jsonArray.size(); i++) {
            IndustryDto industryDto = new IndustryDto();
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            industryDto.setCode(jsonObject.getInteger("code"));
            industryDto.setName(jsonObject.getString("name"));
            industryDtolist.add(industryDto);}
        return industryDtolist;
            //    异步调用，设置回调函数
            //call.enqueue(new Callback() {
            //    public void onFailure(@NotNull Call call, @NotNull IOException e) {
            //        System.out.println("失败");
            //    }
            //
            //    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            //        String result = response.body().string();
            //        JSONArray jsonArray=JSONObject.parseObject(result).getJSONArray("zpData");
            //
            //        for (int i=0;i<jsonArray.size();i++){
            //            IndustryDto industryDto=new IndustryDto();
            //            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            //            industryDto.setCode(jsonObject.getInteger("code"));
            //            industryDto.setName(jsonObject.getString("name"));
            //            industryDtolist.add(industryDto);
            //        }
            //
            //    }
            //});


        }

    }
