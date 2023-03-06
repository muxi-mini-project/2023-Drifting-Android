package android.bignerdranch.drifting.Mine;

import android.bignerdranch.drifting.User.User_Now;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 工具类，用于将图片保存到本地并获取图片
 */
public class SaveFile {


    /**
     *
     * @param context 获取的上下文
     * @param bm 图片
     * @param fileName 图片名字
     * @param path 外加路径（可无）
     * @return 图片的绝对路径
     */
    public static String saveFile(Context context, Bitmap bm, String fileName, String path) throws IOException {
        final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ?
                context.getExternalFilesDir("photoeditor" ).getAbsolutePath() :
                "/mnt/sdcard";
        final String SAVE_REAL_PATH = SAVE_PIC_PATH;
        File root = new File(SAVE_REAL_PATH);
        File foder = new File(root, path);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(foder, User_Now.getUserNow().getUser().getId()+fileName);
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
     * 通过绝对路径获取图片
     * @param pathString 图片绝对路径
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
     * @param context 上下文
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
}

