package com.lxy.zoom.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lxy on 2017/10/16.
 * 非压缩加载大图
 */

public class BigImgActivity extends AppCompatActivity {

    private ImageView mBigImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);

        mBigImageView = (ImageView) findViewById(R.id.iv_big);

        try {
            InputStream inputStream = getAssets().open("cc.JPG");

            //获取图片的宽高
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            int width = options.outWidth;
            int height = options.outHeight;
            System.out.println("bigimg=======width=" + width);

            //设置显示图片的区域
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
            bitmapOption.inPreferredConfig = Bitmap.Config.RGB_565;

            Rect rect = new Rect(width / 2 - 500, height / 2 - 500, width / 2 + 500, height / 2 + 500);
            Bitmap bitmap = decoder.decodeRegion(rect, bitmapOption);
            mBigImageView.setImageBitmap(bitmap);//解析图片的中间区域并显示在imageview上


        } catch (IOException e) {
            e.printStackTrace();
        }

        //BitmapRegionDecoder
    }


}
