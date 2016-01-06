package com.inga.headers;

import com.inga.common.HeaderConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abing on 2015/12/30.
 */
public class PreLoginHeaders {

    /**
     *
     * @return
     */
    public static Map<String , String> packageHeaders(){
        Map<String , String> map = new HashMap<String, String>();
        map.put(HeaderConstant.Accept, "*/*");
        map.put(HeaderConstant.Accept_Encoding , "gzip, deflate");
        map.put(HeaderConstant.Accept_Language ,  "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        map.put(HeaderConstant.Connection , "keep-alive");
        map.put(HeaderConstant.Host , "login.sina.com.cn");
        map.put(HeaderConstant.Referer , "http://login.sina.com.cn/?r=%2Fmember%2Fmy.php%3Fentry%3Dsso");
        map.put(HeaderConstant.User_Agent ,   "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");

        return map;
    }
}
