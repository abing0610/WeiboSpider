package com.inga.utils;

import com.inga.utils.log.PlatformLogger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by abing on 2015/12/30.
 */
public class PrintOutUtils {

    private static Logger logger= Logger.getLogger(PrintOutUtils.class);


    /**
     * ��ӡ���ص�CloseableHttpResponse�����з��ص�ֵ
     *
     * @param response
     */
    public static void printEntity(CloseableHttpResponse response){
        printEntity(response , null, null);
    }

    /**
     * ��ӡ���ص�CloseableHttpResponse�����з��ص�ֵ
     *
     * @param response
     */
    public static void printEntity(CloseableHttpResponse response , String charset){
        printEntity(response , null , charset);
    }


    /**
     * ��ӡ���ص�CloseableHttpResponse�����з��ص�ֵ
     *
     * @param response
     * @param log
     */
    public static void printEntity(CloseableHttpResponse response , Logger log , String charset){
        String str = null;
        HttpEntity entity = response.getEntity();
        try {
            if (charset == null){
                charset = "UTF-8";
            }
            str = EntityUtils.toString(entity , charset);
            PlatformLogger.message("THE ENTITY MESSAGE IS :  " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ն���CloseableHttpResponse ��������Ҫ����Ϣ
     *
     * @param response
     * @return  str  ���ص���ҳ��ϢString
     */
    public static String getEntityToString(CloseableHttpResponse response , String charset){
        String str = null;
        HttpEntity entity = null;
        if (response == null){
            return null;
        }
        if (charset == null){
            charset = "UTF-8";
        }
        try {
            entity = response.getEntity();
            str = EntityUtils.toString(entity , charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  str;
    }

    /**
     * ���ն���CloseableHttpResponse ��������Ҫ����Ϣ
     *
     * @param response
     * @return  str  ���ص���ҳ��ϢString
     */
    public static String getEntityToString(CloseableHttpResponse response ){

        return  getEntityToString(response , null);
    }


}
