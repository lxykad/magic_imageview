package com.lxy.zoom.image.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

/**
 * Created by lxy on 2017/10/10.
 */

public class ZoomImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private boolean mIsFirst = true;
    private float mScale = 1.0f;

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //
        System.out.println("img========onAttachedToWindow");
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //
        System.out.println("img========onDetachedFromWindow");
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        //在这里获取图片的大小
        System.out.println("img========onGlobalLayout");
        if (mIsFirst) {
            //获取控件的宽高
            int viewWidth = getMeasuredWidth();
            int viewHeight = getMeasuredHeight();
            System.out.println("img==mh======" + viewWidth);
            System.out.println("img==mw======" + viewHeight);

            Drawable img = getDrawable();
            if (img == null) {
                //未设置src
                return;
            }
            //获取图片的宽高
            int imgWidth = img.getIntrinsicWidth();
            int imgHeight = img.getIntrinsicHeight();

            //计算缩放比例

            if (imgWidth > viewWidth && imgHeight < viewHeight) {
                //图片宽 -- 缩小宽
                mScale = viewWidth * 1.0f / imgWidth;
            }

            if (imgHeight > viewHeight && imgWidth < viewWidth) {
                //图片高 -- 缩小高
                mScale = viewHeight * 1.0f / imgHeight;

            }

            if (imgWidth > viewWidth && imgHeight > viewHeight) {
                //图片宽高均大于控件宽高，缩小长边
                mScale = Math.min(viewWidth * 1.0f / imgWidth, viewHeight * 1.0f / imgHeight);
            }

            if (imgWidth < viewWidth && imgHeight < viewHeight) {
                //图片宽高均小于控件宽高，拉伸长边
                mScale = Math.min(viewWidth * 1.0f / imgWidth, viewHeight * 1.0f / imgHeight);
            }

            System.out.println("img======scale=="+mScale);

            mIsFirst = false;
        }

    }
}
