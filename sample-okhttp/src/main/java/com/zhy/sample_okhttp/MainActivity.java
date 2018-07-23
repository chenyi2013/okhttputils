package com.zhy.sample_okhttp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBarBitmapUtils progressBarBitmapUtils = new ProgressBarBitmapUtils(this);
        progressBarBitmapUtils.setProgress(85);
        Bitmap bitmap = progressBarBitmapUtils.getBitmap();
        ImageView imageView = (ImageView) findViewById(R.id.image);

        imageView.setImageBitmap(bitmap);
//        imageView.setImageResource(R.drawable.aa);


    }
}
