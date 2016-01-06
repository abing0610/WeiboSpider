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
 * ģ��SSL����
 *
 * Created by abing on 2015/12/29.
 */
public class HttpGetRequest {


    /**
     *
     * get ���� ���� CloseableHttpResponse
     *
     * @param url
     * @return
     */
    public static CloseableHttpResponse getRequest(String url){
        return getRequest(url , null , null);
    }

    /**
     *
     * get ���� ���� CloseableHttpResponse
     *
     * @param url
     * @return
     */
    public static CloseableHttpResponse getRequest(String url , Map<String , String> map){
        return getRequest(url , null , map);
    }


    /**
     *
     * get ���� ���� CloseableHttpResponse ���������������
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
     * ����httpget�����������ͷ
     *
     * �ж�������������Լ���װ�Ķ�map ������һ�����������response����
     *
     * Ȼ���ٽ�����װ����ͷ
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
