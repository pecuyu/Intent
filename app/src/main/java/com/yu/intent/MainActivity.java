package com.yu.intent;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static android.content.pm.PackageManager.MATCH_ALL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }


    public void explicitClick(View view) {
        startActivityExplicitly();
    }

    public void implicitClick(View view) {
        startActivityImplicitly();
    }

    /**
     * 使用选择器
     *
     * @param view
     */
    public void intentChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 选择器
        intent.createChooser(intent, "请选择:");
        startActivity(intent);

    }

    /**
     * 使用ShareCompat.IntentBuilder
     *
     * @param view
     */
    public void shareCompat(View view) {
        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(this);
        builder.setType("text/plain")
                .setSubject("subject")  // 主题
                .setText("text")        // 正文
                .startChooser(); // 发送

    }


    /**
     * implicit intent 隐式意图
     * 匹配IntentFilter，分为action、category 和 data 三个部分
     * data分为Uri、mimeType
     */
    public void startActivityImplicitly() {
        // action有且只可指定一个
        Intent intent = new Intent("com.yu.intent.implicit_intent");
        //系统自动帮助添加默认类别
//        intent.addCategory("android.intent.category.DEFAULT");
        //添加自定义的category，指定的category必须在IntentFilter中定义过的
        intent.addCategory("com.yu.intent.category.my");
        // 指定data
        intent.setData(Uri.parse("http://www.baidu.com"));
        // 选择器
        Intent.createChooser(intent, "请选择");
        /**
         * GET_META_DATA
         * GET_RESOLVED_FILTER
         * GET_SHARED_LIBRARY_FILES
         * MATCH_ALL
         * MATCH_DISABLED_COMPONENTS
         * MATCH_DISABLED_UNTIL_USED_COMPONENTS
         * MATCH_DEFAULT_ONLY
         * MATCH_DIRECT_BOOT_AWARE
         * MATCH_DIRECT_BOOT_UNAWARE
         * MATCH_SYSTEM_ONLY
         * MATCH_UNINSTALLED_PACKAGES
         */
        // 安全检查，判断要启动的组件是否存在
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, MATCH_ALL);
        if (resolveInfo == null ) {
            Toast.makeText(this, "no match activity", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(intent);
    }


    /**
     * explicit intent 显式意图
     * 直接指定要启动的组件
     */
    public void startActivityExplicitly() {
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivity(intent);

    }
}
