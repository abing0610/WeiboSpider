package com.inga;

import com.inga.utils.HttpGetRequest;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abing on 2015/12/29.
 */
public class App {
    public static void main(String[] args) {


        Map<String , String> map = new HashMap<String, String>();
        map.put("Host","github.com");
        map.put("User-Agent" , "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
        map.put("Accept" , "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        map.put("Accept-Language" , "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put("Accept-Encoding" , "gzip, deflate");
        map.put("Referer" , "https://github.com/");
        map.put("Cookie" , "logged_in=no; _gh_sess=eyJfZmxpcHBlcl9pZCI6ODE0ODg2MTg2NzY4MDg0NDM2Mzc3NTY5ODAwOTY3LCJsYXN0X3dyaXRlIjoxNDUxNDQxNTcwNTk1LCJzZXNzaW9uX2lkIjoiNGJlYThiMTAyYmM4MjVjZGRlZGJkY2Q2NzhlY2ZhNjciLCJjb250ZXh0IjoiLyIsInJlZmVycmFsX2NvZGUiOiJodHRwczovL2dpdGh1Yi5jb20vIiwiX2NzcmZfdG9rZW4iOiIyUGRxTzBwOGE5clY5My83d2pvanNTSFRpbVd6RkFXbTZyb3FXV091dFJBPSJ9--7d8ec8d8ae4f77e49f1d4d86ea99bc461d86f2b4; _octo=GH1.1.1456477275.1451441221; _ga=GA1.2.419594005.1451441221; _gat=1; tz=Asia%2FShanghai");
        map.put("Connection" , "keep-alive");
        map.put("Cache-Control" , "max-age=0");

        CloseableHttpResponse response = HttpGetRequest.getRequest("https://github.com/login", map);
        HttpEntity entity = response.getEntity();

        System.out.println(response);
        Header[] headers = response.getAllHeaders();

        for (Header header : headers){
            System.out.println(header.getName() + "  :  " + header.getValue());
        }

        System.out.println("-----------------------------");
        System.out.println(entity.toString());

//        EntityUtils.toString(entity);



        try {
            String str = EntityUtils.toString(entity);
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
//    public static void main(String[] args) {
//
//
////        CloseableHttpResponse response =  HttpGetRequest.getRequest("https://kyfw.12306.cn/otn/lcxxcx/init");
//        CloseableHttpResponse response =  HttpGetRequest.getRequest("http://www.zhihu.com/");
//        HttpEntity entity = response.getEntity();
//
//        Header[] headers = response.getAllHeaders();
//
////        int i = headers.length;
//
//        for (Header header : headers){
//            header.getName();
//            header.getValue();
//        }
//
//        try {
//            System.out.println(EntityUtils.toString(entity));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
