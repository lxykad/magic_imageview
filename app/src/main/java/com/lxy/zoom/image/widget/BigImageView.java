package com.lxy.zoom.image.widget;

import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lxy on 2017/10/16.
 * 自定义加载大图的控件
 */

public class BigImageView extends View {

    private BitmapRegionDecoder mDecoder;
    // 要显示的图片的宽高
    private int mImageWidth, mImageHeight;
    //绘制的区域
    private volatile Rect mRect = new Rect();
    //


    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
