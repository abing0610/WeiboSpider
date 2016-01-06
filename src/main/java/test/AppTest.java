package test;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * Created by abing on 2015/12/31.
 */
public class AppTest {
    public static void main(String[] args) {
//        StringBuilder sb = new StringBuilder();
//        System.out.println(sb);

        String s = "http://newlogin.sina.cn/crossDomain/?g=4ujn2a881Uw7nrmZDRCPm8br37P&t=1451873144&m=128d&r=&u=http%3A%2F%2Fweibo.cn%3Fgsid%3D4ujn2a881Uw7nrmZDRCPm8br37P%26PHPSESSID%3D%26vt%3D4&cross=1&st=ST-MTk1MDU4NTQ4NQ==-1451873144-tc-87D373286188EF7B8C0F3839FEBBE6BC,ST-MTk1MDU4NTQ4NQ==-1451873144-tc-7D4453FA2B42EA1702D862F416F3028D&vt=4";

        String s0 = s.split("\\?")[1];
        String[] ss = s0.split("&");
        for (String s1: ss){
            if (s1.indexOf("g=") != -1) {

            }
        }
        System.out.println(s0);
    }
}
