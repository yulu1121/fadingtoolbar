package com.anshi.filechoice.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.anshi.filechoice.R;
import com.anshi.filechoice.self_view.ZoomImageView;
import com.bumptech.glide.Glide;

/**
 *
 * Created by yulu on 2017/11/29.
 */

public class ImageActivity extends AppCompatActivity {
    private ZoomImageView imageView;
    private String picPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity_main);
        picPath = getIntent().getStringExtra("picPath");
        initView();
    }

    private void initView() {
        imageView = (ZoomImageView) findViewById(R.id.find_image);
        if (!TextUtils.isEmpty(picPath)){
            Glide.with(this).load(picPath).into(imageView);
        }else {
            Toast.makeText(this, "图片路径错误", Toast.LENGTH_SHORT).show();
        }
    }

    public void backing(View view) {
        finish();
    }
}
