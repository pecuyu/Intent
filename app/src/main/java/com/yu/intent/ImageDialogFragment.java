package com.yu.intent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

/**
 * Created by D22436 on 2017/7/28.
 * 使用DialogFragment来显示原图
 */

public class ImageDialogFragment extends DialogFragment {
    public static ImageDialogFragment newInstance(String filePath) {
        ImageDialogFragment fragment = new ImageDialogFragment();
        Bundle args = new Bundle();
        args.putString("filePath", filePath);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_dialog_fragment_image, null);
        ZoomImageView iv = (ZoomImageView) view.findViewById(R.id.id_iv_dialog_image);
        Bitmap bitmap = BitmapFactory.decodeFile(getArguments().getString("filePath"), new BitmapFactory.Options());
        iv.setImageBitmap(bitmap);

        return new AlertDialog.Builder(getActivity()).setView(view).create();
    }
}
