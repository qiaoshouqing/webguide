package top.glimpse.webguide.util;

import java.net.InetAddress;

/**
 * Created by joyce on 16-8-16.
 */
public class HostInfo {

    public static String getHostIp() {
        InetAddress ia = null;
        String localip = "";
        try {
            ia = ia.getLocalHost();
            localip=ia.getHostAddress();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return localip;
    }


}
