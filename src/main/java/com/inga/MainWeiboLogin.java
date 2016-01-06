package com.inga;

import com.alibaba.fastjson.JSON;
import com.inga.common.HeaderConstant;
import com.inga.headers.LoginHeaders;
import com.inga.headers.PreLoginHeaders;
import com.inga.headers.WeiboHeaders;
import com.inga.utils.HttpGetRequest;
import com.inga.utils.HttpPostRequest;
import com.inga.utils.PrintOutUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by abing on 2015/12/30.
 */
public class MainWeiboLogin {

    private static Map<String , Object> preMap = null;

    private static StringBuilder cookieStr = new StringBuilder();

    /**
     *��ʹ��get�����ȡprelogin�з��صĲ��� �ں���ĵ�½�н���ʹ��
     *
     * http://login.sina.com.cn/sso/prelogin.php?entry=account&callback=sinaSSOController.preloginCallBack&su=MTUwMDgxMTA1OSU0MHFxLmNvbQ%3D%3D&rsakt=mod&client=ssologin.js(v1.4.15)&_
     * =1451465771827
     *
     *  @return map prelogin.php �з��ص�ֵ ����map��
     */
    public void preLogin(){
        CloseableHttpResponse response = HttpGetRequest.getRequest("http://login.sina.com.cn/sso/prelogin.php?entry=account&callback=sinaSSOController.preloginCallBack&su=MTUwMDgxMTA1OSU0MHFxLmNvbQ%3D%3D&rsakt=mod&client=ssologin.js(v1.4.15)&_="
                + new Date().getTime()
                , PreLoginHeaders.packageHeaders());
        HttpEntity entity = response.getEntity();
        try {
            String respStr = EntityUtils.toString(entity);
            respStr = respStr.substring(respStr.indexOf("(") + 1 , respStr.indexOf(")"));
            preMap = (Map<String, Object>) JSON.parse(respStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * ��һ��Ϊ��¼���� ������û���������ļ����������Ͻ��аٶȵ�
     *
     * �ٶ�rsa2�����㷨  ����㷨Ϊ�����Լ�д��һ�������㷨 ʹ��javascript����
     * ��12306һ��������Ҫ��������js�Ժ�  ʹ��java���м���
     *
     * https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=1451465771926
     *
     */
    public void loginPost(){
        if (preMap==null){
            return;
        }
        List<BasicNameValuePair> list = LoginHeaders.packageNVPS(preMap);
        CloseableHttpResponse httpResp = HttpPostRequest.postRequest("https://login.sina.com.cn/sso/login.php?client=ssologin.js(v1.4.15)&_=" +
                new Date().getTime(), list);
//        PrintOutUtils.printEntity(httpResp);
        Header[] headers = httpResp.getAllHeaders();
        for (Header header : headers) {
            if (header.getName().equals(HeaderConstant.Set_Cookie)){
                cookieStr.append(header.getValue() + ";");
            }
        }

    }

    /**
     *
     * http://login.sina.com.cn/member/my.php?entry=sso
     *
     */
    public void loginIndexPage(){
//        System.out.println("======================================================");
        Map<String , String> map = LoginHeaders.packageCookieHeaders(cookieStr.toString());
        CloseableHttpResponse response = HttpGetRequest.getRequest("http://login.sina.com.cn/member/my.php?entry=sso", map);
//        System.out.println("-------------------------------------");
//        PrintOutUtils.printEntity(response , "gb2312");

    }

    /**
     * http://weibo.com/
     *
     * http://weibo.com/u/1950585485/home?wvr=5
     *
     * http://4.56.web1.im.weibo.com/im
     *
     */
    public void weiboMainPage(){
        System.out.println(cookieStr.toString());
        Map<String , String> map = WeiboHeaders.packageHeaders(cookieStr.toString());
        CloseableHttpResponse response = HttpGetRequest.getRequest("http://weibo.com/u/1950585485/home?wvr=5" , map);

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        PrintOutUtils.printEntity(response , "gb2312");

    }



    public static void main(String[] args) {
        MainWeiboLogin login = new MainWeiboLogin();
        login.preLogin();
        login.loginPost();
        login.loginIndexPage();
        login.weiboMainPage();
    }
}
