package com.inga.utils.parse;

import com.inga.entity.WeiboBean;
import com.inga.utils.log.PlatformLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abing on 2016/1/4.
 */
public class ParseHtml {

    public static List<WeiboBean> list = new ArrayList<WeiboBean>();

    /**
     * 解析 “消息” 里面的信息 提取出来 打印出来 方便查看
     *
     * @param html
     */
    public static void printPrivateMsg(String html){
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("div");
        for (Element el :elements){
            if (el.attr("class").equals("c") && el.toString().indexOf("img") != -1) {
                WeiboBean bean = new WeiboBean();
                Elements divs = el.getElementsByTag("a");
                Element ea = divs.get(0);
                bean.setEnname(ea.attr("href").substring(1));
                bean.setName(ea.text());
                String name = el.text();
                int start = name.indexOf(":");
                String val = name.substring(start+1);
                bean.setValue(val);
                list.add(bean);
            }
        }

        printStart("PRINT PRIVATE MESSAGE");
        for (WeiboBean bean1 : list) {
            PlatformLogger.message("--------------------------------------------------------------------------------");
            StringBuilder sb = new StringBuilder();
            sb.append(bean1.getName());
            sb.append("  (  ");
            sb.append(bean1.getEnname());
            sb.append("  )    :      ");
            sb.append(bean1.getValue());
            PlatformLogger.message(sb.toString());
        }
        PlatformLogger.message("--------------------------------------------------------------------------------");
        printEnd("PRINT PRIVATE MESSAGE");
    }

    /**
     * 打印微博主页面的信息
     *
     * @param html
     */
    public static void printMainPageMsg(String html){
        list = new ArrayList<WeiboBean>();
        Document doc  = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("div");
        for (Element ele : elements) {
            String divClass = ele.attr("class");
            boolean divId = ele.hasAttr("id");
//            System.out.println("divclass :  " + divClass + " divid : " + divId);
//            PlatformLogger.message(" ++++++++++++++++++  divclass :  "  + divClass + " divid : " + divId);
            if (divClass.equals("c") && divId) {
                String text = ele.text();
                String name = text.substring(0, text.indexOf(":"));
                String val = text.substring(text.indexOf(":") + 1);
                WeiboBean bean = new WeiboBean();
                bean.setName(name);
                bean.setValue(val);
                list.add(bean);
            }
        }
        printStart("PRINT MAIN  MESSAGE" + list.size());
        for (WeiboBean bean : list) {
            StringBuilder sb  = new StringBuilder();
            sb.append(bean.getName());
            int len = bean.getName().length();
            for (int i = 0 ; i < 30 - len ; i++){
                sb.append("  ");
            }
            sb.append(":        ");
            sb.append(bean.getValue());
            PlatformLogger.message(sb);
        }
        printEnd("PRINT MAIN  MESSAGE");

    }


    /**
     * 打印开始的输出
     * @param info
     */
    private static void printStart(String info){
        StringBuilder sb  = new StringBuilder();
        sb.append("==============    " + info +"   start   ======================");
        PlatformLogger.message(sb.toString());
    }

    /**
     * 打印结束的输出
     * @param info
     */
    private static void printEnd(String info){
        StringBuilder sb  = new StringBuilder();
        sb.append("==============    " + info +"   end   ======================");
        PlatformLogger.message(sb.toString());
    }

    public static void main(String[] args) {
        String str = "http://t.cn/R4XPmzY?27分钟前?回复他?共6条对话";
        System.out.println(str.indexOf("回复"));
    }

}
