package com.inga.utils.log;

import java.net.*;
import java.util.Enumeration;

/**
 * Date:15/5/5
 * Time:16:52
 * Author Mr.Object
 */
public class LocalHost {
    private static NetworkInterface localNetworkInterface;
    private static String hostname;
    private static String localIPAddress = "";

    static {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress address;
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.getName().equals("lo")) {
                    localNetworkInterface = networkInterface;
                    Enumeration<InetAddress> enumeration = localNetworkInterface.getInetAddresses();
                    while (enumeration.hasMoreElements()) {
                        address = enumeration.nextElement();
                        if (address instanceof Inet4Address) {
                            localIPAddress = address.getHostAddress();
                            break;
                        }
                    }
                    if (!enumeration.hasMoreElements()) {
                        if (isBlank(localIPAddress)) {
                            try {
                                localIPAddress = InetAddress.getLocalHost().getHostAddress();
                            } catch (UnknownHostException e) {
                                PlatformLogger.error("init hostname error!", e);
                            }
                        }
                    }
                    break;
                }
            }

        } catch (SocketException e) {
            PlatformLogger.error("init LocalHost error!", e);
        }

        try {
            // 多数情况是因为本机ping计算机名 无法正确获取本机, 可以采用修改hosts的方式添加进去
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            PlatformLogger.error("init hostname error!", e);
            if (isBlank(hostname)) {
                hostname = "error_" + localIPAddress;
            }
        }
    }

    /**
     * 取本机的网卡接口
     *
     * @return LocalNetworkInterface
     */
    public static NetworkInterface getLocalNetworkInterface() {
        return localNetworkInterface;
    }

    /**
     * 取本机的机器名称
     *
     * @return MachineName
     */
    public static String getMachineName() {
        return hostname;
    }

    /**
     * 获取本机ip
     *
     * @return LocalIP
     */
    public static String getLocalIP() {
        return localIPAddress;
    }


        public static boolean isBlank(String str) {
            int strLen;
            if (str == null || (strLen = str.length()) == 0) {
                return true;
            }
            for (int i = 0; i < strLen; i++) {
                if ((Character.isWhitespace(str.charAt(i)) == false)) {
                    return false;
                }
            }
            return true;
        }

}
