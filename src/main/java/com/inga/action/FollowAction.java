package com.inga.action;

import com.inga.common.HeaderConstant;
import com.inga.entity.FollowBean;
import com.inga.entity.WeiboBean;
import com.inga.headers.LoginHeader;
import com.inga.headers.LoginHeaders;
import com.inga.utils.HttpGetRequest;
import com.inga.utils.PrintOutUtils;
import com.inga.utils.log.PlatformLogger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by abing on 2016/1/5.
 */
public class FollowAction {

    public static List<FollowBean> list = new ArrayList<FollowBean>();

    /**
     * 获取关注人的信息
     *
     * @param html
     */
    public static void getFollowInfo(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("table");
        for (Element ele : elements) {
            Elements eles = ele.getElementsByTag("a");
            Element aele = eles.get(1);
            String url = aele.attr("href");
            String name = aele.text();
            String enname = url.substring(url.lastIndexOf("/") + 1);
            int start = ele.toString().indexOf("<br") + 6;
            int end =  ele.toString().lastIndexOf("<br") - 1;
            String val = ele.toString().substring(start , end);
            FollowBean bean = new FollowBean();
            bean.setEnname(enname);
            bean.setName(name);
            bean.setUrl(url);
            bean.setStarNum(val);
            list.add(bean);
        }

    }

    /**
     * 循环获取所有页面的关注数目
     *
     * http://weibo.cn/1950585485/follow?page=2
     */
    public static void getAllFollow(String html , String cookie){
        int num = getFollowPageNum(html); //去的关注数页面总数
        PlatformLogger.message(" ALL PAGE NUM : " + num);
        for (int i = 2 ; i < num + 1 ; i++){
            getPageFollow(i , cookie);
        }
        printAllFollow();
    }

    /**
     *  使用get方式获取分页的关注人  并放入list中
     *
     * @param num
     * @param cookie
     */
    public static void getPageFollow(int num , String cookie){
        LoginHeader header = new LoginHeader();
        Map<String , String> map = header.getFollow(cookie);
        PlatformLogger.message("THE PAGE URL : " + "http://weibo.cn/1950585485/follow?page=" + num);
        CloseableHttpResponse response = HttpGetRequest.getRequest("http://weibo.cn/1950585485/follow?page=" + num, map);
        String html = PrintOutUtils.getEntityToString(response , "GB2312");
        getFollowInfo(html);

    }

    /**
     *  获取关注人页面的数目
     *
     * @return
     */
    private static int getFollowPageNum(String html){
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("input");
        int num = 0;
        for (Element ele  : elements) {
            if(ele.attr("name").equals("mp")){
                num = Integer.valueOf(ele.attr("value"));
            }
        }
        return num;
    }

    /**
     *打印输出所有的关注人信息
     *
     */
    public static void printAllFollow(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n  ALL FOLLOW NUM : " + list.size());
        for (FollowBean bean : list) {
            sb.append("\n").append(bean.getName())
                    .append(" ( ").append(bean.getEnname()).append(" ) \n               ")
                    .append("  FOLLOW NUM : ").append(bean.getStarNum())
                    .append(" THE WEIBO URL : ").append(bean.getUrl());
        }
        PlatformLogger.message("\n  THE FOLLOW PEOPLE INFO :  " + sb.toString());
    }
}
