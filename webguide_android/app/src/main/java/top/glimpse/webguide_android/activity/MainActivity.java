package top.glimpse.webguide_android.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import top.glimpse.webguide_android.R;
import top.glimpse.webguide_android.util.FileUtil;
import top.glimpse.webguide_android.util.GetThread;
import top.glimpse.webguide_android.util.IsNet;
import top.glimpse.webguide_android.util.MD5;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FileUtil fileUtil;
    private Activity activity = MainActivity.this;

    private TextView about;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0x123:

                    about.setText("请拨打客服电话:"+ (String)msg.obj +"。上报设备标识码获得激活码完成激活。");

                    break;
                case 0x444:

                    String result = (String) msg.obj;
                    if (result.equals("yes")) {
                        new Thread(new GetThread(handler, "/getPhone")).start();
                    }
                    else {
                        Toast.makeText(activity, "网络不可用", Toast.LENGTH_LONG).show();
                    }

                    break;
                default:
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        final String DEVICE_ID = tm.getDeviceId();

        fileUtil = new FileUtil(activity, "/setting", "setting.se");
        String result = "";


        if(!(result = fileUtil.read()).equals("")) {
            if(validate(DEVICE_ID, result)) {
                Intent intent = new Intent(MainActivity.this, WebguideActivity.class);
                startActivity(intent);
                finish();
            }
        }


        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.deviceid);
        tv.setText("您的设备码是：" + DEVICE_ID);
        final EditText et = (EditText) findViewById(R.id.activation);


        about = (TextView) findViewById(R.id.about);
        new Thread(){

            @Override
            public void run() {
                IsNet.check(handler);
            }


        }.start();





        Button bn = (Button) findViewById(R.id.doactive);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = et.getText().toString();

                if(validate(DEVICE_ID, code)) {
                    fileUtil.write(code);
                    Intent intent = new Intent(MainActivity.this, WebguideActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(activity, "激活码不正确", Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    private boolean validate(String DEVICE_ID, String activeCode) {
        String result = DEVICE_ID + getString();

        String md5 = MD5.GetMD5Code(result ,true);

        Log.i(TAG, "md5:" + md5);

        return md5.equalsIgnoreCase(activeCode);

    }

    public native String getString();
    static {
        System.loadLibrary("MyJni");
    }

}
