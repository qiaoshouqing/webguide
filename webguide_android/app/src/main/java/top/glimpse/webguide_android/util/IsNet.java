package top.glimpse.webguide_android.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by joyce on 16-8-17.
 */
public class IsNet {

    private static final String TAG = "IsNet";

    public static void check(Handler handler) {

        Message msg = new Message();
        msg.what = 0x444;


        BufferedInputStream inputStream;
        String result = "";

        try {
            URL realUrl = new URL(GetThread.domain + "/isNet");
            URLConnection urlConnection = realUrl.openConnection();

            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            urlConnection.connect();

            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer)) != -1)
            {
                result += new String(buffer, 0, bytesRead);
            }

            Log.i(TAG, result);

            if(result.equals("yes")) {
                msg.obj = "yes";
            }
            else {
                msg.obj = "no";
            }


        } catch (Exception e) {
            e.printStackTrace();
            msg.obj = "no";
        }

        handler.sendMessage(msg);


    }


}
