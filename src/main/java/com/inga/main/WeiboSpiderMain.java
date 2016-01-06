package com.inga.main;

import com.inga.action.FollowAction;
import com.inga.action.FollowWeiboInfoAction;
import com.inga.common.HeaderConstant;
import com.inga.common.UserConstant;
import com.inga.entity.FollowBean;
import com.inga.entity.WeiboBean;
import com.inga.headers.LoginHeader;
import com.inga.utils.ClientUtil;
import com.inga.utils.HttpGetRequest;
import com.inga.utils.HttpPostRequest;
import com.inga.utils.PrintOutUtils;
import com.inga.utils.log.PlatformLogger;
import com.inga.utils.parse.ParseHtml;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by abing on 2015/12/31.
 */
public class WeiboSpiderMain {
    private static String location;
    public static StringBuilder cookie = new StringBuilder();
    private static LoginHeader header = new LoginHeader();
    private static String loginAction = null;
    private static String pwdName = null;
    private static List<BasicNameValuePair> nvps  = new ArrayList<BasicNameValuePair>();

    /**
     * ��ѡ��ȡ��¼��ҳ����Ϣ
     *
     * http://login.weibo.cn/login/
     *
     */
    public void preLogin(){

        Map<String , String> map = header.getLoginHeader();
        CloseableHttpResponse response = HttpGetRequest.getRequest("http://login.weibo.cn/login/", map);
        String html = PrintOutUtils.getEntityToString(response);
        PlatformLogger.message("THE PRE LOGIN PAGE HTML : " + html);
        Document doc = Jsoup.parse(html);
        Elements inputs = doc.getElementsByTag("input");
        Element form = doc.getElementsByTag("form").get(0);
        loginAction = form.attr("action");
        for(int i = 0; i < inputs.size(); i++){
            Element input = inputs.get(i);
            // ��input�����ɸѡ������򣬲���ȡpassword name
            if(input.attr("type").equalsIgnoreCase("password")
                    && input.attr("name").startsWith("password_")){
                pwdName = input.attr("name");
            }
            // �����������Form
            else if(input.attr("type").equalsIgnoreCase("hidden")){
                nvps.add(new BasicNameValuePair(input.attr("name"), input.attr("value")));
            }
        }

    }

    /**
     * ��ҳ���еĵ�¼��Ϣ�е�ation Ȼ����е�¼���� ʹ��post��ʽ
     *
     * http://login.weibo.cn/login/    + loginAction
     *
     */
    public void loginActioon(){
        nvps.add(new BasicNameValuePair("mobile" , UserConstant.username));
        nvps.add(new BasicNameValuePair(pwdName , UserConstant.password));
        nvps.add(new BasicNameValuePair("submit", "��¼"));
        CloseableHttpResponse response = HttpPostRequest.postRequest("http://login.weibo.cn/login/" + loginAction, nvps);
        location = ClientUtil.getHeaderValue(response , HeaderConstant.Location);
        cookie.append(ClientUtil.getCookieValue(response));
        String html = PrintOutUtils.getEntityToString(response);
        PlatformLogger.message("THE��LOGIN ACTION PAGE : " + html);

    }


    /**
     *
     * ��¼�Ժ�  ˢ��ҳ��  ��ȡ��¼�Ժ����ҳ����Ϣ
     *
     * http://newlogin.sina.cn/crossDomain/?g=4uvS2a691iFbeMvwxkN8u8br37P&t=1451872100&m=a2c0&r=&u=http%3A%2F%2Fweibo.cn%3Fgsid%3D4uvS2a691iFbeMvwxkN8u8br37P%26PHPSESSID%3D%26vt%3D4&cross=1&st=ST-MTk1MDU4NTQ4NQ==-1451872100-yf-92C3B217696D9293EAECCD9612CF250F,
     * ST-MTk1MDU4NTQ4NQ==-1451872100-yf-DEA57B8ED3F3117247AE1F4F72DA076B&vt=4
     *
     */
    public void loginMain(){

        Map<String , String> map = header.getNewLogin(cookie.toString());
        String strParam = location.split("\\?")[1];
        String[] ss = strParam.split("&");
        for (String s : ss){
            if (s.indexOf("g=") != -1){
                location = "gsid=" + s.split("=")[1];
            }
        }
        CloseableHttpResponse response = HttpGetRequest.getRequest("http://weibo.cn/?" + location, map);
        String html = PrintOutUtils.getEntityToString(response);
        ParseHtml.printMainPageMsg(html);
        PlatformLogger.message("THE��LOGIN MAIN MESSAGE : " + html);

    }

    /**
     *  ��ȡ�˺��е�˽��   ����ӡ����  �鿴
     *
     *   PrintOutUtils.printEntity(response);
     *
     */
    public void privateMsg(){

        Map<String , String> map = header.getUnRead(cookie.toString());
        CloseableHttpResponse response = HttpGetRequest.getRequest("http://weibo.cn/msg/?tf=5_010", map);
        String html = PrintOutUtils.getEntityToString(response);
        PlatformLogger.message("THE PRIVATE MESSAGE PAGE HTML : " + html);
        ParseHtml.printPrivateMsg(html);

    }

    /**
     * �鿴��ע��
     *
     * http://weibo.cn/1950585485/follow?vt=4&PHPSESSID=
     * @param args
     */

    public void getFollow() {
        Map<String , String> map = header.getFollow(cookie.toString());
        CloseableHttpResponse response = HttpGetRequest.getRequest("http://weibo.cn/1950585485/follow", map);
        String html = PrintOutUtils.getEntityToString(response);
        PlatformLogger.message("THE FOLLOW PAGE HTML : " + html);
        FollowAction.getAllFollow(html, cookie.toString());

    }


    /**
     * ������й�ע�˵ĵ�ǰҳ���΢����Ϣ
     *
     */
    public void getFollowWeiboInfo(){
        List<FollowBean> list = FollowAction.list;
        if (list.size() == 0){
            return;
        }
        Map<String , String> map = header.getFollow(cookie.toString());
        for (FollowBean bean : list){
            CloseableHttpResponse response = HttpGetRequest.getRequest(bean.getUrl() , map);
            String html = PrintOutUtils.getEntityToString(response);
            PlatformLogger.message("===================================================================================\n");
            PlatformLogger.message("THE PEOPLE WEIBO MAIN PAGE INFO : " + html);
            PlatformLogger.message(" THE PEOPLE " + bean.getName() + "  (  " + bean.getEnname() + "  )  WEIBO : \n" );
            FollowWeiboInfoAction.getWeiboInfo(html);
            PlatformLogger.message("===================================================================================\n");
        }

    }





    public static void main(String[] args) {
        WeiboSpiderMain spiderMain = new WeiboSpiderMain();
        spiderMain.preLogin();      //��ȡ��¼ҳ��
        spiderMain.loginActioon();    //��¼����
        spiderMain.loginMain();     //��ȡ��¼�Ժ����ҳ��
        spiderMain.privateMsg();  //��ȡ˽����Ϣ
        spiderMain.getFollow();     //��ȡ���еĹ�ע����Ϣ
        spiderMain.getFollowWeiboInfo();

//        HttpClient client

    }
}
