package com.inga.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 *
 * 模拟SSL请求
 *
 * Created by abing on 2015/12/29.
 */
public class HttpGetRequest {


    /**
     *
     * get 请求 返回 CloseableHttpResponse
     *
     * @param url
     * @return
     */
    public static CloseableHttpResponse getRequest(String url){
        return getRequest(url , null , null);
    }

    /**
     *
     * get 请求 返回 CloseableHttpResponse
     *
     * @param url
     * @return
     */
    public static CloseableHttpResponse getRequest(String url , Map<String , String> map){
        return getRequest(url , null , map);
    }


    /**
     *
     * get 请求 返回 CloseableHttpResponse 传入的是两个参数
     *
     * @param url
     * @param firstRes
     * @return response  CloseableHttpResponse
     */
    public static CloseableHttpResponse getRequest(String url ,
                                                   CloseableHttpResponse firstRes , Map<String , String> map){

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = new HttpGet(url);
        try {
            httpClient = ClientUtil.createSSLClient();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(20000)
                    .setConnectTimeout(20000)
                    .setSocketTimeout(20000)
                    .build();
            httpGet.setConfig(requestConfig);
//            if (firstRes == null){
//                httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
//            } else {
//                Header[] headers = firstRes.getAllHeaders();
//                for (Header header : headers){
//                    httpGet.setHeader(header);
//                }
//            }
            httpGet = setHttpGetHeader(httpGet, firstRes, map);

            response = httpClient.execute(httpGet);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//            httpGet.releaseConnection();

        }
        return response;
    }

    /**
     * 设置httpget请求的请求报文头
     *
     * 判断请求过来的是自己组装的额map 还是上一次请求过来的response对象
     *
     * 然后再进行组装报文头
     *
     * @param get
     * @param httpResponse
     * @param map
     * @return
     */
    private static HttpGet setHttpGetHeader(HttpGet get , CloseableHttpResponse httpResponse ,
                               Map<String , String> map){
        if (httpResponse == null && map == null ){
            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        } else if(httpResponse != null && map == null) {
            Header[] headers = httpResponse.getAllHeaders();
            for (Header header : headers) {
                get.setHeader(header);
            }
        } else if (map.size() != 0 && httpResponse == null){
            for (Map.Entry<String , String> me : map.entrySet()){
                get.setHeader(me.getKey() , me.getValue());
            }
        }
        return get;
    }

}
