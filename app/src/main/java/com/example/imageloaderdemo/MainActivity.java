package com.example.imageloaderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_free;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_free=(ImageView)findViewById(R.id.iv_free);
        new ImageLoader().displayImage("http://www.wangjing.cn/app/ic.png",iv_free);

    }
}
