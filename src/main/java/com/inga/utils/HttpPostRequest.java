package com.inga.utils;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by abing on 2015/12/30.
 */
public class HttpPostRequest {


    /**
     *
     * get ���� ���� CloseableHttpResponse
     *
     * @param url
     * @return
     */
    public static CloseableHttpResponse postRequest(String url , List<BasicNameValuePair> nvps){
        return postRequest(url, null, null , nvps);
    }

    /**
     *
     * get ���� ���� CloseableHttpResponse
     *
     * @param url
     * @return
     */
    public static CloseableHttpResponse postRequest(String url , Map<String ,
            String> map,List<BasicNameValuePair> nvps){
        return postRequest(url, null, map , nvps);
    }


    /**
     *
     * get ���� ���� CloseableHttpResponse ���������������
     *
     * @param url
     * @param firstRes
     * @return response  CloseableHttpResponse
     */
    public static CloseableHttpResponse postRequest(String url ,
                                                   CloseableHttpResponse firstRes , Map<String , String> map,
                                                    List<BasicNameValuePair> nvps){

        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            httpClient = ClientUtil.createSSLClient();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(20000)
                    .setConnectTimeout(20000)
                    .setSocketTimeout(20000)
                    .build();
            httpPost.setConfig(requestConfig);
            httpPost = setHttpPostHeader(httpPost, firstRes, map);
            if (nvps != null){
                httpPost.setEntity(new UrlEncodedFormEntity(nvps , HTTP.UTF_8));
            }
            response = httpClient.execute(httpPost);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * ����httpget�����������ͷ
     *
     * �ж�������������Լ���װ�Ķ�map ������һ�����������response����
     *
     * Ȼ���ٽ�����װ����ͷ
     *
     * @param post
     * @param httpResponse
     * @param map
     * @return
     */
    private static HttpPost setHttpPostHeader(HttpPost post , CloseableHttpResponse httpResponse ,
                                            Map<String , String> map){
        if (httpResponse == null && map == null ){
            post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        } else if(httpResponse != null && map == null) {
            Header[] headers = httpResponse.getAllHeaders();
            for (Header header : headers) {
                post.setHeader(header);
            }
        } else if (map.size() != 0 && httpResponse == null){
            for (Map.Entry<String , String> me : map.entrySet()){
                post.setHeader(me.getKey() , me.getValue());
            }
        }
        return post;
    }
}
