package top.glimpse.webguide_android.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by joyce on 16-8-15.
 */
public class GetThread implements Runnable{

    private Handler handler;
    private String url;
    public static String domain = "http://115.28.46.65:8080/webguide";

    public GetThread(Handler handler,String url) {
        this.handler = handler;
        this.url = url;
    }

    @Override
    public void run() {

        String result = GetPostUtil.doGet(domain + url);

        Message msg = new Message();
        msg.what = 0x123;
        msg.obj = result;
        handler.sendMessage(msg);

    }
}
