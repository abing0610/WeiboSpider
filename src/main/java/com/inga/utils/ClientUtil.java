package com.inga.utils;


import com.inga.common.HeaderConstant;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by abing on 2015/12/29.
 */
public class ClientUtil {

    /**
     * HttpClient 最新版本的https 请求
     *
     * 获取所有的sll安全证书
     *
     * @return
     */
    public static CloseableHttpClient createSSLClient(){
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
                    null, new TrustStrategy() {
                        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    }
            ).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        return HttpClients.createDefault();
    }

    /**
     * 系统默认的返回 CloseableHttpClient 请求的方法 跟原来的方法一样
     *
     * @return
     */
    public static CloseableHttpClient createDefaultClient(){
        return  HttpClients.createDefault();
    }


    /**
     *
     * 过时的请求ssl的方法
     * 初始化httpclient,登录https使用，使用的是ssl协议
     *
     */
    @Deprecated
    public static HttpClient gethttpclient(){
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(sslcontext);
            ClientConnectionManager ccm = new DefaultHttpClient().getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 80000);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 80000);
            HttpClient httpclient = new DefaultHttpClient(ccm, params);
            httpclient.getParams().setParameter(HTTP.USER_AGENT,
                    "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; BOIE9;ZHCN)");
            return httpclient;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 过时的请求方法
     */
    @Deprecated
    private static X509TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    /**
     * 去除返回的报文头中的制定信息
     * @param response
     * @return
     */
    public static String getHeaderValue(CloseableHttpResponse response , String info){
        if (response == null){
            return null;
        }
        String value = null;
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            if (header.getName().equals(info)) {
                value = header.getValue();
            }
        }
        return value;
    }

    /**
     * 去除返回的报文头中的制定信息
     * @param response
     * @return
     */
    public static String getCookieValue(CloseableHttpResponse response){
        if (response == null){
            return null;
        }
        StringBuilder  value = new StringBuilder();
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            if (header.getName().equals(HeaderConstant.Set_Cookie)) {
                value.append(header.getValue() + ";");
            }
        }
        return value.toString();
    }

}
