package com.yu.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        startActivityExplicitly();
    }


    public void explicitClick(View view) {
        startActivityExplicitly();
    }

    public void implicitClick(View view) {
        startActivityImplicitly();
    }



    // implicit intent 隐式意图
    public void startActivityImplicitly() {

    }



    // explicit intent 显式意图
    public void startActivityExplicitly() {
        Intent intent = new Intent();
        intent.setClass(this, SecondActivity.class);
        startActivity(intent);

    }
}
