package com.inga;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by abing on 2015/12/29.
 */
public class HttpGetTest {

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

//        httpClient.getParams()

        HttpGet get = new HttpGet("http://www.12306.cn/mormhweb/");

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(500)
                .setConnectTimeout(500)
                .setSocketTimeout(500)
                .build();
        get.setConfig(requestConfig);
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

        try {
            CloseableHttpResponse response = httpClient.execute(get);

//            System.out.println(" status code : " + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity , "utf-8");

            System.out.println(body);

//            Header[] header = response.getAllHeaders();
//            header.

        } catch (IOException e) {
            e.printStackTrace();
        }

        get.releaseConnection();

    }
}
