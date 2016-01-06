package com.inga.action;

import com.inga.utils.log.PlatformLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by abing on 2016/1/6.
 */
public class FollowWeiboInfoAction {

    /**
     *
     * @param html
     */
    public static void getWeiboInfo(String html){
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("div");
        for (Element ele : elements){
            String div = ele.attr("class");
            boolean hasId = ele.hasAttr("id");
            if (div.equals("c") && hasId) {
                PlatformLogger.message("WEIBO INFO : "  + ele.text());
            }
        }
    }

}
