package top.glimpse.webguide_android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joyce on 16-5-18.
 */
public class FileUtil {

    private static final String TAG = "FileUtil";
    private File path = null;
    private File folderPath = null;
    private static String root = "/mnt/sdcard/webguide";
    private Context context;

    /**
     * 所要操作文件相对于/mnt/sdcard/NATIVE
     *
     * 例如FileUtil("/note", "note.txt") 就对应于 /mnt/sdcard/NATIVE/note/note.txt
     *
     * @param filePath
     * @param fileName
     */
    public FileUtil(Context context, String filePath, String fileName) {

        this.context = context;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            Toast.makeText(context ,"成功读取sd卡", Toast.LENGTH_LONG).show();
            Log.i(TAG ,"成功读取sd卡");
            String wholePath = root + filePath;

            this.folderPath = new File(wholePath);
            //检查路径是否存在


            boolean isfolderPath = true;
            if(!this.folderPath.exists()){
                if(folderPath.mkdirs()) {
//                    Toast.makeText(context ,folderPath + "文件夹创建成功", Toast.LENGTH_LONG).show();
                    Log.i(TAG ,folderPath + "文件夹创建成功");
                }
                else {
//                    Toast.makeText(context ,folderPath + "文件夹创建失败", Toast.LENGTH_LONG).show();
                    Log.i(TAG ,folderPath + "文件夹创建失败");
                    isfolderPath = false;
                }
            } else {
//                Toast.makeText(context ,folderPath + "文件夹已存在", Toast.LENGTH_LONG).show();
                Log.i(TAG ,folderPath + "文件夹已存在");
            }

            //文件名不为空
            if(isfolderPath && fileName != null){
                //得到文件.
                path = new File(folderPath,fileName);
                try {
                    //检查文件是否存在
                    if(!path.exists()){
                        if(path.createNewFile()) {
//                            Toast.makeText(context ,path + "文件创建成功", Toast.LENGTH_LONG).show();
                            Log.i(TAG ,path + "文件创建成功");
                        } else {
//                            Toast.makeText(context ,path + "文件创建失败", Toast.LENGTH_LONG).show();
                            Log.i(TAG ,path + "文件创建失败");
                        }
                    }
                    else {
//                        Toast.makeText(context ,path + "文件已存在", Toast.LENGTH_LONG).show();
                        Log.i(TAG ,path + "文件已存在");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
//                    Toast.makeText(context ,path + "文件创建发生错误", Toast.LENGTH_LONG).show();
                    Log.i(TAG ,path + "文件创建发生错误");
                }
            }



           // Log.i("lanbitou","文件完整路径" + path);
        }
        else {
//            Toast.makeText(context ,"不能读取sd卡", Toast.LENGTH_LONG).show();
            Log.i(TAG, "不能读取sd卡");
        }
    }

    /**
     * 直接写要要读的文件名.
     * @return
     */
    public String read() {
        String result = "";
        if (path != null) {
            try {
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
                byte[] bytes = new byte[1024];
                int count;
                while((count = inputStream.read(bytes)) != -1)
                {
                    result += new String(bytes, 0, count);
                }

                inputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
//                Toast.makeText(context ,"读：文件不存在", Toast.LENGTH_LONG).show();
                Log.i(TAG ,"读：文件不存在");
            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(context ,"读取错误", Toast.LENGTH_LONG).show();
                Log.i(TAG ,"读：读取错误");
            }
        }


        return result;
    }

    public void write(String data) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.flush();       //清空缓存区
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            Toast.makeText(context ,"写：文件不存在", Toast.LENGTH_LONG).show();
            Log.i(TAG ,"写：文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
//            Toast.makeText(context ,"写入错误", Toast.LENGTH_LONG).show();
            Log.i(TAG ,"写入错误");
        }
    }


}
