package top.glimpse.webguide_android.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

import top.glimpse.webguide_android.util.GetThread;

public class DownloadService extends Service {
    private static final String TAG = "DownloadService";
    private DownloadManager dm;
    private long enqueue;
    private BroadcastReceiver receiver;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.i(TAG, "进入下载service");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.i(TAG, "开始安装");

                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/webguide.apk")),
                        "application/vnd.android.package-archive");
                startActivity(intent);
                stopSelf();
            }
        };

        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startDownload();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startDownload() {

        Log.i(TAG, "开始下载");

        deleteSameApk();

        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(GetThread.domain + "/resources/app/webguide.apk"));
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "webguide.apk");
        enqueue = dm.enqueue(request);
    }


    private void deleteSameApk() {

        File dirFile = new File(Environment.getExternalStorageDirectory() + "/download");
        File[] files = dirFile.listFiles();
        for (int i = 0;i < files.length;i++) {

            if (files[i].getName().length() > 8 && files[i].getName().substring(0,8).equals("webguide")) {
                files[i].delete();
            }

        }


    }
}