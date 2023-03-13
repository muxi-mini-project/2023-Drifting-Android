package android.bignerdranch.drifting.Mine;

import android.bignerdranch.drifting.User.User_Now;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件相关工具类，包括：
 * 保存文件到本地
 * 保存token到本地
 * 通过本地绝对路径获取图片
 * 获取从相册中返回的图片
 * 从URL中获取图片
 */
public class FileUtils {
   public static final int AVATAR = 1000;//头像
   public static final int PICTURE = 1001;//普通照片

    /**
     * 保存图片文件到本地并获取绝对路径
     * @param bm       图片
     * @param fileName 图片名字
     * @param path     外加路径（可无）
     * @return 图片的绝对路径
     */
    public static String saveFile(Context context,Bitmap bm, String fileName, String path) throws IOException {
        final String SAVE_PIC_PATH = context.getFilesDir().getAbsolutePath();
        final String SAVE_REAL_PATH = SAVE_PIC_PATH;
        File root = new File(SAVE_REAL_PATH);
        File myCaptureFile = null;
        if (path != null) {
            File foder = new File(root, path);
            if (!foder.exists()) {
                foder.mkdirs();
            }
            myCaptureFile = new File(foder, fileName);
        } else {
            myCaptureFile = new File(root, fileName);
        }
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
        myCaptureFile.createNewFile();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        String uri = myCaptureFile.getAbsolutePath();
        return uri;
    }

    /**
     * 保存token到本地
     *
     * @param token    需要保存的token
     * @param fileName 文件名称
     * @param path     外加地址
     * @throws IOException
     */
    public static void savetoken(Context context, String token, String fileName, String path) throws IOException {
        final String SAVE_TOKEN_PATH = context.getFilesDir().getAbsolutePath();
        File root = new File(SAVE_TOKEN_PATH);
        File mytokenFile = null;
        if (path != null) {
            File foder = new File(root, path);
            if (!foder.exists()) {
                foder.mkdirs();
            }
            mytokenFile = new File(foder, fileName);
        } else {
            mytokenFile = new File(root, fileName);
        }
        if (mytokenFile.exists()) {
            mytokenFile.delete();
        }
        mytokenFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(mytokenFile.getAbsolutePath());
        fileOutputStream.write(token.getBytes());
        fileOutputStream.close();
    }


    /**
     * 通过绝对路径获取图片（本地）
     *
     * @param pathString 图片绝对路径（一般通过file.getAbsolutePath()获取）
     * @return 图片
     */
    public static Bitmap getDiskBitmap(String pathString) {
        if (pathString == null || pathString.equals(""))
            return null;
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathString, opts);
        opts.inJustDecodeBounds = false;
        int sampleSize = 1;
        while (true) {
            if (opts.outHeight * opts.outWidth / sampleSize < 1281 * 901) {
                break;
            }
            sampleSize *= 2;
        }
        opts.inSampleSize = sampleSize;
        try {
            bitmap = BitmapFactory.decodeFile(pathString, opts);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 获取从相册返回的图片
     *
     * @param context 上下文
     * @param data    返回的数据(result.getData())
     * @return 图片
     */
    public static Bitmap getPhotoBitmap(Context context, Intent data) {
        Bitmap bitmap = null;
        Uri uri;
        try {
            uri = data.getData();
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从URL中获取返回的图片
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static Bitmap getImage(String path, int way) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn;
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream inStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inStream);
            inStream.close();
            if (way == AVATAR)
                User_Now.getUserNow().getUser().setavatar(bitmap);
            return bitmap;
        }
        return null;
    }

}