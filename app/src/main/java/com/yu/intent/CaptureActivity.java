package com.yu.intent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by D22436 on 2017/7/27.
 * 用于使用intent拍照
 */

public class CaptureActivity extends Activity {

    private static final int REQUEST_CODE_CAPTURE = 1;
    private static final String AUTHORITY =  "com.yu.intent.fileprovider";


    private ImageView mIvCapturePhoto;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mIvCapturePhoto = (ImageView) findViewById(R.id.id_iv_capture_show);
    }

    /**
     * 拍照
     * @param view
     */
    public void capture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = (getPackageManager().resolveActivity(intent, PackageManager.MATCH_ALL) == null) ? false : true;
        if (canTakePhoto) {
            startActivity(intent);
        }
        mFile = new File(getFilesDir(), "IMG_"+new Date().toString() + ".jpg");
        Uri uri = FileProvider.getUriForFile(this,AUTHORITY, mFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);   // 指定uri输出
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        for (ResolveInfo info: resolveInfos) {  // 循环许可权限
            grantUriPermission(info.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        startActivityForResult(intent,REQUEST_CODE_CAPTURE);
    }

    public void showImageDialog(View view) {
        if (mFile == null || mFile.getPath() == null) {
            return;
        }
        ImageDialogFragment dialogFragment = ImageDialogFragment.newInstance(mFile.getPath());
        dialogFragment.show(getFragmentManager(),"ImageDialogFragment");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAPTURE) {
            Uri uri = FileProvider.getUriForFile(this, AUTHORITY, mFile);
            // Remove all permissions to access a particular content provider Uri that were
            // previously added with grantUriPermission(String, Uri, int).
            revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            Bitmap bitmap = getScaleBitmap(mFile.getPath(), 300, 500);
            mIvCapturePhoto.setImageBitmap(bitmap);

        }
    }

    /**
     * 获取压缩的图片
     * @param filePath
     * @param destWidth
     * @param destHeight
     * @return
     */
    public static Bitmap getScaleBitmap(String filePath, int destWidth, int destHeight) {
        if (destWidth <= 0 || destHeight <= 0) {
            return null;
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;  // 直解析配置参数
        BitmapFactory.decodeFile(filePath, opts);
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;
        int scale=1; // 默认缩放比为1
        if (srcWidth > destWidth || srcHeight > destHeight) {  // 比较大小计算缩放比
            int wScale = srcWidth / destWidth;
            int hScale = srcHeight / destHeight;
            scale = wScale > hScale ? wScale : hScale;
        }

        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;

        return BitmapFactory.decodeFile(filePath, opts);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}


