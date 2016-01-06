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
     *先使用get请求获取prelogin中返回的参数 在后面的登陆中进行使用
     *
     * http://login.sina.com.cn/sso/prelogin.php?entry=account&callback=sinaSSOController.preloginCallBack&su=MTUwMDgxMTA1OSU0MHFxLmNvbQ%3D%3D&rsakt=mod&client=ssologin.js(v1.4.15)&_
     * =1451465771827
     *
     *  @return map prelogin.php 中返回的值 放入map中
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
     * 这一步为登录操作 具体的用户名和密码的加密是在网上进行百度的
     *
     * 百度rsa2加密算法  这个算法为新浪自己写的一个加密算法 使用javascript加密
     * 跟12306一样都是需要加载下来js以后  使用java进行加密
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
