package com.inga.headers;

import com.inga.common.HeaderConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abing on 2015/12/31.
 */
public class WeiboHeaders {

    /**
     *
     * @return
     */
    public static Map<String , String> packageHeaders(String cookie){
        Map<String , String> map = new HashMap<String, String>();
        map.put(HeaderConstant.Accept, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        map.put(HeaderConstant.Accept_Encoding , "gzip, deflate");
        map.put(HeaderConstant.Accept_Language ,  "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put(HeaderConstant.Connection , "keep-alive");
        map.put(HeaderConstant.Cookie , cookie);
        map.put(HeaderConstant.Host , "weibo.com");
        map.put(HeaderConstant.Referer , "http://login.sina.com.cn/member/my.php?entry=sso");
        map.put(HeaderConstant.User_Agent ,   "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");

        return map;
    }
}
