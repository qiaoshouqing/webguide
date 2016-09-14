import top.glimpse.webguide.util.HostInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by joyce on 16-8-17.
 */
public class GetIp {

    public static void main(String[] args) {
        InetAddress inet3 = null;
        try {
            inet3 = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(inet3);
        System.out.println(inet3.getHostName());
        System.out.println(inet3.getHostAddress());
    }


}
