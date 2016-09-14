package top.glimpse.webguide_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import top.glimpse.webguide_android.service.DownloadService;
import top.glimpse.webguide_android.util.GetThread;
import top.glimpse.webguide_android.R;
import top.glimpse.webguide_android.adapter.WebsiteAdapter;
import top.glimpse.webguide_android.entity.WebsiteEntity;
import top.glimpse.webguide_android.util.IsNet;

public class WebguideActivity extends AppCompatActivity {

    private Type WebsiteListType = new TypeToken<List<WebsiteEntity>>(){}.getType();
    private Gson gson = new Gson();
    private WebsiteAdapter websiteAdapter;
    private List<WebsiteEntity> websiteListItems = new ArrayList<>();
    public static final String TAG = "WebguideActivity";
    private GridView gridview;
    private Activity activity = WebguideActivity.this;


    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            String result = (String) msg.obj;

            switch (msg.what) {

                case 0x123:

                    if (result.substring(0, 1).matches("[1-9]")) {
                        int hostVersionCode = Integer.valueOf(result);
                        int localVersionCode = Integer.valueOf(getVersionCode());
                        if(hostVersionCode > localVersionCode) {
                            Log.i(TAG,"该更新啦~");

                            Upgrade();
                        } else {
                            Log.i(TAG,"最新版");
                        }
                    }
                    else {
                        String json = (String) msg.obj;

                        Log.i("啦啦啦啦啦",json);

                        websiteListItems.clear();
                        websiteListItems = gson.fromJson(json, WebsiteListType);

                        for (int i = 0;i < websiteListItems.size();i++) {
                            Log.i(TAG, websiteListItems.get(i).getWname());
                        }

                        websiteAdapter.setListItems(websiteListItems);
                        websiteAdapter.notifyDataSetChanged();
                    }
                    break;
                case 0x444:

                    Log.i(TAG, result);

                    if (result.equals("yes")) {
                        new Thread(new GetThread(handler, "/home_android")).start();
                        new Thread(new GetThread(handler, "/getVersionCode")).start();
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

    private void Upgrade() {

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("有新版本应用，是否升级？");
        builder.setTitle("升级");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(activity, DownloadService.class);
                startService(intent);

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webguide);

        gridview = (GridView) findViewById(R.id.gridview);
        websiteAdapter = new WebsiteAdapter(activity, websiteListItems);
        gridview.setAdapter(websiteAdapter);


        new Thread(){

            @Override
            public void run() {
                IsNet.check(handler);
            }


        }.start();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(activity, WebbrowseActivity.class);
                String url = websiteListItems.get(position).getUrl();
                intent.putExtra("url", url);
                startActivity(intent);

            }
        });
    }



    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersionCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionCode + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "no find";
        }
    }



}


