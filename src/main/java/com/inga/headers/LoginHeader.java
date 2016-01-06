package com.inga.headers;

import com.inga.common.HeaderConstant;
import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abing on 2015/12/31.
 */
public class LoginHeader {

    /**
     * 组装的    http://login.weibo.cn/login/      请求头信息
     *
     * @return
     */
    public Map<String , String> getLoginHeader(){
        Map<String , String> map = new HashMap<String, String>();
        map.put(HeaderConstant.Accept , "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        map.put(HeaderConstant.Accept_Encoding , "gzip, deflate");
        map.put(HeaderConstant.Accept_Language , "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put(HeaderConstant.Connection , "keep-alive");
        map.put(HeaderConstant.Host , "login.weibo.cn");
        map.put(HeaderConstant.User_Agent , "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
        return map;
    }

    public Map<String , String> getLoginActionHeader(){
        Map<String , String> map = new HashMap<String, String>();
        map.put(HeaderConstant.User_Agent , "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
        map.put(HeaderConstant.Referer , "http://login.weibo.cn/login/ ");
        map.put(HeaderConstant.Content_Type , "application/x-www-form-urlencoded");
        return map;
    }

    /**
     * 组装的    http://login.weibo.cn/login/      请求头信息
     *
     * @return
     */
    public Map<String , String> getLoginHeader(String cookie){
        Map<String , String> map = new HashMap<String, String>();
        map.put(HeaderConstant.Accept , "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        map.put(HeaderConstant.Accept_Encoding , "gzip, deflate");
        map.put(HeaderConstant.Accept_Language , "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put(HeaderConstant.Connection , "keep-alive");
        map.put(HeaderConstant.Host , "login.weibo.cn");
        map.put(HeaderConstant.User_Agent , "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
        return map;
    }

    /**
     * http://newlogin.sina.cn/crossDomain/?g=4uvS2a691iFbeMvwxkN8u8br37P&t=1451872100&m=a2c0&r=&u=http%3A%2F%2Fweibo.cn%3Fgsid%3D4uvS2a691iFbeMvwxkN8u8br37P%26PHPSESSID%3D%26vt%3D4&cross=1&st=ST-MTk1MDU4NTQ4NQ==-1451872100-yf-92C3B217696D9293EAECCD9612CF250F,ST-MTk1MDU4NTQ4NQ==-1451872100-yf-DEA57B8ED3F3117247AE1F4F72DA076B&vt=4
     */

    public Map<String , String> getNewLogin(String cookie){
        Map<String , String>  map = new HashMap<String, String>();
        map.put(HeaderConstant.Accept , "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        map.put(HeaderConstant.Accept_Encoding , "gzip, deflate");
        map.put(HeaderConstant.Accept_Language , "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put(HeaderConstant.Connection , "keep-alive");
        map.put(HeaderConstant.Cookie , cookie);
        map.put(HeaderConstant.Host , "weibo.cn");
        map.put(HeaderConstant.Referer , "http://login.weibo.cn/login/");
        map.put(HeaderConstant.User_Agent , "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");

        return map;
    }

    /**
     * http://weibo.cn/msg/?unread=5&vt=4
     *
     */
    public Map<String , String> getUnRead(String cookie) {
        Map<String , String> map = new HashMap<String, String>();
        map.put(HeaderConstant.Accept , "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        map.put(HeaderConstant.Accept_Encoding , "gzip, deflate");
        map.put(HeaderConstant.Accept_Language , "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put(HeaderConstant.Connection , "keep-alive");
        map.put(HeaderConstant.Cookie , cookie);
        map.put(HeaderConstant.Host , "weibo.cn");
        map.put(HeaderConstant.Referer , "http://weibo.cn/");
        map.put(HeaderConstant.User_Agent , "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");

        return map;
    }

    /**
     * http://weibo.cn/1950585485/follow?vt=4&PHPSESSID=
     *
     */
    public Map<String , String> getFollow(String cookie) {
        Map<String , String> map = new HashMap<String, String>();
        map.put(HeaderConstant.Accept , "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        map.put(HeaderConstant.Accept_Encoding , "gzip, deflate");
        map.put(HeaderConstant.Accept_Language , "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put(HeaderConstant.Connection , "keep-alive");
        map.put(HeaderConstant.Cookie , cookie);
        map.put(HeaderConstant.Host , "weibo.cn");
        map.put(HeaderConstant.Referer , "http://weibo.cn/");
        map.put(HeaderConstant.User_Agent , "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");

        return map;
    }

}
