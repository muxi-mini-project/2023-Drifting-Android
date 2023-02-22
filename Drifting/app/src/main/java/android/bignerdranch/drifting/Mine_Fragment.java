package android.bignerdranch.drifting;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class Mine_Fragment extends Fragment {
    TextView mSignText;
    TextView mSexText;
    TextView mNameText;
    ImageView mPortrait;
    User_ mUser;
    private Uri uri;
    final String FRAGMENT_KEY = "id";
    private static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED) ?
            Environment.getExternalStorageDirectory().getAbsolutePath() :
            "/mnt/sdcard";
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID mUUID = (UUID) getArguments().getSerializable(FRAGMENT_KEY);
        mUser = User_Lab.get(getActivity()).getUser(mUUID);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, container, false);

        mNameText = (TextView) view.findViewById(R.id.name_text);
        mSexText = (TextView) view.findViewById(R.id.sex_text);
        mSignText = (TextView) view.findViewById(R.id.sign_text);
        mPortrait = (ImageView) view.findViewById(R.id.portrait);
        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    // 从相册返回的数据
                    if (result.getData() != null) {
                        // 得到图片的全路径
                        Bitmap bit = getPhotoBitmap(result.getData());
                        try {
                            saveFile(bit, "myicon.jpg", "/icon");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
                        }

                        mPortrait.setImageBitmap(bit);
                        mUser.setPortrait(SAVE_REAL_PATH + "/icon/" + "myicon.jpg");
                    }
                }
            }
        });
        if (mUser.getPortrait() != null) {
            Bitmap bitmap = getDiskBitmap(mUser.getPortrait());
            BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
            mPortrait.setImageDrawable(bd);
        }
        mNameText.setText(mUser.getName());
        if (mUser.isSex())
            mSexText.setText("性别:女");
        else
            mSexText.setText("性别:男");
        mSignText.setText(mUser.getSignature());
        mPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                launcher.launch(intent);
            }
        });
        return view;
    }

    public Bitmap getPhotoBitmap(Intent data) {
        Bitmap bitmap = null;
        Bitmap bit = null;
        try {
            uri = data.getData();
            bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri));
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //return bit;
        return bitmap;
    }

    public void saveFile(Bitmap bm, String fileName, String path) throws IOException {
        String subForder = SAVE_REAL_PATH + path;
        File foder = new File(subForder);
        if (!foder.exists()) {
            Toast.makeText(getContext(), "this1", Toast.LENGTH_SHORT).show();
            foder.mkdir();
            Toast.makeText(getContext(), "this3", Toast.LENGTH_SHORT).show();
            foder.getParent();
        }
        if (foder.exists())
            Toast.makeText(getContext(), "this4", Toast.LENGTH_SHORT).show();
        File myCaptureFile = new File(SAVE_REAL_PATH + path);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
            myCaptureFile.createNewFile();
            Toast.makeText(getContext(), "this2", Toast.LENGTH_SHORT).show();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();

        // File myIconFile = new File(path);
        //if (myIconFile.exists()) {
        //     myIconFile.delete();
        //}
        //try {
        //     myIconFile.createNewFile();
        // }catch (IOException e){
        //     e.printStackTrace();
        // }
        // FileOutputStream fos = null;
        // try {
        //     fos =new FileOutputStream(myIconFile);
        //}catch (FileNotFoundException e){
        //    e.printStackTrace();
        // }
        // bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        // try {
        //     fos.flush();
        //     fos.close();
        //  }catch (IOException e){
        //     e.printStackTrace();
        //  }
    }

    public Bitmap getDiskBitmap(String pathString) {
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
}